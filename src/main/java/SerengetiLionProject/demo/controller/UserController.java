package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.Team;
import SerengetiLionProject.demo.domain.User;
import SerengetiLionProject.demo.dto.SessionUser;
import SerengetiLionProject.demo.dto.UserNickNameForm;
import SerengetiLionProject.demo.service.FinalScheduleService;
import SerengetiLionProject.demo.service.TeamService;
import SerengetiLionProject.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private MongoTemplate mongoTemplate;

    private FinalScheduleService finalScheduleService;
    private TeamService teamService;
    private UserService userService;

    @Autowired
    public UserController(FinalScheduleService finalScheduleService, TeamService teamService, UserService userService) {
        this.finalScheduleService = finalScheduleService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping("/checkNickname")
    public String checkNickname(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");
        // 로그인한 순간부터 가지고 있는 session 에서 user가져와서 이미 가입한 유저인지 확인
        if (user.getNickname().equals("")) // 닉네임이 없다면 닉네임 입력 페이지로
            return "thymeleaf/users/setUserNicknameForm";

        if((user.getTeam_id()).equals("")){ // 팀이 없는 경우 다회성 팀 만들기로 연결해주기
            return "redirect:/fixed/makeTeam";
        }

        String uid = user.getUid().toString();
        return "redirect:/mypage/" + uid; // 닉네임이 있다면 마이페이지로
    }

    @PostMapping("/users/enterNickname")
    public String setNickname(UserNickNameForm form, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Long uid = sessionUser.getUid();
        String nickname = form.getNickname();
        sessionUser.setNickname(nickname);   //session에 저장되는 nickname 수정
        User user = mongoTemplate.findAndModify( //DB에 해당하는 User 찾아서 nickname 수정
                Query.query(Criteria.where("_id").is(uid)),
                Update.update("nickname", nickname),
                User.class
        );

        // 자신의 일정만 관리하는 팀 하나를 자동 생성하고 내 id에 팀번호 넣어주기
        Team team = new Team(nickname+"의 개인 일정",uid); // 팀 객체 생성 (팀장 id - 나자신)
        Team ownTeam = teamService.saveTeam(team);

        // 현재 팀을 만든 사람에도 team id를 추가해줘야함
        User me=userService.findByEmail(sessionUser.getEmail());
        System.out.println("me.getNickname() = " + me.getNickname());
        User user1 = userService.saveTeamId(me.getTeam_id(),me.getEmail(),ownTeam.get_id());
        System.out.println("user1.getTeam_id() = " + user1.getTeam_id());
        return "redirect:/";
    }

    @GetMapping("/mypage/{uid}")
    public String myPage(@PathVariable("uid") String uid, Model model) {
        System.out.println("uid = " + uid);
        Long id = Long.parseLong(uid);
        HashMap<Long, String> teams = new HashMap<>();
        HashMap<String, String> schedules = new HashMap<>();
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), User.class);

        String[] teamList = user.getTeam_id().split(",");
        for (int i = 0; i < teamList.length; i++) {
            Long tid = Long.parseLong(teamList[i]);
            Team team = teamService.findTeamById(tid);
            teams.put(tid, team.getName()); // 팀아이디, 팀이름
            List<FinalSchedule> scheduleList = finalScheduleService.findAllbyTeamId(tid);
            if (scheduleList.isEmpty())
                continue;
            for (int j = 0; j < scheduleList.size(); j++) {
                schedules.put(scheduleList.get(j).getSchedule_title(), scheduleList.get(j).getFinal_date());
            }
        }
        model.addAttribute("teams", teams);
        model.addAttribute("schedules", schedules);

        return "thymeleaf/users/mypage";
    }
}
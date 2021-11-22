package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.Team;
import SerengetiLionProject.demo.domain.User;
import SerengetiLionProject.demo.dto.SessionUser;
import SerengetiLionProject.demo.dto.UserNickNameForm;
import SerengetiLionProject.demo.service.FinalScheduleService;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.TeamService;
import SerengetiLionProject.demo.service.TestMeetPersonalService;
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
//    private SequenceGeneratorService sequenceGeneratorService;
    private MeetGroupService meetGroupService;
        private TestMeetPersonalService personalService;
        private FinalScheduleService finalScheduleService;
        private TeamService teamService;

        @Autowired
        public UserController(MeetGroupService onceMemberService, TestMeetPersonalService personalService, FinalScheduleService finalScheduleService, TeamService teamService) {
            this.meetGroupService = onceMemberService;
            this.personalService=personalService;
            this.finalScheduleService=finalScheduleService;
            this.teamService=teamService;
        }

    @GetMapping("/users/new")
    public String createForm(){
        return "thymeleaf/users/createUserForm";
    }

    @GetMapping("/checkNickname")
    public String checkNickname(HttpServletRequest request){
        HttpSession session = request.getSession();
        SessionUser user=(SessionUser) session.getAttribute("user");

        if(user.getNickname().equals(""))
            return "thymeleaf/users/setUserNicknameForm";
        String uid=user.getUid().toString();
        return "redirect:/mypage/"+uid;
    }

    @PostMapping("/users/enterNickname")
    public String setNickname(UserNickNameForm form,HttpServletRequest request){
        HttpSession session = request.getSession();
        SessionUser sessionUser=(SessionUser) session.getAttribute("user");
        Long uid= sessionUser.getUid();
        String nickname=form.getNickname();
        sessionUser.setNickname(nickname);   //session에 저장되는 nickname 수정
        User user = mongoTemplate.findAndModify( //DB에 해당하는 User 찾아서 nickname 수정
                Query.query(Criteria.where("_id").is(uid)), Update.update("nickname",nickname), User.class);
        return "redirect:/";

        //어떻게 title이랑 id마다 다른 링크 보내는거야...ㅠㅠ
    }

    @GetMapping("/mypage/{uid}")
    public String myPage(@PathVariable("uid") String uid, Model model){
        Long id=Long.parseLong(uid);
        HashMap<Long, String> teams=new HashMap<>();
        HashMap<String, String> schedules=new HashMap<>();
        User user=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)),User.class);
        String[] teamList=user.getTeam_id().split(",");
        for(int i=0;i< teamList.length;i++){
            Long tid=Long.parseLong(teamList[i]);
            Team team=teamService.findTeamById(tid);
            teams.put(tid,team.getName());
            List<FinalSchedule> scheduleList=finalScheduleService.findAllbyTeamId(tid);
            if(scheduleList.isEmpty())
                continue;
            for(int j=0;j< scheduleList.size();j++){
                schedules.put(scheduleList.get(j).getSchedule_title(),scheduleList.get(j).getFinal_date());
            }
        }
        model.addAttribute("teams",teams);
        model.addAttribute("schedules",schedules);

        return "thymeleaf/users/mypage";
    }

    /* 회원관리 예제 테스트 코드
    @PostMapping("/users/new")
    public String create(Userform userform) { // form 데이터를 받을 때 DTO 라는 객체로 받음
        User user = new User(userform.getName(),email, Role.USER);
        user.setUid(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setName(userform.getName());
        mongoTemplate.insert(user);
        return "redirect:/"; // 홈화면으로 보내는 것
    }
    @GetMapping("/users")
    public String list(Model model){
        List<User> Users = mongoTemplate.findAll(User.class);
        model.addAttribute("users", Users);
        return "Users/UserList";
    }
    */
}
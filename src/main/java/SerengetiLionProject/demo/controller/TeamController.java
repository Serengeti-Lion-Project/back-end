package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.MeetNote;
import SerengetiLionProject.demo.domain.Team;
import SerengetiLionProject.demo.domain.User;

import SerengetiLionProject.demo.dto.*;
import SerengetiLionProject.demo.service.FinalScheduleService;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.MeetNoteService;
import SerengetiLionProject.demo.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import java.util.List;

@Controller
public class TeamController {

    private MeetGroupService meetGroupService;
    private MeetPersonalService personalService;
    private FinalScheduleService finalScheduleService;
    private MeetNoteService meetNoteService;
    private TeamService teamService;
    private UserService userService;

    @Autowired
    public TeamController(MeetGroupService meetGroupService, MeetPersonalService personalService, FinalScheduleService finalScheduleService, MeetNoteService meetNoteService, TeamService teamService, UserService userService) {

        this.meetGroupService = meetGroupService;
        this.personalService = personalService;
        this.finalScheduleService = finalScheduleService;
        this.meetNoteService = meetNoteService;
        this.teamService = teamService;
        this.userService = userService;
        this.meetNoteService = meetNoteService;
    }

    @GetMapping("/fixed/makeTeam") // 다회성 팀 만들기 페이지
    public String teamMake(){
        return "thymeleaf/makeTeam"; // makeTeam.html
    }

    @PostMapping("/fixed/makeTeam")
    public String teamMake(TeamMakingForm teamMakingForm, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        String team_name = teamMakingForm.getTeam_name(); // 팀명
        String invited_members = teamMakingForm.getInvited_members(); // 초대한 팀원
        String[] emails = invited_members.split(",");
        // 팀 생성 시 팀장 아이디도 넣어줘야 함

        Team team = new Team(team_name,sessionUser.getUid()); // 팀 객체 생성 (팀장 id - 현재 로그인한 애)
        Team team_temp = teamService.saveTeam(team);
        Long team_id = team_temp.get_id(); // 팀 id 가져오기
        String teamId = team_id.toString();
        for(int i=0;i<emails.length;i++){ // 초대할 팀원 한명씩 이미 존재하는 유저인지 확인
            User user = userService.findByEmail(emails[i]);
            if(user==null){ // 해당 이메일을 갖는 유저가 존재하지 않는다면
                model.addAttribute("msg",emails[i]+"는 없는 이메일입니다");
                return "thymeleaf/makeTeam"; // front에게 msg 보냄
            }
            else{ // 유저가 존재하면 teamId에 추가해준다.
                userService.saveTeamId(user.getTeam_id(), user.getEmail(), team_id);
            }
        }
        return "redirect:/fixed/"+teamId; //    <input type="hidden" id="team_id" name="team_id" value="${team_id}">
    }

    //현재 팀 (team_id가 할당되어있다 가정 -> '새로운 meet 만들기' 누르면 여기로 넘어옴
    @GetMapping(value="/fixed/{teamid}")
    public String createNewTeamMeet(Model model, @PathVariable("teamid") String teamid){
        Long team_id=Long.parseLong(teamid);
        model.addAttribute("team_id",team_id);
        return "thymeleaf/createFixedMeetForm";
    }

    // "createFixedMeetForm" 에서 작성한 폼 (새로운 팀용 meet에 대한 정보): 여기로 넘어옴
    @PostMapping(value="/fixed/newmeet")
    public String createNewTeamMeetForm(MeetTeamNewGroupForm form){
        Long team_id=form.getTeam_id();
        MeetGroup meetGroup=new MeetGroup(form.getTitle(),form.getStart_date().toString(),form.getEnd_date().toString(),form.getStart_time(),form.getEnd_time(),"");
        meetGroup.setTeam_id(team_id);
        Long created_url=meetGroupService.SaveGroup(meetGroup);
        Long url_id=created_url;
        String url=url_id.toString();
        String title=meetGroup.getTitle();
        return "redirect:/fixed/"+title+"/"+url; //생성한 후 새롭게 만들어진 meet페이지로 넘겨줌!
    }

    //새롭게 생성된 현재 팀의 meet page -> 지금은 확정일정만,, 테스트하니까 확정일정용 페이지로 넘겨줌
    @GetMapping("/fixed/{title}/{urlid}")
    public String enterNewTeamMeetPage(Model model, @PathVariable("title") String title, @PathVariable("urlid") String urlid){
        Long url_id=Long.parseLong(urlid);
        MeetGroup group=meetGroupService.findOne(url_id);
        Long team_id=group.getTeam_id();
        model.addAttribute("teamid",team_id);
        model.addAttribute("title",title); //확정날짜 저장할 때 team_id랑 제목 저장해야하니까 jsp로 넘겨서 같이 저장 -> 받아오기
        return "thymeleaf/saveFinalScheduleForm";
    }

    @PostMapping("/team/confirmMeetDate")
    public String finalDateForm(MeetTeamFinalDateForm form){
        //입력한 확정날짜 정보 받아와서 "FinalSchedule클래스 생성, db에 저장"
        FinalSchedule date=new FinalSchedule(form.getTeam_id(),form.getTitle(),form.getFinal_date(),form.getStart_hour(),form.getStart_min()
        ,form.getEnd_hour(),form.getEnd_min());
        finalScheduleService.saveFinalSchedule(date);
        System.out.println("form.getTitle() = " + form.getTitle());
        return "redirect:/";
    }


    @GetMapping ("/teampage/{teamid}")
    public String mainTeamPage(Model model, @PathVariable("teamid") String teamid){
        Long team_id = Long.parseLong(teamid);
        Team team = teamService.findTeamById(team_id);
        List<FinalSchedule> finalSchedules = finalScheduleService.findAllbyTeamId(team_id);
        List<MeetNote> notes = meetNoteService.findAllByTeam_id(team_id); // 팀 회의록

        // 팀의 최종 스케줄        
        model.addAttribute("finalSchedules",finalSchedules);
        // 팀의 회의록
        model.addAttribute("notes",notes);
        return "thymeleaf/mainTeamPage";
    }


    //현재 팀 (team_id가 할당되어있다 가정
    @GetMapping(value="/teampage/new/{teamid}")
    public String createNewTeamNote(Model model, @PathVariable("teamid") String teamid){
        Long team_id=Long.parseLong(teamid);
        model.addAttribute("team_id",team_id);
        return "thymeleaf/createNewTeamNote";
    }
    @PostMapping("/teampage/new")
    public String createNewTeamNoteForm(MeetTeamNewNoteForm form){
        Long team_id = form.getTeam_id();
        MeetNote meetNote=new MeetNote(form.getNote_title(),form.getNote_content(),form.getWrite_date().toString());
        meetNote.setTeam_id(team_id);
        meetNoteService.SaveNote(meetNote); // 저장하면 note_id 리턴
        String teamid=team_id.toString();

        return "redirect:/teampage/"+teamid;
    }

}

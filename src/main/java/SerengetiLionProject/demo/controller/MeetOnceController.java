package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.TestMeetPersonal;
import SerengetiLionProject.demo.dto.MeetOnceGroupForm;
import SerengetiLionProject.demo.dto.MeetOnceUserForm;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.TestMeetPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MeetOnceController {
    private MeetGroupService meetGroupService;
    private final TestMeetPersonalService personalService;

    @Autowired  //생성자 위에 Autowired 있으면 스프링 컨테이너에 있는 OnceMemberService 와 연결시킴 (의존관계 주입)
    public MeetOnceController(MeetGroupService onceMemberService, TestMeetPersonalService personalService) {
        this.meetGroupService = onceMemberService;
        this.personalService=personalService;
    }


    @GetMapping("/")
    public String firstPage(){
        return "thymeleaf/firstPage";
    }

    @GetMapping("/once")
    public String once(){
        return "thymeleaf/onceGroup"; // once.html
    }

    @PostMapping("/once")
    public String create(MeetOnceGroupForm form){
        //Long url_id, String title, String start_date, String end_date, Integer start_time, Integer end_time, String page_pw
        System.out.println("meet group post mapping: title:"+form.getTitle());
        MeetGroup meetGroup=new MeetGroup(form.getTitle(),form.getStart_date().toString(),form.getEnd_date().toString(),form.getStart_time(),form.getEnd_time(),form.getPage_pw());
        Long get_url=meetGroupService.onceSaveGroup(meetGroup);
        Long url_id=get_url;
        String url=url_id.toString();
        String title=meetGroup.getTitle();
        return "redirect:/once/"+title+"/"+url; //beforeEnter.html

        //어떻게 title이랑 id마다 다른 링크 보내는거야...ㅠㅠ
    }

    /**
     * 일회용 when2meet 생성 -> url이 할당되었다 가정
     */
    @GetMapping(value="/once/{title}/{urlid}")
    public String newMeetUserForm(Model model, @PathVariable("title") String title, @PathVariable("urlid") String urlid, HttpServletRequest request){
        Long id=Long.parseLong(urlid);

        //Model에 값 넣어서 form으로 전달 -> 나중에 다시 postMapping에서 제대로 된 url로 보내주기 위한,,,,,, 바보가튼 방법입니다.
        model.addAttribute("urlid",id);
        model.addAttribute("title",title);
        return "meetNewUserForm";
    }

    /**
     * form에 이름, 방 비밀번호, 개인비밀번호 입력하면 여기로 옴
     * 왜 value가 "/meetonce/new/create" 인가요? -> ,, form에서 action으로 변수값을 넣어주는게 인터넷에서 알려주는대로 해도 안돼서,,, 그냥 여기서 redirect하기로 했어요
     */
    @PostMapping(value="/once/new/create")
    public String createOnceUser(MeetOnceUserForm meetOnceUserForm, Model model){
        //form에 hidden input으로 값 넣어둠 -> url_id랑 title 얻어오기
        String url_id=meetOnceUserForm.getUrl_id();
        String title=meetOnceUserForm.getTitle();
        System.out.println("From Postmapping: url_id: "+url_id+"   title: "+title);

        //url+title로 방 비밀번호 검증작업 필요!

        TestMeetPersonal person=new TestMeetPersonal(Long.parseLong(url_id),title,meetOnceUserForm.getName(),meetOnceUserForm.getUpw());
        String val=personalService.saveNewUser(person); //여기서 중복유저 확인, 신규유저 확인합니다.
        if(val.equals("userCheckSuccess")||val.equals(person.getName())){
            model.addAttribute("resultText",val);
            model.addAttribute("name",person.getName());
            return "redirect:/once/"+title+"/"+url_id+"/enter"; //입장하면 (신규유저이든 아니면 기존유저 확인이든) 보내줘야 하는 url로 다시 보내주기
        }
        else
            return "redirect:/once/"+title+"/"+url_id;  //비밀번호 (개인비밀번호 or 팀비밀번호) 틀리면 처음페이지로 redirect
    }

    /**
     * 위에서 사용자 검증 후, redirect하면 여기로 넘어옵니다.
     */
    @GetMapping(value="/once/{title}/{urlid}/enter")
    public String enterMeetOnce(Model model, @PathVariable("title") String title, @PathVariable("urlid") String urlid){
        return "thymeleaf/meet";
    }
}

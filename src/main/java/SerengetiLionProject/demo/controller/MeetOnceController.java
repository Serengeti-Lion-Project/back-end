package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.AvailableTime;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.domain.TestPersonal;
import SerengetiLionProject.demo.dto.MeetOnceAvailableTimeForm;
import SerengetiLionProject.demo.dto.MeetOnceNewGroupForm;
import SerengetiLionProject.demo.dto.MeetOnceEntranceForm;
import SerengetiLionProject.demo.service.AvailableTimeService;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.NewTestPersonalService;
import SerengetiLionProject.demo.service.TestMeetPersonalService;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MeetOnceController {
    private MeetGroupService meetGroupService;
    private NewTestPersonalService personalService;
    private AvailableTimeService availableTimeService;

    @Autowired  //생성자 위에 Autowired 있으면 스프링 컨테이너에 있는 OnceMemberService 와 연결시킴 (의존관계 주입)
    public MeetOnceController(MeetGroupService meetGroupService, NewTestPersonalService personalService, AvailableTimeService availableTimeService) {
        this.meetGroupService = meetGroupService;
        this.personalService = personalService;
        this.availableTimeService = availableTimeService;
    }

    @GetMapping("/") // 첫 페이지
    public String firstPage() {
        return "thymeleaf/firstPage";
    }

    @GetMapping("/once") // 일회성 만드는 페이지
    public String once() {
        return "thymeleaf/onceGroup"; // onceGroup.html
    }

    @PostMapping("/once") // 일회성 만드는 페이지에서 데이터 입력하여 DB에 저장
    public String create(MeetOnceNewGroupForm form) {
        //Long url_id, String title, String start_date, String end_date, Integer start_time, Integer end_time, String page_pw
        System.out.println("meet group post mapping: title:" + form.getTitle());
        System.out.println("form.getStart_date() = " + form.getStart_date());
        System.out.println("form.getStart_date() = " + form.getEnd_date());

        MeetGroup meetGroup = new MeetGroup(form.getTitle(), form.getStart_date().toString(), form.getEnd_date().toString(), form.getStart_time(), form.getEnd_time(), form.getPage_pw());
        Long get_url = meetGroupService.SaveGroup(meetGroup); // meet 만들고 리턴받은 url_id 값 저장
        Long url_id = get_url;
        String url = url_id.toString();
        String title = meetGroup.getTitle();
        return "redirect:/once/" + title + "/" + url;
    }

    /**
     * 일회용 when2meet 생성 -> 이름, 비밀번호, 방 비밀번호 입력받는 페이지로 연결
     * (일회성_메인페이지_초대된사람_입장전 페이지)
     */
    @GetMapping(value = "/once/{title}/{urlid}")
    public String newMeetUserForm(Model model, @PathVariable("title") String title, @PathVariable("urlid") String urlid, HttpServletRequest request) {
        Long id = Long.parseLong(urlid);
        //Model에 값 넣어서 form으로 전달 -> 나중에 다시 바로 아래 코드 postMapping에서 제대로 된 url로 보내주기 위한,,,,,, 바보가튼 방법입니다.
        //
        model.addAttribute("urlid", id);
        model.addAttribute("title", title);
        System.out.println("id = " + id);
        System.out.println("title = " + title);
        return "thymeleaf/onceMeetEntranceForm";
    }

    /**
     * onceMeetEntranceForm.html (입장 전 입력하는 form 화면)
     */
    // 입장전 페이지에서 입장하기 버튼 누른 경우에 폼에 입력한 데이터 여기로 넘어옴
    @PostMapping(value = "/once/new/createUser")
    public String createOnceUser(MeetOnceEntranceForm meetOnceEntranceForm, Model model, RedirectAttributes redirectAttributes) {
        //form에 hidden input으로 값 넣어둔 url_id랑 title 얻어오기
        String url_id = meetOnceEntranceForm.getUrl_id();
        String title = meetOnceEntranceForm.getTitle();
        System.out.println("url_id = " + url_id);
        System.out.println("title = " + title);
        //url+title로 방 비밀번호 검증작업 필요!
        MeetGroup group = meetGroupService.findOne(Long.parseLong(url_id));
        if (group != null) {
            String page_pw = group.getPage_pw();
            if (!meetOnceEntranceForm.getPage_pw().equals(page_pw)) {
                return "redirect:/once/" + title + "/" + url_id;
            }
        } else {
            return "redirect:/once";
        }

        TestPersonal person = new TestPersonal(Long.parseLong(url_id), title, meetOnceEntranceForm.getName(), meetOnceEntranceForm.getUpw());
        String val = personalService.saveNewUser(person); //여기서 중복유저 확인, 신규유저 확인합니다.
        if (val.equals("userCheckSuccess") || val.equals(person.getName())) { // 신규 등록 유저 이거나 기존 유저인 경우
//            model.addAttribute("resultText", val);
//            model.addAttribute("name", person.getName());
//            // 링크 입장 후에도 사용하기 위해서 title, url_id 도 모델에 담음
//            model.addAttribute("title", title);
//            model.addAttribute("url_id", url_id);
            redirectAttributes.addAttribute("name", person.getName());
            return "redirect:/once/" + title + "/" + url_id + "/enter"; //입장하면 (신규유저이든 아니면 기존유저 확인이든) 보내줘야 하는 url로 다시 보내주기
        } else
            return "redirect:/once/" + title + "/" + url_id;  //비밀번호 (개인비밀번호 or 팀비밀번호) 틀리면 처음페이지로 redirect
    }

    /**
     * 위에서 사용자 검증 후, redirect하면 여기로 넘어옵니다.
     * 일회성_메인페이지_초대된사람_입장후_이름입력후 페이지
     */
    @GetMapping(value = "/once/{title}/{urlid}/enter")
    public String enterMeetOnce(Model model, @PathVariable("title") String title, @PathVariable("urlid") Long urlid, @RequestParam("name") String name) {
        MeetGroup group=meetGroupService.findOne(urlid);
        model.addAttribute("title",title);
        model.addAttribute("urlid",urlid);
        model.addAttribute("start_date",group.getStart_date());
        model.addAttribute("end_date",group.getEnd_date());
        model.addAttribute("start_time",group.getStart_time());
        model.addAttribute("end_time",group.getEnd_time());
        model.addAttribute("name",name);
        return "thymeleaf/onceMeetAfterEnter";
    }

    /**
     * 그냥 일단 2차원 배열 무작정 넘겨보고 디비에 저장되는지만 확인 (2차원 배열이 어떻게 넘어오는지 모르겠어서)
     */

    @PostMapping("/once/createPersonal")
    public String testSaveMeetPersonal(MeetOnceAvailableTimeForm form){
        String[] splited=form.getStart_date().split("/");
        Integer start_date=Integer.parseInt(splited[1]);
        splited=form.getEnd_date().split("/");
        Integer end_date=Integer.parseInt(splited[1]);
        Integer total_date=end_date-start_date+1;
        Integer total_time=form.getEnd_time()-form.getStart_time()+1;
        String title=form.getTitle();

        //mon,tue ... 이런식이 아니라 그냥 first, second 이렇게 seventh까지 만들어서 first==start_date, end_date이후의 값들은 null로
        Integer[][] availability=new Integer[total_date][total_time];
        //일단 전체날짜*전체시간으로 2차원 배열 생성
        for(int i=0;i<total_date;i++){
            for(int j=0;j<total_time;j++){
                availability[i][j]=(int)(Math.random()*2);  //0,1 중에 랜덤하게 값 저장
            }
        }

        System.out.println("<<<<<<<<<<<Availability>>>>>>>>>>");
        for(int i=0;i<total_date;i++){
            for(int j=0;j<total_time;j++){
                System.out.print(availability[i][j]+" ");
            }
            System.out.println();
        }
        Long url_id=form.getUrlid();

        //.........arraylist 생성....
        ArrayList<Integer> first=null;
        ArrayList<Integer> second=null;
        ArrayList<Integer> third=null;
        ArrayList<Integer> fourth=null;
        ArrayList<Integer> fifth=null;
        ArrayList<Integer> sixth=null;
        ArrayList<Integer> seventh=null;  //총 선택가능 날짜가 5일이라면? -> sixth랑 seventh는 null

        //한숨만 나오는 노가다 코드..... 원래 String.valueOf(1) 이런식으로 해서 제목이 1이면->availability[1] 저장 이렇게 하고 싶었는데,,, 안되더라구요 좋은 방법을 아신다면 연락주세요...
        for(int i=0;i<total_date;i++){
            switch (i) {
                case 0:
                    first = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
                case 1:
                    second = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
                case 2:
                    third = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
                case 3:
                    fourth = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
                case 4:
                    fifth = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
                case 5:
                    sixth = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
                case 6:
                    seventh = new ArrayList<Integer>(Arrays.asList(availability[i]));
                    break;
            }
        }

        TestPersonal updateRes=personalService.updatePersonalMeet(url_id,form.getName(),first,second,third,fourth,fifth,sixth,seventh);
        return "redirect:/once/"+title+"/"+url_id.toString();  //일단은 그냥 입장화면으로 redirect했는데 나중에 결과 다 불러오는 페이지 추가할 예정

    }
}
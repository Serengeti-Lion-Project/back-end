package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.AvailableTime;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.dto.MeetOnceAvailableTimeForm;
import SerengetiLionProject.demo.dto.MeetOnceNewGroupForm;
import SerengetiLionProject.demo.dto.MeetOnceEntranceForm;
import SerengetiLionProject.demo.service.AvailableTimeService;
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
    private TestMeetPersonalService personalService;
    private AvailableTimeService availableTimeService;

    @Autowired  //생성자 위에 Autowired 있으면 스프링 컨테이너에 있는 OnceMemberService 와 연결시킴 (의존관계 주입)
    public MeetOnceController(MeetGroupService meetGroupService, TestMeetPersonalService personalService, AvailableTimeService availableTimeService) {
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
    public String createOnceUser(MeetOnceEntranceForm meetOnceEntranceForm, Model model) {
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

        MeetPersonal person = new MeetPersonal(Long.parseLong(url_id), title, meetOnceEntranceForm.getName(), meetOnceEntranceForm.getUpw());
        String val = personalService.saveNewUser(person); //여기서 중복유저 확인, 신규유저 확인합니다.
        if (val.equals("userCheckSuccess") || val.equals(person.getName())) { // 신규 등록 유저 이거나 기존 유저인 경우
            model.addAttribute("resultText", val);
            model.addAttribute("name", person.getName());
            // 링크 입장 후에도 사용하기 위해서 title, url_id 도 모델에 담음
            model.addAttribute("title", title);
            model.addAttribute("url_id", url_id);
            return "redirect:/once/" + title + "/" + url_id + "/enter"; //입장하면 (신규유저이든 아니면 기존유저 확인이든) 보내줘야 하는 url로 다시 보내주기
        } else
            return "redirect:/once/" + title + "/" + url_id;  //비밀번호 (개인비밀번호 or 팀비밀번호) 틀리면 처음페이지로 redirect
    }

    /**
     * 위에서 사용자 검증 후, redirect하면 여기로 넘어옵니다.
     * 일회성_메인페이지_초대된사람_입장후_이름입력후 페이지
     */
    @GetMapping(value = "/once/{title}/{urlid}/enter")
    public String enterMeetOnce(Model model, @PathVariable("title") String title, @PathVariable("urlid") String urlid) {
        return "thymeleaf/onceMeetAfterEnter";
    }

    /**
     * 입장 후 사용자가 가능한 시간 입력한 데이터를 DB에 저장
     */
//    @PostMapping(value = "/once/{title}/{urlid}/enter")
//    public String markAvailableTime(MeetOnceAvailableTimeForm meetOnceAvailableTimeForm, Model model) {
//        AvailableTime availableTime = new AvailableTime(
//                Long.valueOf(String.valueOf(model.getAttribute("url_id"))), // 입장전 페이지의 model에 있던 값을 가져옴
//                String.valueOf(model.getAttribute("title")),
//                String.valueOf(model.getAttribute("name")),
//                meetOnceAvailableTimeForm.getMonday_start_time(),
//                meetOnceAvailableTimeForm.getMonday_end_time(),
//                meetOnceAvailableTimeForm.getTuesday_start_time(),
//                meetOnceAvailableTimeForm.getTuesday_end_time(),
//                meetOnceAvailableTimeForm.getWednesday_start_time(),
//                meetOnceAvailableTimeForm.getWednesday_end_time(),
//                meetOnceAvailableTimeForm.getThursday_start_time(),
//                meetOnceAvailableTimeForm.getThursday_end_time(),
//                meetOnceAvailableTimeForm.getFriday_start_time(),
//                meetOnceAvailableTimeForm.getFriday_end_time(),
//                meetOnceAvailableTimeForm.getSaturday_start_time(),
//                meetOnceAvailableTimeForm.getSaturday_end_time(),
//                meetOnceAvailableTimeForm.getSunday_start_time(),
//                meetOnceAvailableTimeForm.getSunday_end_time()
//                );
//        AvailableTime time = availableTimeService.saveTime(availableTime);
//        return "redirect:/once/" + time.getTitle() + "/" + time.getUrl_id();
//    }
}
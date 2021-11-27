package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.*;
import SerengetiLionProject.demo.dto.MeetOnceAvailableTimeForm;
import SerengetiLionProject.demo.dto.MeetOnceNewGroupForm;
import SerengetiLionProject.demo.dto.MeetOnceEntranceForm;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.MeetPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MeetOnceController {
    private MeetGroupService meetGroupService;
    private MeetPersonalService personalService;

    @Autowired  //생성자 위에 Autowired 있으면 스프링 컨테이너에 있는 OnceMemberService 와 연결시킴 (의존관계 주입)
    public MeetOnceController(MeetGroupService meetGroupService, MeetPersonalService personalService) {
        this.meetGroupService = meetGroupService;
        this.personalService = personalService;
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

        //아래의 시간, 날짜 값들은 프론트에서 화면에 날짜 및 시간 표시해주기 위해 저장해주는 값
        model.addAttribute("start_date",group.getStart_date());
        model.addAttribute("start_time",group.getStart_time());
        model.addAttribute("name",name);
        model.addAttribute("isLeader",0);

        int[] calcRes= meetGroupService.calcTotalTimeandDate(group);
        int total_date=calcRes[0];
        int total_time=calcRes[1];

        // 배열 크기 결정 위해 저장하는 값
        model.addAttribute("total_time",total_time);
        model.addAttribute("total_date",total_date);

        List<MeetPersonal> total_personal=personalService.findAll(urlid,title);
        model.addAttribute("headcount",total_personal.size());

        //해당 meet에 대한 전체 가능 시간 보여주기 위해 배열로 저장
        int[][] avail_array=meetGroupService.findTotalAvailability(group,total_personal);
        model.addAttribute("total_availability",avail_array);

        //개인 가능 시간 화면에 보여주기 위해 배열로 저장
        MeetPersonal person=personalService.findOneByName(urlid,name);
        int[][] personal_array=personalService.findOnesAvailability(person,total_time,total_date);
        model.addAttribute("personal_availability",personal_array);
        return "thymeleaf/selectTime";
    }

    @ResponseBody
    @RequestMapping(value="/once/testPersonal",method=RequestMethod.POST)
    public String getJson(MultipartHttpServletRequest req){
        String[] splited=req.getParameter("selectedArray").split(",");
        //사용자가 프론트에서 선택한 시간배열이 string으로 넘어옴 -> , 기준으로 잘라서 String[]에 넣어놓고

        int total_time=Integer.parseInt(req.getParameter("total_time"));
        int total_date=Integer.parseInt(req.getParameter("total_date"));
        Integer[][] availability=new Integer[total_time][total_date];

        //total_time, total_date 기준으로 잘라서 int로 변환시켜 availability 배열에 저장 (=> 개인이 선택한 가능시간)
        for(int i=0;i<total_time;i++){
            for(int j=0;j<total_date;j++){
                availability[i][j]=Integer.parseInt(splited[total_date*i+j]);  //0,1 중에 랜덤하게 값 저장
            }
        }

        Long url_id=Long.parseLong(req.getParameter("urlid"));

        ArrayList<ArrayList> availabilityList=new ArrayList<>();
        for(int i=0;i<total_time;i++){
            availabilityList.add(new ArrayList<Integer> (Arrays.asList(availability[i])));
        }
        //디비에 가능시간 업데이트
        MeetPersonal updateRes=personalService.updatePersonalMeet(url_id,req.getParameter("name"), availabilityList);

        return "success";
    }
}
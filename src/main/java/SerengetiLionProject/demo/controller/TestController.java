package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.TestMeetPersonal;
import SerengetiLionProject.demo.dto.MeetOnceUserForm;

import SerengetiLionProject.demo.service.TestMeetPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    private final TestMeetPersonalService personalService;
    @Autowired
    public TestController(TestMeetPersonalService personalService){
        this.personalService=personalService;
    }
    @Autowired
    private MongoTemplate mongoTemplate;

//    @GetMapping("/test")
//    public String home(HttpServletRequest request){
//
//        HttpSession httpSession=request.getSession();
//        SessionUser user=(SessionUser) httpSession.getAttribute("user");
//        System.out.println("user object:"+user);
//
////        httpSession.invalidate();
////        httpSession.removeAttribute("user");
////        SessionUser user= (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        System.out.println(user.getEmail());
//        return "test";
//    }



}

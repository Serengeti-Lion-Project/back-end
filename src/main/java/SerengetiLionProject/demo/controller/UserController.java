package SerengetiLionProject.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private MongoTemplate mongoTemplate;
//    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/users/new")
    public String createForm(){
        return "thymeleaf/users/createUserForm";
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
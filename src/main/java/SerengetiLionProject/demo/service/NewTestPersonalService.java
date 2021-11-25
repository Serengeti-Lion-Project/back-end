package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.domain.TestPersonal;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;
import SerengetiLionProject.demo.repository.NewMeetPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class NewTestPersonalService {

    private final NewMeetPersonalRepository personalRepository;

    //TestMeetPersonalService : SpringCOnfig에서 등록록록록록
    @Autowired
    public NewTestPersonalService(NewMeetPersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }


    public String saveNewUser(TestPersonal person) {
        String existStatus = validateDuplicateMember(person); //중복 회원 검증
        if (existStatus.equals("notexist")) {
            personalRepository.save(person);
            return person.getName();
        } else if (existStatus.equals("loginSuccess")) {
            return "userCheckSuccess";
        } else {
            return "userNameExist, Pwd Check Fail";
        }

    }

    private String validateDuplicateMember(TestPersonal person) {
        TestPersonal getUser = personalRepository.findOneByNameandUrl(person.getName(), person.getUrl_id(), person.getTitle());
        if (getUser == null) {
            return "notexist";
        }
        TestPersonal getUserPwd = personalRepository.checkPwd(person.getUpw(), person.getName(), person.getUrl_id(), person.getTitle());
        if (getUserPwd == null) {
            return "wrongpwd";
        } else {
            return "loginSuccess";
        }
    }

    /**
     * 현재 url, title에 대한 전체 회원 조회
     */

    public List<TestPersonal> findAll(Long url_id, String title) {
        return personalRepository.findAllByUrlandTitle(url_id,title);
    }

    public TestPersonal updatePersonalMeet(Long url_id, String name,ArrayList<Integer> mon, ArrayList<Integer> tue, ArrayList<Integer> wed,
                                           ArrayList<Integer> thu, ArrayList<Integer> fri, ArrayList<Integer> sat,
                                           ArrayList<Integer> sun){
        return personalRepository.findAndModifyByUrlIdandName(url_id,name,mon,tue,wed,thu,fri,sat,sun);
    }
}

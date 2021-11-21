package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class TestMeetPersonalService {

    private final MeetPersonalRepository personalRepository;

    //TestMeetPersonalService : SpringCOnfig에서 등록록록록록
   public TestMeetPersonalService(MeetPersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }


    public String saveNewUser(MeetPersonal person) {
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

    private String validateDuplicateMember(MeetPersonal person) {
        MeetPersonal getUser = personalRepository.findOneByNameandUrl(person.getName(), person.getUrl_id(), person.getTitle());
        if (getUser == null) {
            return "notexist";
        }
        MeetPersonal getUserPwd = personalRepository.checkPwd(person.getUpw(), person.getName(), person.getUrl_id(), person.getTitle());
        if (getUserPwd == null) {
            return "wrongpwd";
        } else {
            return "loginSuccess";
        }
    }

    /**
     * 현재 url, title에 대한 전체 회원 조회
     */

    public List<MeetPersonal> findAll(Long url_id, String title) {
        return personalRepository.findAllByUrlandTitle(url_id,title);
    }
}

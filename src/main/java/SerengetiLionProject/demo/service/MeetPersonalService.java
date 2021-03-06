package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class MeetPersonalService {

    private final MeetPersonalRepository personalRepository;

    //TestMeetPersonalService : SpringCOnfig에서 등록록록록록
    @Autowired
    public MeetPersonalService(MeetPersonalRepository personalRepository) {
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

    public MeetPersonal updatePersonalMeet(Long url_id, String name, ArrayList<ArrayList> availability){
        return personalRepository.findAndModifyByUrlIdandName(url_id,name,availability);
    }

    public MeetPersonal findOneByName(Long url_id, String name){

        return personalRepository.findOne(url_id,name);
    }

    public int[][] findOnesAvailability(MeetPersonal personal, int total_time, int total_date){
        int[][] personal_array=new int[total_time][total_date];
        if(personal==null)
            return personal_array;
        ArrayList<ArrayList<Integer>> availability=personal.getAvailability();
        if(!availability.isEmpty()) {
            for (int i = 0; i < total_time; i++) {
                for (int j = 0; j < total_date; j++) {
                    personal_array[i][j] += availability.get(i).get(j);
                }
            }
        }
        return personal_array;
    }
}

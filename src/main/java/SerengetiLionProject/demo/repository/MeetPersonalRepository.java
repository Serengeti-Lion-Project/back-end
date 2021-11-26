package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetPersonal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MeetPersonalRepository {

    MeetPersonal save(MeetPersonal person);
    MeetPersonal findOneByNameandUrl(String name, Long url_id, String title);
    List<MeetPersonal> findAllByUrlandTitle(Long url_id, String title);
    MeetPersonal checkPwd(String upw, String name, Long url_id, String title);
    MeetPersonal findAndModifyByUrlIdandName(Long url_id, String name, ArrayList<ArrayList> availability);
//    List<TestMeetPersonal> findAll();
}

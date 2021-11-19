package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.TestMeetPersonal;

import java.util.List;
import java.util.Optional;

public interface MeetPersonalRepository {

    TestMeetPersonal save(TestMeetPersonal person);
    TestMeetPersonal findOneByNameandUrl(String name,Long url_id, String title);
    List<TestMeetPersonal> findAllByUrlandTitle(Long url_id,String title);
    TestMeetPersonal checkPwd(String upw,String name,Long url_id,String title);
//    List<TestMeetPersonal> findAll();
}

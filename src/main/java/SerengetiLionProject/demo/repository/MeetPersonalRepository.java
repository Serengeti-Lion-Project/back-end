package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetPersonal;

import java.util.List;

public interface MeetPersonalRepository {

    MeetPersonal save(MeetPersonal person);
    MeetPersonal findOneByNameandUrl(String name, Long url_id, String title);
    List<MeetPersonal> findAllByUrlandTitle(Long url_id, String title);
    MeetPersonal checkPwd(String upw, String name, Long url_id, String title);
//    List<TestMeetPersonal> findAll();
}

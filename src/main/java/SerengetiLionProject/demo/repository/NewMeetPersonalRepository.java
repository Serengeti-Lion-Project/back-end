package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.domain.TestPersonal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface NewMeetPersonalRepository {

    TestPersonal save(TestPersonal person);
    TestPersonal findOneByNameandUrl(String name, Long url_id, String title);
    List<TestPersonal> findAllByUrlandTitle(Long url_id, String title);
    TestPersonal checkPwd(String upw, String name, Long url_id, String title);
    TestPersonal findAndModifyByUrlIdandName(Long url_id, String name,ArrayList<Integer> mon, ArrayList<Integer> tue, ArrayList<Integer> wed,
                                      ArrayList<Integer> thu, ArrayList<Integer> fri, ArrayList<Integer> sat,
                                      ArrayList<Integer> sun);
//    List<TestMeetPersonal> findAll();
}

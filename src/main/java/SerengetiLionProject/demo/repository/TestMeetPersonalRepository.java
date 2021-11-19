package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.TestMeetPersonal;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;


public class TestMeetPersonalRepository implements MeetPersonalRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TestMeetPersonal save(TestMeetPersonal person){
//        TestMeetPersonal person = new TestMeetPersonal(attributes.getName(),attributes.getEmail(),"", Role.USER);
        mongoTemplate.insert(person);
        return person;
    }

    @Override
    public TestMeetPersonal findOneByNameandUrl(String name,Long url_id, String title){
        //null return? -> save
        //not null return? -> checkPwd -> ok? login / nok? fail
//        String str="SELECT e FROM TestMeetPersonal e WHERE e.url_id = :url_id AND e.title = :title AND e.name=:name";
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("url_id").is(url_id))
                .addCriteria(Criteria.where("title").is(title));
        TestMeetPersonal person = mongoTemplate.findOne(query, TestMeetPersonal.class);
        return person;
    }

    @Override
    public List<TestMeetPersonal> findAllByUrlandTitle(Long url_id,String title){
//        String query="SELECT e FROM TestMeetPersonal e WHERE e.url_id = :url_id AND e.title = :title";
        Query query = new Query();
        query.addCriteria(Criteria.where("url_id").is(url_id))
                .addCriteria(Criteria.where("title").is(title));
        List<TestMeetPersonal> list= mongoTemplate.find(query, TestMeetPersonal.class);
        return list;
    }

    public TestMeetPersonal checkPwd(String upw,String name,Long url_id,String title){
//        String query="SELECT m FROM TestMeetPersonal m WHERE m.url_id = :url_id AND m.title = :title AND m.name=:name AND m.upw=:upw";
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("url_id").is(url_id))
                .addCriteria(Criteria.where("title").is(title))
                .addCriteria(Criteria.where("upw").is(upw));
        TestMeetPersonal person = mongoTemplate.findOne(query, TestMeetPersonal.class);
        return person;
    }
}


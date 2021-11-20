package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetPersonal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;


public class TestMeetPersonalRepository implements MeetPersonalRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MeetPersonal save(MeetPersonal person){
//        TestMeetPersonal person = new TestMeetPersonal(attributes.getName(),attributes.getEmail(),"", Role.USER);
        mongoTemplate.insert(person);
        return person;
    }

    @Override
    public MeetPersonal findOneByNameandUrl(String name, Long url_id, String title){
        //null return? -> save
        //not null return? -> checkPwd -> ok? login / nok? fail
//        String str="SELECT e FROM TestMeetPersonal e WHERE e.url_id = :url_id AND e.title = :title AND e.name=:name";
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("url_id").is(url_id))
                .addCriteria(Criteria.where("title").is(title));
        MeetPersonal person = mongoTemplate.findOne(query, MeetPersonal.class);
        return person;
    }

    @Override
    public List<MeetPersonal> findAllByUrlandTitle(Long url_id, String title){
//        String query="SELECT e FROM TestMeetPersonal e WHERE e.url_id = :url_id AND e.title = :title";
        Query query = new Query();
        query.addCriteria(Criteria.where("url_id").is(url_id))
                .addCriteria(Criteria.where("title").is(title));
        List<MeetPersonal> list= mongoTemplate.find(query, MeetPersonal.class);
        return list;
    }

    public MeetPersonal checkPwd(String upw, String name, Long url_id, String title){
//        String query="SELECT m FROM TestMeetPersonal m WHERE m.url_id = :url_id AND m.title = :title AND m.name=:name AND m.upw=:upw";
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("url_id").is(url_id))
                .addCriteria(Criteria.where("title").is(title))
                .addCriteria(Criteria.where("upw").is(upw));
        MeetPersonal person = mongoTemplate.findOne(query, MeetPersonal.class);
        return person;
    }
}


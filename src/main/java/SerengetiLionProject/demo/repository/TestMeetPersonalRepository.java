package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetPersonal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


// when2meet 개인
@Repository
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

//    public TestPersonal findAndModifyByUrlIdandName(Long url_id, String name,ArrayList<Integer> mon,ArrayList<Integer> tue,ArrayList<Integer> wed,
//                                             ArrayList<Integer> thu, ArrayList<Integer> fri,ArrayList<Integer> sat,
//                                             ArrayList<Integer> sun){
//        Query query=new Query();
//        query.addCriteria(Criteria.where("url_id").is(url_id));
//        query.addCriteria(Criteria.where("name").is(name));
//        Update update=new Update();
//        update.set("first",mon);
//        update.set("second",tue);
//        update.set("third",wed);
//        update.set("fourth",thu);
//        update.set("fifth",fri);
//        update.set("sixth",sat);
//        update.set("seventh",sun);
//
//        TestPersonal personal = mongoTemplate.findAndModify(query,update,TestPersonal.class);
//        return personal;


    @Override
    public MeetPersonal findAndModifyByUrlIdandName(Long url_id, String name, ArrayList<ArrayList> availability){
        Query query=new Query();
        query.addCriteria(Criteria.where("url_id").is(url_id));
        query.addCriteria(Criteria.where("name").is(name));
        Update update=new Update();
        update.set("availability",availability);

        MeetPersonal personal=mongoTemplate.findAndModify(query,update, MeetPersonal.class);
        return personal;
    }
}


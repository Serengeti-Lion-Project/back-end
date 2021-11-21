package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class TestMeetGroupRepository implements MeetGroupRepository{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MeetGroup save(MeetGroup meetGroup) {
//        onceMember.setUrl_id(++sequence);               // store에 넣기 전에 url_id를 세팅해줌(자동 증가)
        mongoTemplate.insert(meetGroup);
        return meetGroup;
    }

    @Override
    public MeetGroup findByUrl_id(Long url_id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("url_id").is(url_id));
        MeetGroup group=mongoTemplate.findOne(query,MeetGroup.class);
        return group;  // store.get(url_id) 로 해도 되지만 혹시 Null 나올 수도 있으니까
    }

    @Override
    public MeetGroup findByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        MeetGroup group=mongoTemplate.findOne(query,MeetGroup.class);
        return group;//같은 경우만 필터링되고 찾으면 반환. findAny는 하나라도 찾는걸 의미함. 끝까지 돌았는데 없으면 optional 에 null 이 포함되서 반환됨.
    }

    @Override
    public List<MeetGroup> findAll() {
        return mongoTemplate.findAll(MeetGroup.class);
    }
}

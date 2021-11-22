package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class TestFinalScheduleRepository implements FinalScheduleRepository{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public FinalSchedule save(FinalSchedule schedule) {
//        onceMember.setUrl_id(++sequence);               // store에 넣기 전에 url_id를 세팅해줌(자동 증가)
        mongoTemplate.insert(schedule);
        return schedule;
    }
    @Override
    public List<FinalSchedule> findAllByTeamId(Long team_id){
        return mongoTemplate.find(Query.query(Criteria.where("team_id").is(team_id)), FinalSchedule.class);
    }
}

package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class TestTeamRepository implements TeamRepository{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Team save(Team team){
        mongoTemplate.insert(team);
        return team;
    }

    @Override
    public Team findById(Long tid){
        Team team=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(tid)),Team.class);
        return team;
    }
}

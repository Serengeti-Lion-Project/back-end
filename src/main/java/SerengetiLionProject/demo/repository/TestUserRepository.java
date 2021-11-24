package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TestUserRepository implements UserRepository{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public User findByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        User user=mongoTemplate.findOne(query, User.class);
        return user;
    }

    @Override
    public User saveTeamId(String teamIds, String email,Long teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        System.out.println("teamIds+teamId = " + teamIds+teamId);
        User user=mongoTemplate.findAndModify(
                query,
                Update.update("team_id", teamIds+teamId+','),
                User.class
        );
        return user;
    }
}

package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.AvailableTime;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class TestAvailableTimeRepository implements AvailableTimeRepository {
    private MongoTemplate mongoTemplate;
    @Override
    public AvailableTime save(AvailableTime availableTime) {
        mongoTemplate.insert(availableTime);
        return availableTime;
    }

    @Override
    public AvailableTime findByUrl_id(Long url_id) {
        return null;
    }

    @Override
    public AvailableTime findByTitle(String title) {
        return null;
    }

    @Override
    public List<AvailableTime> findAll() {
        return null;
    }
}

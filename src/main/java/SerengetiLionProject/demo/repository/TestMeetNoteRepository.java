package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.MeetNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public class TestMeetNoteRepository implements MeetNoteRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MeetNote save(MeetNote meetNote){
        mongoTemplate.insert(meetNote);
        return meetNote;
    }
    @Override
    public MeetNote findByTeam_idAndNote_id(Long team_id, Long note_id) {                // team_id, note_id에 맞는 하나의 회의록 가져옴
        Query query = new Query();                                             // 회의록 상세페이지 가져올때 사용
        query.addCriteria(Criteria.where("team_id").is(team_id))
                .addCriteria(Criteria.where("note_id").is(note_id));
        MeetNote note = mongoTemplate.findOne(query,MeetNote.class);
        return note;
        }
    @Override
    public List<MeetNote> findAllByTeam_id(Long team_id) {               // team_id에 맞는 모든 회의록 가져옴
        Query query = new Query();                                       // 팀별화면 메인페이지에서 해당 팀의 모든 회의록 가져올때 사용
        query.addCriteria(Criteria.where("team_id").is(team_id));
        List<MeetNote> list = mongoTemplate.find(query,MeetNote.class);
        return list;
    }

/**                 안쓸거 같아서 일단 주석
    @Override
    public List<MeetNote> findAll() {
        return mongoTemplate.findAll(MeetNote.class);
    }
 */

}

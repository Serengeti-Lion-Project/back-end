package SerengetiLionProject.demo.service;


import SerengetiLionProject.demo.domain.AutoIncrementSequence;
import SerengetiLionProject.demo.domain.NewAutoIncrementSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
public class NewSequenceGeneratorService { // uid - autoincrement 를 위해 필요한 클래스
    private final MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        NewAutoIncrementSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                NewAutoIncrementSequence.class);
        System.out.println("check new counter: "+counter.getSeq());
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
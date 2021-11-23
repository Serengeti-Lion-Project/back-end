package SerengetiLionProject.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "team_auto_sequence") // uid - autoincrement 를 위해 필요한 클래스
public class NewAutoIncrementSequence {
    @Id
    private String id;
    private Long seq;
}
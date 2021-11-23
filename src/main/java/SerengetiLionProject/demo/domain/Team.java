package SerengetiLionProject.demo.domain;

import SerengetiLionProject.demo.dto.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="team")
@Data
public class Team {
    @Transient
    public static final String SEQUENCE_NAME = "auto_sequence";

    @Id
    private Long _id; // auto increment 적용
    private String name;

    @Builder
    public Team(String name){
        this.name=name;
    }
}

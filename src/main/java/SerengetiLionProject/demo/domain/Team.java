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
    private Long _id; // auto increment 적용 (팀id)
    private String name;
    private Long leader_uid; // 팀장 id

    @Builder
    public Team(String name, Long leader_uid) {
        this.name = name;
        this.leader_uid = leader_uid;
    }
}

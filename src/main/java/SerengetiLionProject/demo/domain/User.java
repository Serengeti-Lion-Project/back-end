package SerengetiLionProject.demo.domain;

import SerengetiLionProject.demo.dto.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
@Data
public class User {
//    @Id
//    private String id; // mongoDB에 들어갈 때 자동생성되는 object id 값
    /*
        @Id로 지정된 field가 있으면 MongoDB의 _id로 맵핑된다.
        만약 @Id로 지정된 field가 없다면 id란 이름의 field가 MongoDB의 _id로 맵핑된다.
    */

    @Transient
    public static final String SEQUENCE_NAME = "auto_sequence";

    @Id
    private Long _id; // auto increment 적용
    private String team_id; // , 기준으로 자르게 string으로 넣을게요. (기본값은 "")
    private String name;
    private String email;
    private String nickname;
    private Role role;

    @Builder
    public User(String name, String email, String nickname, Role role, String team_id) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.team_id=team_id;
    }
    public User update(String name) {
        this.name = name;
        return this;
    }
    public String getRoleKey() {
        return this.role.getKey();
    }
}
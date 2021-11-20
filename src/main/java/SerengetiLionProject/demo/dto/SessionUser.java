package SerengetiLionProject.demo.dto;

//import SerengetiLionProject.config.auth.userTest.TestUser;
import SerengetiLionProject.demo.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * user를 세션에 저장하기 위한? 그런,, 클래스라고 하네요
 */
@Getter
@Setter
public class SessionUser implements Serializable {
    private String name, email,nickname, team_id;
    private Long uid;
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname=user.getNickname();
        this.uid=user.get_id();
    }
}

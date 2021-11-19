package SerengetiLionProject.demo.dto;

//import SerengetiLionProject.config.auth.userTest.TestUser;
import SerengetiLionProject.demo.domain.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * user를 세션에 저장하기 위한? 그런,, 클래스라고 하네요
 */
@Getter
public class SessionUser implements Serializable {
    private String name, email,nickname;
    private Long team_id,uid;
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname="";
        //this.team_id=user.getTeam_id();
        this.uid=user.get_id();
    }
}

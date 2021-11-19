package SerengetiLionProject.demo.auth;


import SerengetiLionProject.demo.domain.User;
import SerengetiLionProject.demo.dto.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * 소셜로그인해서 구글로 부터 받아오는 정보를 저장 + User Entity에 값 넘겨주는 부분
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey, name, email;
    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //여기서 User Entity에 넣어줄 값을 설정합니다! (구글이 넘겨준 값 + 우리가 지정한 값)
    /*public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)   //어떤 ROLE이 들어갈지 여기서 우리가 직접 지정해줌!
                .build();
    }*/
}

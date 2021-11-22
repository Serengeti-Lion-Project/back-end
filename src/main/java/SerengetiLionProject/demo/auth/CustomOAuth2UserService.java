package SerengetiLionProject.demo.auth;

import SerengetiLionProject.demo.domain.User;
import SerengetiLionProject.demo.dto.Role;
import SerengetiLionProject.demo.dto.SessionUser;
import SerengetiLionProject.demo.service.generator.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
/**
 * 소셜 로그인->회원가입 및 회원정보 update, 세션 저장 등 하는 부분
 */
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // -> 근데 우리는 구글만 구현하니까 이 부분은 없어됨 (attribute 담을 때도 얘는 저장 안함)
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        // oauth2 로그인 진행 시 키가 되는 필드값 -> 사실 무슨 소린지 모르겠어요~~
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuthAttributes: attribute를 담을 클래스 (개발자가 생성)
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);     //현재 접속한 개체의 정보를 저장하거나 (디비에 없다면) 업데이트하거나 (디비에 있다면) -> 현재 자바 파일 맨 아래에 메소드 정의
        // SessionUser: 세션에 사용자 정보를 저장하기 위한 DTO 클래스 (개발자가 생성)
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    //TestUser (User) entity에 들어갈 정보들을 설정함 (해당 정보들로 진짜 '객체' 를 생성하는건 OAuthAttributes에서 수행)
    private User saveOrUpdate(OAuthAttributes attributes) {
        //디비에 이미 존재하는 이메일이라면 (중복불가인 영역 -> 우리는 닉네임이 중복불가면 그걸로 하면 될 것 같아요)
        // -> save가 아니라 update 수행

        User existUser = mongoTemplate.findOne( // email이 구글에서 받아온 email과 같은 애가 user객체 유무 확인
                Query.query(Criteria.where("email").is(attributes.getEmail())),
                User.class
        );

        if(existUser!=null) { // DB에 존재하는 유저라면
            User user = mongoTemplate.findAndModify( // email 값을 다시 찾아서 name 값 수정
                    Query.query(Criteria.where("email").is(attributes.getEmail())),
                    Update.update("name",attributes.getName()),
                    User.class
            );
            return user;
        }

        // DB에 존재하지 않는 유저라면 user 객체 생성, 처음 생성시에는 ""으로 team_id가 설정! (어떤 팀도 없는 상태)
        User user = new User(attributes.getName(),attributes.getEmail(),"", Role.USER,"");
        /**
         * 디폴트 Role: USER / 팀장일 경우 체크 -> Role.LEAD 로 지정 (LEADER의미)
         */
        mongoTemplate.insert(user);
        return user;
    }
}
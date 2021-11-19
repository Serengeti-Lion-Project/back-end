package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    LEAD("ROLE_LEADER", "팀장"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}

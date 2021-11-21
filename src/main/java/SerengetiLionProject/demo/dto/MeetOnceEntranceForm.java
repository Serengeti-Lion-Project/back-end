package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 일회용 입장 전 입력하는 데이터를 담을 DTO
public class MeetOnceEntranceForm {
    private String url_id;
    private String title;
    private String name;
    private String upw;
    private String page_pw;
}

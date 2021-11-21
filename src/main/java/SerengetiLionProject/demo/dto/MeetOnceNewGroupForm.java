package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 일회성 만드는 페이지에서 입력받는 데이터
public class MeetOnceNewGroupForm {
    private String title;
    private String start_date;
    private String end_date;
    private Integer start_time;
    private Integer end_time;
    private String page_pw;
}

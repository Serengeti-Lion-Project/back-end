package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 일회용 입장 후 가능 시간을 체크한 데이터 받는 form
public class MeetOnceAvailableTimeForm {
    private String start_date;
    private String end_date;
    private Integer start_time;
    private Integer end_time;
    private Long urlid;
    private String title;
    private String name;
}

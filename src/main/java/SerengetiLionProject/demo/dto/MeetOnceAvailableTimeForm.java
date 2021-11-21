package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 일회용 입장 후 가능 시간을 체크한 데이터 받는 form
public class MeetOnceAvailableTimeForm {
    private String day; // 요일
    private String start_time;
    private String end_time;
}

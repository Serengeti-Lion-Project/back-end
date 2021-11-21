package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetTeamFinalDateForm {
    private String final_date;
    private Integer start_hour;
    private Integer start_min;
    private Integer end_hour;
    private Integer end_min;
    private Long team_id;
    private String title;
}

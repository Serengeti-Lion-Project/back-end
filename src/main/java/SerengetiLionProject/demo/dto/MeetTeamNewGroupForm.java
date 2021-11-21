package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetTeamNewGroupForm {
        private String title;
        private String start_date;
        private String end_date;
        private Integer start_time;
        private Integer end_time;
        private Long team_id;
}

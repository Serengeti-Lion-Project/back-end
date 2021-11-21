package SerengetiLionProject.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Document(collection="final_schedule")
@Data
public class FinalSchedule {
    @Transient
    public static final String SEQUENCE_NAME = "auto_sequence";

    //id자동증가 해주는 거, team_id 가져오는거 만들어야함
    //사용자가 팀 클릭 -> 팀페이지: teampage/{teamid} 여기에서 meet생성할 때 request url에서 teamid가져오기
    @Id
    private Long date_id;
    private Long team_id; //얘는 하나의 확정날짜에 대해 하나의 team만 존재하니까 String 아니어도 됨
    private String schedule_title;
    private String final_date;
    private Integer final_start_hour;
    private Integer final_end_hour;
    private Integer final_start_min;
    private Integer final_end_min;

    @Builder
    public FinalSchedule(Long team_id, String title, String date, Integer sh, Integer sm, Integer eh, Integer em) {
        this.team_id=team_id;  //현재 팀의 team_id값 파싱해와서 저장
        this.schedule_title=title;
        this.final_date=date;
        this.final_start_hour=sh;
        this.final_start_min=sm;
        this.final_end_hour=eh;
        this.final_end_min=em;
    }
}

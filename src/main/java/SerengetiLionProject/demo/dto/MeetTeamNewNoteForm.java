package SerengetiLionProject.demo.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MeetTeamNewNoteForm {
    private String note_title;
    private String note_content;
    private String write_date;

    private Long team_id;
    private Long note_id;

}

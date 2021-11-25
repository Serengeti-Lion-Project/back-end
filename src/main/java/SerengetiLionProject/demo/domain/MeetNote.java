package SerengetiLionProject.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection="meetnote")
@Data
public class MeetNote {
    @Transient
    public static final String SEQUENCE_NAME = "auto_sequence";

    @Id
    private Long note_id;
    private String note_title;       // 제목
    private String note_content;       // 내용
    private String write_date;  // date 타입 스트링 괜찮은지 확인해야함
    private Long team_id;


    @Builder
    public MeetNote(String note_title, String note_content, String write_date) {
        this.note_title = note_title;
        this.note_content=note_content;
        this.write_date=write_date;

    }
}

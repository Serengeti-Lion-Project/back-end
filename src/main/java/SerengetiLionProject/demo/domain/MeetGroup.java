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
@Document(collection="meetgroup")
@Data
public class MeetGroup {
        @Transient
        public static final String SEQUENCE_NAME = "auto_sequence";

        @Id
        private Long url_id;     // int 형으로 자동 증가
        private String title;       // 제목
        private String start_date;  // date 타입 스트링 괜찮은지 확인해야함
        private String end_date;
        private Integer start_time;  // time 타입 스트링 괜찮은지 확인해야함
        private Integer end_time;
        private String page_pw;

        @Builder
        public MeetGroup(String title, String start_date, String end_date, Integer start_time, Integer end_time, String page_pw) {
                this.title = title;
                this.start_date=start_date;
                this.end_date=end_date;
                this.start_time=start_time;
                this.end_date=end_date;
                this.page_pw=page_pw;
        }
}

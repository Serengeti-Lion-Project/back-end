package SerengetiLionProject.demo.domain;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="available_time")
@Data
public class AvailableTime {

    private Long url_id;
    private String title;
    private String name;
    private String day;
    private String start_time;
    private String end_time;

    @Builder
    public AvailableTime(Long url_id, String title, String name, String day, String start_time, String end_time) {
        // 접속해있는 url 정보
        this.url_id = url_id;
        this.title = title;
        this.name = name;

        // 가능한 요일/시간 데이터
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
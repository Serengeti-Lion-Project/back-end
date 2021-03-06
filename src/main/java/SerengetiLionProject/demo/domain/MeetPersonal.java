package SerengetiLionProject.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
@Document(collection="2d_meet_personal")
@Data
public class MeetPersonal {

    private Long url_id;
    private String title;
    private String name;
    private String upw;

    private ArrayList<ArrayList<Integer>> availability=new ArrayList<ArrayList<Integer>>();


    @Builder
    public MeetPersonal(Long url_id, String title, String name, String upw) {
        this.url_id = url_id; //자기가 접속한 url에서 id값 파싱해와서 저장
        this.title = title; //마찬가지로 접속한 url에서 파싱
        this.name = name; //일회성 가정하고 만드니까 일단은 여기서 객체 생성과 동시에 초기화하게 할게요!
        this.upw = upw;
    }

}

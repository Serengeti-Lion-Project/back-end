package SerengetiLionProject.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;

@Getter
@Setter
@Document(collection="test_personal")
@Data
public class TestPersonal {

    private Long url_id;
    private String title;
    private String name;
    private String upw;

    private ArrayList<Integer> first=new ArrayList<>();  //ArrayList를 쓰는 이유? -> 프론트에서 넘겨주는 2차원 배열을 각각 arraylist로 변환시켜 저장
    //그렇다면 왜 배열이 아니라 list인가요?
    //각 팀별로 가능한 총 시간 길이가 달라지기 때문에 길이 가변성이 있는 list를 사용했습니다~
    private ArrayList<Integer> second=new ArrayList<>();
    private ArrayList<Integer> third=new ArrayList<>();
    private ArrayList<Integer> fourth=new ArrayList<>();
    private ArrayList<Integer> fifth=new ArrayList<>();
    private ArrayList<Integer> sixth=new ArrayList<>();
    private ArrayList<Integer> seventh=new ArrayList<>();


    @Builder
    public TestPersonal(Long url_id, String title, String name, String upw) {
        this.url_id = url_id; //자기가 접속한 url에서 id값 파싱해와서 저장
        this.title = title; //마찬가지로 접속한 url에서 파싱
        this.name = name; //일회성 가정하고 만드니까 일단은 여기서 객체 생성과 동시에 초기화하게 할게요!
        this.upw = upw;
    }

}

package SerengetiLionProject.demo.repository;
import SerengetiLionProject.demo.domain.AvailableTime;
import java.util.List;

public interface AvailableTimeRepository {
    AvailableTime save(AvailableTime availableTime); // 가능시간 저장
    AvailableTime findByUrl_id(Long url_id); // domain.OnceMember 에서 url_id 로 해당하는 객체 찾음
    // 특정 사람의 가능한 시간대 모두 가져오기
    AvailableTime findByTitle(String title); // title 로 객체 찾음
    List<AvailableTime> findAll(); //
}
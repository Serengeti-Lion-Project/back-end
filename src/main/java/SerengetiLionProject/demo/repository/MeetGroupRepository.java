package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.MeetGroup;

import java.util.List;
import java.util.Optional;

public interface MeetGroupRepository {
    MeetGroup save(MeetGroup onceMember);         // save를 통해 객체 저장함.
    MeetGroup findByUrl_id(Long url_id); // domain.OnceMember 에서 url_id 로 해당하는 객체 찾음
    MeetGroup findByTitle(String title); // title 로 객체 찾음
    MeetGroup findByTeam(Long team_id); // 팀 아이디로 meeting 찾기
    List<MeetGroup> findAll();                     // 지금까지 저장된 모든 것들 리스트로 반환
}

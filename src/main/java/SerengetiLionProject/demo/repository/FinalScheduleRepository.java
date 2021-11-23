package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.FinalSchedule;

import java.util.List;

public interface FinalScheduleRepository {
    FinalSchedule save(FinalSchedule schedule);
    List<FinalSchedule> findAllByTeamId(Long team_id);
}

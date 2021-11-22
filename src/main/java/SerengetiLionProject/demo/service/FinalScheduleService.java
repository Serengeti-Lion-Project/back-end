package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.repository.FinalScheduleRepository;
import SerengetiLionProject.demo.repository.MeetGroupRepository;

import java.util.List;

public class FinalScheduleService {
    private FinalScheduleRepository finalScheduleRepository;

    public FinalScheduleService(FinalScheduleRepository finalScheduleRepository) {
        this.finalScheduleRepository=finalScheduleRepository;
    }

    //일회성 만드는 페이지에 내용들을 다 저장함.
    public Long saveFinalSchedule(FinalSchedule schedule) {
        finalScheduleRepository.save(schedule);
        return schedule.getDate_id();
    }

    public List<FinalSchedule> findAllbyTeamId(Long tid){
        return finalScheduleRepository.findAllByTeamId(tid);
    }
}

package SerengetiLionProject.demo.service;
import SerengetiLionProject.demo.domain.AvailableTime;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.repository.AvailableTimeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;

    public AvailableTimeService(AvailableTimeRepository availableTimeRepository) {
        this.availableTimeRepository = availableTimeRepository;
    }

    // 가능한 시간대 체크한 거 db에 저장
    public Long saveTime(AvailableTime availableTime) {
        AvailableTime time = availableTimeRepository.save(availableTime);
        return time.getUrl_id();
    }

    public List<AvailableTime> findAll(){
        return availableTimeRepository.findAll(); //일회성 만드는 페이지에서 저장한 모든 내용들 보여줌.
    }
}


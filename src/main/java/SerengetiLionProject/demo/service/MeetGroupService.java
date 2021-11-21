package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.repository.MeetGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MeetGroupService {
    private final MeetGroupRepository groupRepository;

    public MeetGroupService(MeetGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    //일회성 만드는 페이지에 내용들을 다 저장함.
    public Long SaveGroup(MeetGroup onceMember){
        groupRepository.save(onceMember);
        return onceMember.getUrl_id();
    }

    public List<MeetGroup> findAll(){
        return groupRepository.findAll(); //일회성 만드는 페이지에서 저장한 모든 내용들 보여줌.
    }
    public MeetGroup findOne(Long url_id){
        return groupRepository.findByUrl_id(url_id);
    }
}

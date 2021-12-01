package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.domain.MeetPersonal;
import SerengetiLionProject.demo.repository.MeetGroupRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class MeetGroupService {
    private final MeetGroupRepository groupRepository;

    public MeetGroupService(MeetGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    //일회성 만드는 페이지에서 폼으로 들어온 내용들을 다 저장함.
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

    public int[][] findTotalAvailability(MeetGroup group, List<MeetPersonal> total_personal){
        String[] splited=group.getStart_date().split("/");
        Integer start_date=Integer.parseInt(splited[1]);
        splited=group.getEnd_date().split("/");
        Integer end_date=Integer.parseInt(splited[1]);
        Integer total_date=end_date-start_date+1;
        Integer total_time=group.getEnd_time()-group.getStart_time()+1;

        int[][] avail_array=new int[total_time][total_date];

        if(total_personal.isEmpty())
            return avail_array;
        //url_id로 해당 그룹의 모든 사람 정보 가져오는거 나중에 service로 뺴주기 (전체 가능시간: meetgroup으로, 한 사람의 가능시간: meetpersonal로)
        for(MeetPersonal one:total_personal){
            ArrayList<ArrayList<Integer>> avail=one.getAvailability();
            for(int i=0;i<avail.size();i++){
                for(int j=0;j<avail.get(i).size();j++){
                    avail_array[i][j]+=avail.get(i).get(j);
                }
            }
        }

        return avail_array;
    }

    public int[] calcTotalTimeandDate(MeetGroup group){
        String[] splited=group.getStart_date().split("/");
        int start_date=Integer.parseInt(splited[1]);
        splited=group.getEnd_date().split("/");
        int end_date=Integer.parseInt(splited[1]);
        int total_date=end_date-start_date+1;
        int total_time=group.getEnd_time()-group.getStart_time()+1;
        int[] result={total_date,total_time};
        return result;
    }

    public List<MeetGroup> findAllbyTeamId(Long teamid){
        return groupRepository.findByTeam(teamid);
    }
}

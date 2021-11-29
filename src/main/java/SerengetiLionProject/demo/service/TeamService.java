package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.Team;
import SerengetiLionProject.demo.repository.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TeamService {
    private final TeamRepository teamRepository;

    //TestMeetPersonalService : SpringCOnfig에서 등록
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository=teamRepository;
    }

    public Team saveTeam(Team team){
        if(team.getName().equals(""))
            return null;
        teamRepository.save(team);
        return team;
    }

    public Team findTeamById(Long tid) {
        return teamRepository.findById(tid);
    }
}

package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.Team;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;
import SerengetiLionProject.demo.repository.TeamRepository;

public class TeamService {
    private TeamRepository teamRepository;

    //TestMeetPersonalService : SpringCOnfig에서 등록록
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository=teamRepository;
    }
    public Team saveTeam(Team team){
        if(team.getName().equals(""))
            return null;
        teamRepository.save(team);
        return team;
    }

    public Team findTeamById(Long tid){
        return teamRepository.findById(tid);
    }
}

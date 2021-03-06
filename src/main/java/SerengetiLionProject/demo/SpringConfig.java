package SerengetiLionProject.demo;

import SerengetiLionProject.demo.repository.*;
import SerengetiLionProject.demo.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MeetPersonalService personalService(){
        return new MeetPersonalService(personalRepository());
    }

    @Bean
    public MeetPersonalRepository personalRepository(){
        return new TestMeetPersonalRepository();
    }

    @Bean
    public MeetGroupService meetGroupService(){
        return new MeetGroupService(groupRepository());
    }

    @Bean
    public MeetGroupRepository groupRepository(){
        return new TestMeetGroupRepository();
    }

    @Bean
    public FinalScheduleService finalScheduleService(){
        return new FinalScheduleService(finalScheduleRepository());
    }

    @Bean
    public FinalScheduleRepository finalScheduleRepository(){
        return new TestFinalScheduleRepository();
    }

    @Bean
    public TeamRepository teamRepository(){
        return new TestTeamRepository();
    }

    @Bean
    public TeamService teamService(){
        return new TeamService(teamRepository());
    }

    @Bean
    public MeetNoteRepository noteRepository() { return new TestMeetNoteRepository();}

    @Bean
    public MeetNoteService meetNoteService() {return new MeetNoteService(noteRepository()); }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new TestUserRepository();
    }

}
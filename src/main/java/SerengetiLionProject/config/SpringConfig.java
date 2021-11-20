package SerengetiLionProject.config;

import SerengetiLionProject.demo.repository.MeetGroupRepository;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;
import SerengetiLionProject.demo.repository.TestMeetGroupRepository;
import SerengetiLionProject.demo.repository.TestMeetPersonalRepository;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.TestMeetPersonalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public TestMeetPersonalService personalService(){
        return new TestMeetPersonalService(personalRepository());
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
}

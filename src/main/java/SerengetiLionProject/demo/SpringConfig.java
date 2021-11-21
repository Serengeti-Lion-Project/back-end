package SerengetiLionProject.demo;

import SerengetiLionProject.demo.repository.*;
import SerengetiLionProject.demo.service.AvailableTimeService;
import SerengetiLionProject.demo.service.FinalScheduleService;
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

    @Bean
    public FinalScheduleService finalScheduleService(){
        return new FinalScheduleService(finalScheduleRepository());
    }

    @Bean
    public FinalScheduleRepository finalScheduleRepository(){
        return new TestFinalScheduleRepository();
    }

    @Bean
    public AvailableTimeRepository availableTimeRepository(){
        return new TestAvailableTimeRepository();
    }

    @Bean
    public AvailableTimeService availableTimeService(){
        return new AvailableTimeService(availableTimeRepository());
    }
}

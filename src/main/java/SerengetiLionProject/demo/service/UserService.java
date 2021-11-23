package SerengetiLionProject.demo.service;

import SerengetiLionProject.demo.domain.User;
import SerengetiLionProject.demo.repository.MeetPersonalRepository;
import SerengetiLionProject.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

// 다회성 팀 생성 시 초대할 유저가 존재하는 유저인지 확인해야함
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User saveTeamId(String teamIds, String email, Long teamId) {
        return userRepository.saveTeamId(teamIds, email, teamId);
    }
}

package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.User;

public interface UserRepository {
    User findByEmail(String email);
    User saveTeamId(String teamIds, String email,Long teamId);
}

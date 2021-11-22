package SerengetiLionProject.demo.repository;

import SerengetiLionProject.demo.domain.Team;

public interface TeamRepository {
    Team save(Team team);
    Team findById(Long tid);
}

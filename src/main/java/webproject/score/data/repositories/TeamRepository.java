package webproject.score.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webproject.score.data.models.Team;
import webproject.score.data.models.User;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {
    Team findTeamByUserUsername(String username);
}

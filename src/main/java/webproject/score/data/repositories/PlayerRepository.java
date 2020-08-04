package webproject.score.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webproject.score.data.models.Player;
import webproject.score.data.models.Team;

import java.util.Set;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Set<Player> findPlayersByTeam(Team team);
    Set<Player> findPlayersByTeamId(String id);
}

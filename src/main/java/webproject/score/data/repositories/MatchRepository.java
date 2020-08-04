package webproject.score.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.score.data.models.Match;
import webproject.score.data.models.Team;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
    List<Match> findMatchesByRound(Integer round);
    List<Match> findMatchesByHomeTeamOrAwayTeam(Team team1, Team team2);
    Match findMatchById(String id);
}

package webproject.score.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.score.data.models.Goal;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, String> {
    List<Goal> findByMatchIdAndTeamId(String matchId, String teamId);
}

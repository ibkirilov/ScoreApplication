package webproject.score.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.score.data.models.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, String> {
    League getLeagueByName(String name);

}

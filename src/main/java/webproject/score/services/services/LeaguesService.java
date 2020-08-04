package webproject.score.services.services;

import webproject.score.data.models.League;
import webproject.score.data.models.Team;
import webproject.score.services.models.LeaguesServiceModel;

import java.util.List;

public interface LeaguesService {
    void initCreateLeagues();

    League getLeague() throws Exception;

    List<LeaguesServiceModel> getAllLeagues();

    Integer getRoundsPlayed();

    void roundPlayed();

    LeaguesServiceModel getLeagueByTeam(Team team);
}

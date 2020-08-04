package webproject.score.services.services;

import webproject.score.data.models.Team;
import webproject.score.services.models.*;

import java.util.List;
import java.util.Set;

public interface TeamsService {
    void initCreateTeams() throws Exception;

    Team create(TeamCreateServiceModel teamCreateServiceModel, String username) throws Exception;

    Double getTeamPower(Set<PlayerServiceModel> collect);

    Team getTeamByUsername(String username);

    void addPointsToTeams(MatchServiceModel match);

    List<TeamServiceModel> getTeamsByLeague(LeaguesServiceModel leaguesServiceModel);
}

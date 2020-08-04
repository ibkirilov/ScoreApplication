package webproject.score.services.services;

import webproject.score.data.models.Player;
import webproject.score.data.models.Team;
import webproject.score.services.models.PlayerServiceModel;

import java.util.List;
import java.util.Set;

public interface PlayersService {
    Set<Player> createSetOfPlayersForNewTeam(Team team);

    void deletePlayersByTeam(Team team);

    void changePlayersFormAfterTheMatches();

    List<PlayerServiceModel> getPlayersByTeamId(String teamId);
}

package webproject.score.services.factories.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import webproject.score.config.annotations.Factory;
import webproject.score.data.models.League;
import webproject.score.data.models.Team;
import webproject.score.data.repositories.TeamRepository;
import webproject.score.services.factories.TeamFactory;
import webproject.score.services.services.PlayersService;
import webproject.score.services.services.StadiumsService;

@Factory
public class TeamFactoryImpl implements TeamFactory {
    private final PlayersService playersService;
    private final StadiumsService stadiumsService;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamFactoryImpl(PlayersService playersService, StadiumsService stadiumsService, TeamRepository teamRepository) {
        this.playersService = playersService;
        this.stadiumsService = stadiumsService;
        this.teamRepository = teamRepository;
    }

    @Override
    public Team create(String name, League league, String stadiumName) {
        Team team = new Team();
        team.setLeague(league);
        team.setName(name);
        team.setPoints(0);

        return team;
    }
}

package webproject.score.services.factories.implementations;

import webproject.score.config.annotations.Factory;
import webproject.score.data.models.League;
import webproject.score.data.models.Team;
import webproject.score.services.factories.TeamFactory;

@Factory
public class TeamFactoryImpl implements TeamFactory {

    @Override
    public Team create(String name, League league) {
        Team team = new Team();
        team.setLeague(league);
        team.setName(name);
        team.setPoints(0);

        return team;
    }
}

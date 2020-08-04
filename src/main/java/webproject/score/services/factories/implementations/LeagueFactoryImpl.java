package webproject.score.services.factories.implementations;

import webproject.score.config.annotations.Factory;
import webproject.score.data.models.League;
import webproject.score.services.factories.LeagueFactory;

@Factory
public class LeagueFactoryImpl implements LeagueFactory {

    @Override
    public League create(String name) {
        League league = new League();
        league.setName(name);
        league.setRoundsPlayed(0);

        return league;
    }
}

package webproject.score.services.factories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.League;

import static org.junit.Assert.*;

public class LeagueFactoryTests extends TestBase {

    @Autowired
    private LeagueFactory leagueFactory;

    @Test
    public void create_createsNewLeagueWithGivenName_WhenFactoryCalled() {
        String leagueName = "First Test league";
        League league = new League();
        league.setName(leagueName);
        league.setRoundsPlayed(0);

        League leagueFromFactory = leagueFactory.create(leagueName);

        assertEquals(leagueFromFactory, league);
    }

    @Test
    public void create_createsNewLeagueWithGivenName_WhenFactoryCalledTwo() {
        String leagueName = "Second Test league";
        League league = new League();
        league.setName(leagueName);
        league.setRoundsPlayed(0);

        League leagueFromFactory = leagueFactory.create(leagueName);

        assertEquals(leagueFromFactory, league);
    }
}

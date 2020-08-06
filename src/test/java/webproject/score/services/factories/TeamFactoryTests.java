package webproject.score.services.factories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.League;
import webproject.score.data.models.Team;

import static org.junit.Assert.*;

public class TeamFactoryTests extends TestBase {

    @Autowired
    private TeamFactory teamFactory;

    @Test
    public void create_createsTeamByGivenArguments() {
        String teamName = "Test Team Name";
        Team team = new Team();
        League league = new League();
        team.setName(teamName);
        team.setPoints(0);
        team.setLeague(league);

        Team teamFromFactory = this.teamFactory.create(teamName, league);

        assertEquals(team, teamFromFactory);
        assertNotNull(teamFromFactory.getLeague());
    }
}

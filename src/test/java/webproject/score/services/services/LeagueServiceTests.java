package webproject.score.services.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.League;
import webproject.score.data.models.Team;
import webproject.score.data.repositories.LeagueRepository;
import webproject.score.errors.AllGroupsAreFullException;
import webproject.score.services.factories.LeagueFactory;
import webproject.score.services.models.LeaguesServiceModel;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class LeagueServiceTests extends TestBase {
    @Autowired
    LeaguesService leaguesService;

    @MockBean
    LeagueRepository leagueRepository;

    @Autowired
    LeagueFactory leagueFactory;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void getLeague_shouldThrowAllGroupsAreFullException() {
        Team team1 = new Team();
        Team team2 = new Team();
        Team team3 = new Team();
        Team team4 = new Team();
        List<Team> teams = List.of(team1, team2, team3, team4);
        League league = new League();
        league.setTeams(teams);

        Mockito.when(leagueRepository.getLeagueByName(any(String.class))).thenReturn(league);

        assertThrows(AllGroupsAreFullException.class, () -> this.leaguesService.getLeague());
    }

    @Test
    public void getLeague_shouldReturnLeague() throws Exception {
        Team team1 = new Team();
        Team team2 = new Team();
        Team team3 = new Team();
        List<Team> teams = List.of(team1, team2, team3);
        League league = new League();
        league.setTeams(teams);

        Mockito.when(leagueRepository.getLeagueByName(any(String.class))).thenReturn(league);

        assertEquals(league, this.leaguesService.getLeague());
    }

    @Test
    public void getRoundsPlayed_shouldReturnIntegerOfRoundsPlayed() throws Exception {

        League league = this.leagueFactory.create("First League");
        Mockito.when(this.leagueRepository.findAll()).thenReturn(List.of(league));

        assertEquals(0, (int) this.leaguesService.getRoundsPlayed());
    }

    @Test
    public void getRoundsPlayed_shouldReturnInteger4fRoundsPlayed() throws Exception {

        League league = this.leagueFactory.create("First League");
        league.setRoundsPlayed(4);
        Mockito.when(this.leagueRepository.findAll()).thenReturn(List.of(league));

        assertEquals(4, (int) this.leaguesService.getRoundsPlayed());
    }

    @Test
    public void getLeagueByTeam_shouldReturnLeague() {
        League league = this.leagueFactory.create("First league");
        Team team = new Team();
        team.setLeague(league);

        assertEquals(this.modelMapper.map(league, LeaguesServiceModel.class), this.leaguesService.getLeagueByTeam(team));
    }
}

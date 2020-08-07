package webproject.score.services.services;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import webproject.score.base.TestBase;
import webproject.score.data.models.*;
import webproject.score.services.factories.PlayerFactory;
import webproject.score.services.models.LeaguesServiceModel;
import webproject.score.services.models.PlayerServiceModel;
import webproject.score.services.models.TeamServiceModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class TeamsServiceTests extends TestBase {

    @Autowired
    TeamsService teamsService;

    @Autowired
    PlayerFactory playerFactory;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void getTeamPower_shouldReturnCorrectDouble() {
        Player goalkeeper = this.playerFactory.create(Position.GOALKEEPER);
        goalkeeper.setId("gk");
        Player defender = this.playerFactory.create(Position.DEFENDER);
        defender.setId("df");
        Player midfielder = this.playerFactory.create(Position.MIDFIELDER);
        midfielder.setId("mf");
        Player forwarder = this.playerFactory.create(Position.FORWARD);
        forwarder.setId("fw");

        Set<PlayerServiceModel> players = Set.of(goalkeeper, defender, midfielder, forwarder)
                .stream()
                .map(pl -> this.modelMapper.map(pl, PlayerServiceModel.class))
                .collect(Collectors.toSet());

        Double power = this.teamsService.getTeamPower(players);

        assertTrue(power > 1000d);
    }

//    @Test
//    public void addPointsToTeams_shouldReturnTeamWithAddedPoints() {
//        League league = new League();
//        league.setId("leagueId");
//
//        Team homeTeam = new Team();
//        homeTeam.setId("homeTeam");
//        homeTeam.setLeague(league);
//        homeTeam.setPoints(0);
//
//        Team awayTeam = new Team();
//        awayTeam.setId("awayTeam");
//        awayTeam.setLeague(league);
//        awayTeam.setPoints(0);
//
//        MatchServiceModel match = new MatchServiceModel();
//        match.setId("matchId");
//        match.setHomeTeam(homeTeam);
//        match.setAwayTeam(awayTeam);
//
//        TeamRepository teamRepository = Mockito.mock(TeamRepository.class);
//        Mockito.when(teamRepository.save(any(Team.class))).thenReturn(homeTeam);
//        Mockito.when(teamRepository.count()).thenReturn(1L);
//
//        match.setWinner("home");
//        this.teamsService.addPointsToTeams(match);
//        assertEquals(3, (int) homeTeam.getPoints());
//    }

    @Test
    public void getTeamsByLeague_shouldReturnListOfTeamsInLeague() {
        LeaguesServiceModel leaguesServiceModel = new LeaguesServiceModel();

        Team team1 = new Team();
        Team team2 = new Team();
        Team team3 = new Team();
        Team team4 = new Team();

        leaguesServiceModel.setTeams(List.of(team1, team2, team3, team4));

        List<TeamServiceModel> teams = this.teamsService.getTeamsByLeague(leaguesServiceModel);

        assertEquals(4, teams.size());
    }
}

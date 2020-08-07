package webproject.score.services.services;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import webproject.score.base.TestBase;
import webproject.score.data.models.*;
import webproject.score.data.repositories.GoalRepository;
import webproject.score.data.repositories.MatchRepository;
import webproject.score.services.factories.PlayerFactory;
import webproject.score.services.models.MatchServiceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;

public class MatchesServiceTests extends TestBase {

    @MockBean
    LeaguesService leaguesService;

    @MockBean
    MatchRepository matchRepository;

    @MockBean
    GoalRepository goalRepository;

    @MockBean
    TeamsService teamsService;

    @Autowired
    MatchesService matchesService;

    @Autowired
    PlayerFactory playerFactory;

    @Test
    public void play_addsResultsToaMatchEntity() {
        Mockito.when(this.leaguesService.getRoundsPlayed()).thenReturn(0);

        Team homeTeam = new Team();
        homeTeam.setId("homeTeam");
        Player homeTeamForward1 = this.playerFactory.create(Position.FORWARD);
        Player homeTeamForward2 = this.playerFactory.create(Position.FORWARD);
        homeTeamForward1.setId("htPlayer1");
        homeTeamForward2.setId("htPlayer2");
        homeTeam.setPlayers(Set.of(homeTeamForward1, homeTeamForward2));

        Team awayTeam = new Team();
        Player awayTeamForward1 = this.playerFactory.create(Position.FORWARD);
        Player awayTeamForward2 = this.playerFactory.create(Position.FORWARD);
        awayTeamForward1.setId("atPlayer1");
        awayTeamForward2.setId("atPlayer2");
        awayTeam.setId("awayTeam");
        awayTeam.setPlayers(Set.of(awayTeamForward1, awayTeamForward2));

        Match match = new Match();
        match.setId("match");
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        Mockito.when(this.matchRepository.findMatchesByRound(any(Integer.class))).thenReturn(matches);
        Mockito.when(this.goalRepository.save(any(Goal.class))).thenReturn(null);
        Mockito.when(this.matchRepository.save(any(Match.class))).thenReturn(null);

        Mockito.doNothing().when(teamsService).addPointsToTeams(isA(MatchServiceModel.class));
        Mockito.when(teamsService.getTeamPower(any())).thenReturn(100D);

        this.matchesService.play();
        System.out.println();
        //todo how to test that method if it does not return anything and it works with MatchServiceModel.class.
        // It is not changing the Match.class, which i give him with Mockito. It should be void because of scheduled task.
    }
}

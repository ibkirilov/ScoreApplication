package webproject.score.services.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.Match;
import webproject.score.data.models.Player;
import webproject.score.data.models.Team;
import webproject.score.data.repositories.MatchRepository;
import webproject.score.data.repositories.PlayerRepository;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class PlayersServiceTests extends TestBase {
    private int idCount = 0;

    @MockBean
    PlayerRepository playerRepository;

    @MockBean
    LeaguesService leaguesService;

    @MockBean
    MatchRepository matchRepository;

    @Autowired
    PlayersService playersService;

    @Test
    public void createSetOfPlayersForNewTeam_shouldReturnSetOfPlayers() {
        Mockito.when(this.playerRepository.saveAndFlush(any(Player.class))).thenReturn(new Player());
        Team team = new Team();
        Set<Player> players = this.playersService.createSetOfPlayersForNewTeam(team);

        assertEquals(1, players.size());
    }

    @Test
    public void changePlayersFormAfterTheMatches_shouldChangeTheFormOfAllPlayers() {
        Mockito.when(this.playerRepository.saveAndFlush(any(Player.class))).thenReturn(null);
        Player player = new Player();
        player.setForm(0);

        Player player1 = new Player();
        player1.setForm(10);

        Set<Player> winnerPlayers = Set.of(player);
        Set<Player> looserPlayers = Set.of(player1);

        Team winner = new Team();
        winner.setPlayers(winnerPlayers);

        Team looser = new Team();
        looser.setPlayers(looserPlayers);

        Match match = new Match();
        match.setHomeTeam(winner);
        match.setAwayTeam(looser);

        match.setWinner("home");

        Mockito.when(this.leaguesService.getRoundsPlayed()).thenReturn(1);
        Mockito.when(this.matchRepository.findMatchesByRound(1)).thenReturn(List.of(match));

        this.playersService.changePlayersFormAfterTheMatches();

        assertTrue(player.getForm() != 0);
        assertTrue(player1.getForm() != 10);
    }
}

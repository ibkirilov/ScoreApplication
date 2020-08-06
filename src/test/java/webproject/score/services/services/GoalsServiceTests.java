package webproject.score.services.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.*;
import webproject.score.data.repositories.GoalRepository;
import webproject.score.services.factories.PlayerFactory;
import webproject.score.services.models.MatchServiceModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class GoalsServiceTests extends TestBase {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GoalsService goalsService;

    @Autowired
    PlayerFactory playerFactory;

    @MockBean
    GoalRepository goalRepository;

    @Test
    public void createGoalsForTeam_shouldCreateListOfGoals() {
        MatchServiceModel matchServiceModel = new MatchServiceModel();
        Match match = this.modelMapper.map(matchServiceModel, Match.class);
        Team team = new Team();
        int numberOfGoals = 3;

        Set<Player> players = new HashSet<>();
        Player player1 = this.playerFactory.create(Position.FORWARD);
        Player player2 = this.playerFactory.create(Position.FORWARD);
        player1.setId("abc");
        player2.setId("cba");
        players.add(player1);
        players.add(player2);
        team.setPlayers(players);

        Mockito.when(this.goalRepository.save(any(Goal.class))).thenReturn(null);

        List<Goal> goals = this.goalsService.createGoalsForTeam(matchServiceModel, team, numberOfGoals);

        assertEquals(goals.size(), numberOfGoals);
        assertEquals(goals.get(0).getMatch(), match);
        assertEquals(goals.get(1).getMatch(), match);
        assertEquals(goals.get(2).getMatch(), match);
        assertNotNull(goals.get(0).getPlayer());
    }
}

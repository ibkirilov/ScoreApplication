package webproject.score.services.factories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.Player;
import webproject.score.data.models.Position;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PlayerFactoryTests extends TestBase {

    @Autowired
    private PlayerFactory playerFactory;

    @Test
    public void create_createPlayerWithNonEmptyFields_whenCreateGoalkeeperCreated() {
        Player player = playerFactory.create(Position.GOALKEEPER);
        assertNotNull(player.getFirstName());
        assertNotNull(player.getLastName());
        assertNotNull(player.getAge());
        assertNotNull(player.getGoalkeeping());
        assertNotNull(player.getDefending());
        assertNotNull(player.getPlaymaking());
        assertNotNull(player.getScoring());
        assertNotNull(player.getAge());
        assertEquals(player.getPosition(), Position.GOALKEEPER);
    }

    @Test
    public void create_createPlayerWithNonEmptyFields_whenCreateDefenderCreated() {
        Player player = playerFactory.create(Position.DEFENDER);
        assertNotNull(player.getFirstName());
        assertNotNull(player.getLastName());
        assertNotNull(player.getAge());
        assertNotNull(player.getGoalkeeping());
        assertNotNull(player.getDefending());
        assertNotNull(player.getPlaymaking());
        assertNotNull(player.getScoring());
        assertNotNull(player.getAge());
        assertEquals(player.getPosition(), Position.DEFENDER);
    }

    @Test
    public void create_createPlayerWithNonEmptyFields_whenCreateMidfielderCreated() {
        Player player = playerFactory.create(Position.MIDFIELDER);
        assertNotNull(player.getFirstName());
        assertNotNull(player.getLastName());
        assertNotNull(player.getAge());
        assertNotNull(player.getGoalkeeping());
        assertNotNull(player.getDefending());
        assertNotNull(player.getPlaymaking());
        assertNotNull(player.getScoring());
        assertNotNull(player.getAge());
        assertEquals(player.getPosition(), Position.MIDFIELDER);
    }

    @Test
    public void create_createPlayerWithNonEmptyFields_whenCreateForwarderCreated() {
        Player player = playerFactory.create(Position.FORWARD);
        assertNotNull(player.getFirstName());
        assertNotNull(player.getLastName());
        assertNotNull(player.getAge());
        assertNotNull(player.getGoalkeeping());
        assertNotNull(player.getDefending());
        assertNotNull(player.getPlaymaking());
        assertNotNull(player.getScoring());
        assertNotNull(player.getAge());
        assertEquals(player.getPosition(), Position.FORWARD);
    }
}

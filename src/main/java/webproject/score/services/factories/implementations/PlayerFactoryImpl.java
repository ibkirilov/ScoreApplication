package webproject.score.services.factories.implementations;

import com.github.javafaker.Faker;
import webproject.score.config.annotations.Factory;
import webproject.score.data.models.Player;
import webproject.score.data.models.Position;
import webproject.score.services.factories.PlayerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

@Factory
public class PlayerFactoryImpl implements PlayerFactory {
    @Override
    public Player create(Position position) {
        Player player = new Player();

        Faker faker = new Faker(new Locale("en"));
        player.setFirstName(faker.name().firstName());
        player.setLastName(faker.name().lastName());

        player.setPosition(position);

        player.setAge(getRandomNumber(18,32));

        player.setForm(getRandomNumber(1, 10));

        player.setGoals(new ArrayList<>());

        setSkills(player);

        return player;
    }

    private void setSkills(Player player) {

        if (player.getPosition() == Position.GOALKEEPER) {
            player.setGoalkeeping(getRandomNumber(60, 100));
            player.setDefending(getRandomNumber(10, 60));
            player.setPlaymaking(getRandomNumber(0, 50));
            player.setScoring(getRandomNumber(0, 40));
        } else if (player.getPosition() == Position.DEFENDER) {
            player.setGoalkeeping(getRandomNumber(0, 40));
            player.setDefending(getRandomNumber(60, 100));
            player.setPlaymaking(getRandomNumber(20, 70));
            player.setScoring(getRandomNumber(10, 60));
        } else if (player.getPosition() == Position.MIDFIELDER) {
            player.setGoalkeeping(getRandomNumber(0, 30));
            player.setDefending(getRandomNumber(20, 70));
            player.setPlaymaking(getRandomNumber(60, 100));
            player.setScoring(getRandomNumber(20, 70));
        } else {
            player.setGoalkeeping(getRandomNumber(0, 20));
            player.setDefending(getRandomNumber(10, 50));
            player.setPlaymaking(getRandomNumber(30, 70));
            player.setScoring(getRandomNumber(60, 100));
        }
    }

    private Integer getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}

package webproject.score.services.factories.implementations;

import webproject.score.config.annotations.Factory;
import webproject.score.data.models.Stadium;
import webproject.score.services.factories.StadiumFactory;

import java.util.Random;

@Factory
public class StadiumFactoryImpl implements StadiumFactory {
    @Override
    public Stadium create(String name) {
        Stadium stadium = new Stadium();
        stadium.setName(name);
        stadium.setCapacity(getRandomCapacity());
        return stadium;
    }

    private Integer getRandomCapacity() {
        Random random = new Random();
        int min = 150;
        int max = 300;

        int randomNum = (int)(Math.random() * (max - min + 1) + min);
        int capacity = randomNum * 100;

        return capacity;
    }
}

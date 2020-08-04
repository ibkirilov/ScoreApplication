package webproject.score.services.services;

import webproject.score.data.models.Stadium;

public interface StadiumsService {
    Stadium createStadium(String name);

    Stadium saveNewStadium(Stadium stadium);
}

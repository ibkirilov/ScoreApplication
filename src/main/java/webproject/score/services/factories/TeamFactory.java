package webproject.score.services.factories;

import webproject.score.data.models.League;
import webproject.score.data.models.Team;

public interface TeamFactory {
    Team create(String name, League league);
}

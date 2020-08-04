package webproject.score.services.factories;

import webproject.score.data.models.Player;
import webproject.score.data.models.Position;

public interface PlayerFactory {
    Player create(Position position);
}

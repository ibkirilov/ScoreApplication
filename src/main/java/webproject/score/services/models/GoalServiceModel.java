package webproject.score.services.models;

import lombok.Data;
import webproject.score.data.models.Match;
import webproject.score.data.models.Player;
import webproject.score.data.models.Team;

import java.util.Objects;

@Data
public class GoalServiceModel {
    private String id;

    private Match match;

    private Player player;

    private Team team;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalServiceModel that = (GoalServiceModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

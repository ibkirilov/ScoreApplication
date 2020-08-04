package webproject.score.services.models;

import lombok.Data;
import webproject.score.data.models.Goal;
import webproject.score.data.models.Position;

import java.util.Set;

@Data
public class PlayerServiceModel {
    private String firstName;

    private String lastName;

    private Position position;

    private Integer goalkeeping;

    private Integer age;

    private Integer defending;

    private Integer playmaking;

    private Integer scoring;

    private Integer form;

    private Set<Goal> goals;
}

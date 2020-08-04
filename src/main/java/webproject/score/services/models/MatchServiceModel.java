package webproject.score.services.models;

import lombok.Data;
import webproject.score.data.models.Goal;
import webproject.score.data.models.Stadium;
import webproject.score.data.models.Team;

import java.util.List;
import java.util.Set;

@Data
public class MatchServiceModel {
    private String id;

    private Team homeTeam;

    private Team awayTeam;

    private Double homeTeamPower;

    private Double awayTeamPower;

    private Stadium stadium;

    private List<Goal> goals;

    private Integer round;

    private String winner;
}

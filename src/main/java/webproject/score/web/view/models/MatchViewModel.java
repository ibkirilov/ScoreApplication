package webproject.score.web.view.models;

import lombok.Data;

@Data
public class MatchViewModel {

    private String id;

    private String homeTeam;

    private String awayTeam;

    private Double homeTeamPower;

    private Double awayTeamPower;

    private String stadium;

    private Integer homeGoals;

    private Integer awayGoals;

    private Integer round;

    private String winner;
}

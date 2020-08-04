package webproject.score.services.models;

import lombok.Data;
import webproject.score.data.models.Team;

import java.util.List;

@Data
public class LeaguesServiceModel {
    private String name;
    private List<Team> teams;
    private Integer roundsPlayed;
}

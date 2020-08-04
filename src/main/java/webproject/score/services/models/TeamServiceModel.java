package webproject.score.services.models;

import lombok.Data;
import webproject.score.data.models.League;
import webproject.score.data.models.Stadium;

@Data
public class TeamServiceModel {
    private String name;

    private String town;

    private Integer points;

    private String logoLink;

    private League league;

    private Stadium stadium;

    private String fanClubName;
}

package webproject.score.web.view.models;

import lombok.Data;
import webproject.score.data.models.*;

@Data
public class TeamViewModel {
    private String name;

    private String town;

    private Integer points;

    private String logoLink;

    private League league;

    private Stadium stadium;

    private String fanClubName;

}

package webproject.score.services.models;

import lombok.Data;

@Data
public class TeamCreateServiceModel {

    private String teamName;
    private String town;
    private String stadiumName;
    private String fanClub;
    private String logoLink;
}

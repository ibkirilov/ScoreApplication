package webproject.score.data.models;

import lombok.Data;
import webproject.score.data.models.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "matches")
public class Match extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_id")
    private Team awayTeam;

    @Column(name = "home_team_power")
    private Double homeTeamPower;

    @Column(name = "away_team_power")
    private Double awayTeamPower;

    @ManyToOne
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER)
    private List<Goal> goals;

    @Column(name = "round")
    private Integer round;

    @Column(name = "winner")
    private String winner;
}

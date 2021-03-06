package webproject.score.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import webproject.score.data.models.base.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "town")
    private String town;

    @Column(name = "points")
    private Integer points;

    @Column(name = "logo_link")
    private String logoLink;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @OneToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    private Stadium stadium;

    @Column(name = "fanclub_name")
    private String fanClubName;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Player> players;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "homeTeam", fetch = FetchType.EAGER)
    private Set<Match> homeMatches;

    @OneToMany(mappedBy = "awayTeam", fetch = FetchType.EAGER)
    private Set<Match> awayMatches;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private Set<Goal> goals;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}

package webproject.score.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.score.data.models.base.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leagues")
public class League extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER)
    private List<Team> teams;

    @Column(name = "rounds_played")
    private Integer roundsPlayed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        League league = (League) o;
        return Objects.equals(name, league.name) &&
                Objects.equals(roundsPlayed, league.roundsPlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, roundsPlayed);
    }
}

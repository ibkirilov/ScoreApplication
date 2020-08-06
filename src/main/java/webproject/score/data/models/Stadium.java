package webproject.score.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.score.data.models.base.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stadiums")
public class Stadium extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "stadium")
    private Team team;

    @Column(name = "capacity")
    private Integer capacity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Stadium stadium = (Stadium) o;
        return Objects.equals(name, stadium.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}

package webproject.score.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.score.data.models.base.BaseEntity;

import javax.persistence.*;

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
}

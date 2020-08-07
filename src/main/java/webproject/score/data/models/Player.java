package webproject.score.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.score.data.models.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne()
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "age")
    private Integer age;

    @Column(name = "goalkeeping")
    private Integer goalkeeping;

    @Column(name = "defending")
    private Integer defending;

    @Column(name = "playmaking")
    private Integer playmaking;

    @Column(name = "scoring")
    private Integer scoring;

    @Column(name = "form")
    private Integer form;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private List<Goal> goals;
}

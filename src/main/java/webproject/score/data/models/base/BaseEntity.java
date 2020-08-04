package webproject.score.data.models.base;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseEntity {

//    @Id
//    @Column(name = "id", nullable = false, unique = true, updatable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private String id;
}

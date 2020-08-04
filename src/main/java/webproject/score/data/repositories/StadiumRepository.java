package webproject.score.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webproject.score.data.models.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, String> {
}

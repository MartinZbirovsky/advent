package advent.repository;

import advent.model.AdsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsResponseRepository extends JpaRepository<AdsResponse, Long> {
}

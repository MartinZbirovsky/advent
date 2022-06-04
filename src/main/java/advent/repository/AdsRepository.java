package advent.repository;

import advent.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findByNameContaining(String name);
}

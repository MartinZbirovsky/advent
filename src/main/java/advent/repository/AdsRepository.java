package advent.repository;

import advent.model.Ads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    Page<Ads> findByNameContaining(String name, Pageable pageable);
    @Query(  value = "SELECT * FROM ADS u WHERE u.category_id = ?1", nativeQuery = true)
    Page<Ads> findByCategory(Long category, Pageable pageable);
}

package advent.repository;

import advent.model.Ads;
import advent.model.Benefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    Benefit findByName(String benefitName);
}

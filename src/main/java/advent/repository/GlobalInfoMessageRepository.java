package advent.repository;

import advent.model.GlobalInfoMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalInfoMessageRepository extends JpaRepository<GlobalInfoMessage, Long> {
}

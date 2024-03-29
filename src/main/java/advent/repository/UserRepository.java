package advent.repository;

import advent.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	@Transactional
	@Modifying
	@Query("UPDATE User a " +
			"SET a.enabled = TRUE WHERE a.email = ?1")
	int enableUser(String email);

	/**
	 * Page with list of User
	 * @param email
	 * @param pageable
	 * @return
	 */
	Page<User> findByEmailContaining(String email, Pageable pageable);
}

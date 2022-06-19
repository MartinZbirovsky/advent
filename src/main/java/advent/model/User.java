package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends UserDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Email
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@Column(nullable = false, length = 64)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles= new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private Set<Payment> payments = new HashSet<>();

	@Min(0)
	protected BigDecimal currentMoney = new BigDecimal(0);
}

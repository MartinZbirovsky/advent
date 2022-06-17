package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Arrays;
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
	@Column(nullable = false, length = 50, unique = true)
	@Email
	private String email;
	private String username;
	@Column(nullable = false, length = 64)
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles= new HashSet<>();
	@OneToMany(mappedBy="user")
	private Set<Payment> payments = new HashSet<>();
	/*@OneToMany(mappedBy="user")
	private Set<Ads> ads;*/
	// SOFT DELETE
}

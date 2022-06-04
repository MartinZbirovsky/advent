package advent.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 50, unique = true)
	@Email
	private String email;
	
	@Column(nullable = false, length = 64)
	private String password;

	@ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
	@JoinColumn(name = "address_id")
	private Address address;

	@ManyToMany(fetch = EAGER)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String email, String password, Address address, List<Role> roles) {
		this.email = email;
		this.password = password;
		this.address = address;
		this.roles = roles;
	}
	public void addRole(Role role) { this.roles.add(role); }
	public void removeRole(Role role) { this.roles.remove(role); }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}
	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}

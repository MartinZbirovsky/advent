package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String username;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Role> roles= new ArrayList<>();
}


/*
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends UserDetail implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;



	@Column(nullable = false, length = 50, unique = true)
	@Email
	private String email;

	@Column(nullable = false, length = 64)
	private String password;
	// SOFT DELETE
	@ManyToMany(fetch = EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy="user")
	private Set<Payment> payment = new HashSet<>();

	@OneToMany(mappedBy="user")
	private Set<Ads> items;

	@NotNull
	private boolean isAccountNonExpired = true;
	@NotNull
	private boolean isAccountNonLocked = true;
	@NotNull
	private boolean isCredentialsNonExpired = true;
	@NotNull
	private boolean isEnabled = true;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String email, String password, Address address, Set<Role> roles) {
		this.email = email;
		this.password = password;
		this.firstAddress = address;
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
		return this.isAccountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}
}
*/
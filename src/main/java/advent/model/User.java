package advent.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	@Email
	private String email;

	@Column(nullable = false, length = 64)
	private String password;

	@Min(0)
	private Long credits;

	// SOFT DELETE

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime modifiedAt;
	
	@ManyToMany(fetch = EAGER)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
	@JoinColumn(name = "first_address_id")
	private Address firstAddress;

	@ManyToOne
	@JoinColumn(name = "second_address_id")
	private Address secondAddress;

	@NotNull
	private boolean isAccountNonExpired = true;
	@NotNull
	private boolean isAccountNonLocked = true;
	@NotNull
	private boolean isCredentialsNonExpired = true;
	@NotNull
	private boolean isEnabled = true;

	/*@OneToMany(mappedBy="user")
	private Set<Ads> items;*/

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

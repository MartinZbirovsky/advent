package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @Length(min = 0, max = 50)
    protected String fistName;

    @Length(min = 0, max = 50)
    protected String secondName;

    protected String companyName = "";

    @OneToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "first_address_id")
    protected Address firstAddress;

    @OneToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "second_address_id")
    protected Address secondAddress;

    @Min(0)
    protected BigDecimal currentMoney = new BigDecimal(0);

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    protected Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    protected Date modifiedAt;

    /*
	@NotNull
	private boolean isAccountNonExpired = true;
	@NotNull
	private boolean isAccountNonLocked = true;
	@NotNull
	private boolean isCredentialsNonExpired = true;
	@NotNull
	private boolean isEnabled = true;
	protected void addRole(Role role) { this.roles.add(role); }
	protected void removeRole(Role role) { this.roles.remove(role); }
	protected Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}*/
}

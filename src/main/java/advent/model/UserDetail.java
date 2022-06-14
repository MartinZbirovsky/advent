package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @Length(min = 0, max = 50)
    public String fistName;

    @Length(min = 0, max = 50)
    public String secondName;

    public String companyName = "";

    @ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "first_address_id")
    public Address firstAddress;

    @ManyToOne
    @JoinColumn(name = "second_address_id")
    public Address secondAddress;

    @Min(0)
    public BigDecimal currentMoney = new BigDecimal(0);

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    public Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    public Date modifiedAt;

    /*
	@NotNull
	private boolean isAccountNonExpired = true;
	@NotNull
	private boolean isAccountNonLocked = true;
	@NotNull
	private boolean isCredentialsNonExpired = true;
	@NotNull
	private boolean isEnabled = true;
	public void addRole(Role role) { this.roles.add(role); }
	public void removeRole(Role role) { this.roles.remove(role); }
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}*/
}

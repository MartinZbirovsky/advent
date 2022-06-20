package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static advent.cons.GeneralCons.ADS_PRICE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Slf4j
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

	/**
	 * Deduct money after creating new ad, -10: ADS_PRICE
	 */
	public void reduceCurrentMoney(){
		log.info("Current money: " + this.getCurrentMoney());
		if(this.getCurrentMoney().compareTo(ADS_PRICE) == -1){
			throw new RuntimeException("No money");
		}else {
			this.setCurrentMoney(this.getCurrentMoney().add(ADS_PRICE));
		}
	}
}

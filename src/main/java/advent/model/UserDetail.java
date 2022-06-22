package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

import static advent.cons.GeneralCons.ADS_PRICE;
import static javax.persistence.FetchType.EAGER;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserDetail {

    @Length(min = 0, max = 30)
    protected String publisherName;
    @Length(min = 0, max = 30)
    protected String fistName;
    @Length(min = 0, max = 30)
    protected String secondName;

    @OneToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "first_address_id")
    protected Address firstAddress;

    @OneToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "second_address_id")
    protected Address secondAddress;

    @Lob
    private byte[] companyLogo;

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    protected Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    protected Date modifiedAt;

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
            //NEED FIX
            //this.setCurrentMoney(this.getCurrentMoney().add(ADS_PRICE));
        }
    }
}

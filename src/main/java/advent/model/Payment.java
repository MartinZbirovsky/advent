package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Length(min = 0, max = 200)
    private String paymentName;
    private String accountNumber;
    @Min(0)
    private BigDecimal payed = new BigDecimal(0);

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    private Date modifiedAt;
}

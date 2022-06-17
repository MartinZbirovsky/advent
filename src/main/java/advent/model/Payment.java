package advent.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String paymentPrice;

    @Min(0)
    private BigDecimal payed;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    private Date modifiedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(String paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public BigDecimal getPayed() {
        return payed;
    }

    public void setPayed(BigDecimal payed) {
        this.payed = payed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}

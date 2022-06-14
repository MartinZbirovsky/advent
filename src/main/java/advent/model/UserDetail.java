package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    @ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "first_address_id")
    public Address firstAddress;

    @ManyToOne
    @JoinColumn(name = "second_address_id")
    public Address secondAddress;

    @Min(0)
    public BigDecimal currentMoney;

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    public Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    public Date modifiedAt;
}

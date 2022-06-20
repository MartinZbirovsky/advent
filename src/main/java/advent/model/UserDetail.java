package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
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
}

package advent.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ads_benefits",
            joinColumns = @JoinColumn(name = "ads_id"),
            inverseJoinColumns = @JoinColumn(name = "benefits_id")
    )
    private Set<Benefit> benefits = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_user")
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    private Date modified_at;

    public Ads(String name) {
        this.name = name;
    }

    public void addBenefitToAds (Benefit benefit){
        this.benefits.add(benefit);
    }

    public void deleteBenefitFromAds (Benefit benefit) {
        this.benefits.remove(benefit);
    }
}

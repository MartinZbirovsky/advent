package advent.model;

import advent.enums.stateAds;
import advent.enums.workTypeAds;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    @Length(min = 0, max = 800)
    private String description = "";

    @Length(min = 0, max = 200)
    private String requirements;

    private String companyOffer;

    @Min(0)
    private Long salaryFrom;
    @Min(0)
    private Long salaryTo;

    @Length(min = 0, max = 150)
    private String officePlace;

    @Enumerated(EnumType.STRING)
    private workTypeAds type = workTypeAds.OTHER;

    @Enumerated(EnumType.STRING)
    private stateAds state = stateAds.ACTIVE;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name= "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ads_benefits",
            joinColumns = @JoinColumn(name = "ads_id"),
            inverseJoinColumns = @JoinColumn(name = "benefits_id")
    )
    private Set<Benefit> benefits = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ads_id")
    private Set<AdsResponse> response = new HashSet<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    private Date modifiedAt;
    
    public void addBenefit (Benefit benefit){
        this.benefits.add(benefit);
    }
    public void removeBenefit (Benefit benefit) {
        this.benefits.remove(benefit);
    }
}

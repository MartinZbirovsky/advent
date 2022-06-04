package advent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ads_benefits",
            joinColumns = @JoinColumn(name = "ads_id"),
            inverseJoinColumns = @JoinColumn(name = "benefits_id")
    )
    private List<Benefit> benefits = new ArrayList<>();

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

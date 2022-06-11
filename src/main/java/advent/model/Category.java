package advent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "ads"})
public class Category {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Length(min = 1, max = 20)
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ads> ads = new ArrayList<>();

    public Category(String name, List<Ads> ads) {
        this.name = name;
        this.ads = ads;
    }

    public Category(String name) {
        this.name = name;
    }

    public void addAds(Ads ads) { this.ads.add(ads); }
    public void removeAds(Ads ads) { this.ads.remove(ads); }
}

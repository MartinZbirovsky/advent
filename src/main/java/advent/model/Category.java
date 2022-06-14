package advent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Length(min = 1, max = 20)
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "category"/*,  orphanRemoval = true*/)
    private Set<Ads> ads = new HashSet<>();

    public Category(String name, Set<Ads> ads) {
        this.name = name;
        this.ads = ads;
    }

    public Category(String name) {
        this.name = name;
    }

    public void addAds(Ads ads) { this.ads.add(ads); }
    public void removeAds(Ads ads) { this.ads.remove(ads); }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ads> getAds() {
        return ads;
    }

    public void setAds(Set<Ads> ads) {
        this.ads = ads;
    }
}

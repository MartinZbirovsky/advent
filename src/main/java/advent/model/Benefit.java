package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Lob
    private byte[] icon;

    public Benefit(String name) {
        this.name = name;
    }

    public Benefit(String name, byte[] icon, List<Ads> ads) {
        this.name = name;
        this.icon = icon;
    }
}

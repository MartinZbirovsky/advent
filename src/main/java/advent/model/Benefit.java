package advent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Length(min = 3, max = 30)
    @Column(unique=true)
    private String name;
    @Lob
    private byte[] icon;
}

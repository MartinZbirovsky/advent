package advent.dto.responseDto;

import advent.model.Benefit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDetailResDto extends AdsHomeResDto {
    private Date createdAt;
    private String description;
    private String requirements;
    private String companyOffer;
    private Set<Benefit> benefits;
}

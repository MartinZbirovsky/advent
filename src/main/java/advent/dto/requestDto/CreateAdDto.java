package advent.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdDto {
    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    @Length(min = 0, max = 800)
    private String description;

    @Length(min = 0, max = 200)
    private String requirements;

    private String companyOffer;

    @Min(0)
    private Long salaryFrom;
    @Min(0)
    private Long salaryTo;

    @Length(min = 0, max = 150)
    private String officePlace;
}

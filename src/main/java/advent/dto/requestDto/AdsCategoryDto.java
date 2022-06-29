package advent.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model represent request body for assign ad to category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsCategoryDto {
    private String adsName;
    private String catName;
}

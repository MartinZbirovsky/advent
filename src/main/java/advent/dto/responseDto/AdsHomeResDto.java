package advent.dto.responseDto;

import advent.enums.WorkTypeAds;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Home page list with simple detail.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsHomeResDto {
        private Long id;
        private String name;
        private Long salaryFrom;
        private Long salaryTo;
        private String officePlace;
        private WorkTypeAds workType = WorkTypeAds.OTHER;
        private String publisherName;
        private byte[] companyLogo;
}

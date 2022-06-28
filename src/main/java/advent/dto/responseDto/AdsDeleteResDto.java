package advent.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response after delete advertisement
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDeleteResDto {
    private String message;
    private String name;
    private Long salaryFrom;
    private Long salaryTo;
    private String publisherName;
}

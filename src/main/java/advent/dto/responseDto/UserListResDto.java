package advent.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserListResDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean locked = false;
    private Boolean enabled = false;
}

package advent.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleUserResDto {
    private String email;
    private String roleName;
    private String message;
}

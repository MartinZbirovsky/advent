package advent.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class RoleUserDto {
    //@Email
    private String email;
    @Length(min = 4, max = 25)
    private String roleName;
}

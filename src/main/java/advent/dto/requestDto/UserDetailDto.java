package advent.dto.requestDto;

import advent.dto.responseDto.UserCreateResDto;
import advent.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto extends UserCreateResDto {
    private Address address;
}

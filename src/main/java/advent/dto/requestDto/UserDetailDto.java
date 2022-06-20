package advent.dto.requestDto;

import advent.dto.responseDto.UserCreateResDto;
import advent.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDetailDto extends UserCreateResDto {
    private Address address;
}

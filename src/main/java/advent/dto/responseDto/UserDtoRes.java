package advent.dto.responseDto;

import advent.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRes {
    private int id;
    private String email;
    private Address address;
}

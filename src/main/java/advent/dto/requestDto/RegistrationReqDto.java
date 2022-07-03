package advent.dto.requestDto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class RegistrationReqDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

package advent.dto.requestDto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class RegistrationReqDto {
    @NotEmpty
    @Length(min = 2, max = 20)
    private String firstName;
    @NotEmpty
    @Length(min = 2, max = 20)
    private String lastName;
    @Email
    @Length(min = 5, max = 50)
    private String email;
    @NotEmpty
    @Length(min = 5, max = 50)
    private String password;
}

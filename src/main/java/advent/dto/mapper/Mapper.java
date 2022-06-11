package advent.dto.mapper;

import advent.dto.requestDto.UserDetailDto;
import advent.dto.responseDto.UserCreateDto;
import advent.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserCreateDto userToCreateDto(User user) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setId(user.getId());
        userCreateDto.setEmail(user.getEmail());
        return userCreateDto;
    }

    public UserDetailDto userToDetailDto(User user) {
        UserDetailDto userCreateDtoRes = new UserDetailDto();
        userCreateDtoRes.setId(user.getId());
        userCreateDtoRes.setEmail(user.getEmail());
        userCreateDtoRes.setAddress(user.getFirstAddress());
        return userCreateDtoRes;
    }
}

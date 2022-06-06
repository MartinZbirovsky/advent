package advent.dto.mapper;

import advent.dto.responseDto.UserDtoRes;
import advent.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    /*public AdsDTO transformToDto(Ads entity) {
        AdsDTO dto = new AdsDTO();
        dto.setName(entity.getName());
        return dto;
    }

    public Ads transformToEntity(AdsDTO dto) {
        Ads dev = new Ads();
        dev.setName(dto.getName());
        return dev;
    }*/

    public UserDtoRes UserToDto(User user) {
        UserDtoRes userDtoRes = new UserDtoRes();
        userDtoRes.setEmail(user.getEmail());
        userDtoRes.setAddress(user.getAddress());
        return userDtoRes;
    }
}

package advent.service.Interface;

import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.Role;
import advent.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(User user);
    User editUser (User user);
    User getUser(String username);
    Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy);
    User deleteUserByEmail(String email);
    Role saveRole(Role role);
    RoleUserResDto addRoleToUse(String userEmail, String role);
    RoleUserResDto removeRole(RoleUserDto form);

}
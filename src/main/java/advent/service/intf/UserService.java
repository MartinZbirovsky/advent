package advent.service.intf;

import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>User service</p>
 *
 * @see #saveRole(Role)
 *
 * @author mz
 */
@Service
public interface UserService {
    /**
     * Save new user
     *
     * @param user
     * @return User
     */
    User saveUser(User user);
    User editUser (User user);
    User getUserByEmail(String email);
    Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy);
    User deleteUserByEmail(String email);
    Role saveRole(Role role);
    RoleUserResDto addRoleToUse(String userEmail, String role);
    RoleUserResDto removeRole(RoleUserDto form);
    BigDecimal chargeMoney(String email, Payment payment);
}
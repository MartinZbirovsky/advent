package advent.service.Interface;

import advent.model.Role;
import advent.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUse(String username, String role);
    User getUser(String username);
    Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy);
}
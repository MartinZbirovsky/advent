package advent.service.serviceinterface;

import advent.model.Role;
import advent.model.User;
import advent.service.serviceinterface.general.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUse(String username, String role);
    User getUser(String username);
    List<User> getUsers();
}
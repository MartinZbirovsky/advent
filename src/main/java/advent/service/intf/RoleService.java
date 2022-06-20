package advent.service.intf;

import advent.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role addNew(Role entityBody);
    Page<Role> getAll(int pageNo, int pageSize, String sortBy);
    Role get(Long entityId);
    Role edit(Long entityId, Role entityBody);
    Role delete(Long entityId);
    Role save (Role role);
    Role findByName(String name);
}

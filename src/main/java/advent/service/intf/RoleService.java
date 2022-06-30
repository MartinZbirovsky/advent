package advent.service.intf;

import advent.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 
 * @see #addNew(Role) 
 * @see #getAll(int, int, String)
 * @see #get(Long) 
 * @see #edit(Long, Role) 
 * @see #delete(Long) 
 * @see #save(Role) 
 * @see #findByName(String)
 */
@Service
public interface RoleService {
    /**
     *
     * @param entityBody
     * @return
     */
    /**
     *
     * @param entityBody
     * @return
     */
    Role addNew(Role entityBody);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<Role> getAll(int pageNo, int pageSize, String sortBy);

    /**
     *
     * @param entityId
     * @return
     */
    Role get(Long entityId);

    /**
     *
     * @param entityId
     * @param entityBody
     * @return
     */
    Role edit(Long entityId, Role entityBody);

    /**
     *
     * @param entityId
     * @return
     */
    Role delete(Long entityId);

    /**
     *
     * @param role
     * @return
     */
    Role save (Role role);

    /**
     *
     * @param name
     * @return
     */
    Optional<Role> findByName(String name);
}

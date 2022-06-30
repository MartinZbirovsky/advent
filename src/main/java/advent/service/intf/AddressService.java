package advent.service.intf;

import advent.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * @see #getAll(int, int, String) 
 * @see #get(Long)
 * @see #delete(Long) 
 */
@Service
public interface AddressService {
    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<Address> getAll(int pageNo, int pageSize, String sortBy);

    /**
     *
     * @param entityId
     * @return
     */
    Address get(Long entityId);

    /**
     *
     * @param entityId
     * @return
     */
    Address delete(Long entityId);
}

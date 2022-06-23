package advent.service.intf;

import advent.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Page<Address> getAll(int pageNo, int pageSize, String sortBy);
    Address get(Long entityId);
    Address delete(Long entityId);
}

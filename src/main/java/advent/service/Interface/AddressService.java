package advent.service.Interface;

import advent.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address addNew(Address entityBody);
    Page<Address> getAll(int pageNo, int pageSize, String sortBy);
    Address get(Long entityId);
    Address edit(Long entityId, Address entityBody);
    Address delete(Long entityId);
}

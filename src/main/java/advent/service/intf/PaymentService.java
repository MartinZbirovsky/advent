package advent.service.intf;

import advent.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    Payment addNew(Payment entityBody);
    Page<Payment> getAll(int pageNo, int pageSize, String sortBy);
    Payment get(Long entityId);
}

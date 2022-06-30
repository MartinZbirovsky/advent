package advent.service.intf;

import advent.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * @see #addNew(Payment)
 * @see #getAll(int, int, String)
 * @see #get(Long)
 */
@Service
public interface PaymentService {
    /**
     *
     * @param entityBody
     * @return
     */
    Payment addNew(Payment entityBody);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<Payment> getAll(int pageNo, int pageSize, String sortBy);

    /**
     *
     * @param entityId
     * @return
     */
    Payment get(Long entityId);
}

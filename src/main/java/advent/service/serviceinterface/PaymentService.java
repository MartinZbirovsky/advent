package advent.service.serviceinterface;

import advent.model.Ads;
import org.springframework.data.domain.Page;

public interface PaymentService<T> {
    T addNew(T entityBody);
    Page<T> getAll(int pageNo, int pageSize, String sortBy);
    T get(Long entityId);
}

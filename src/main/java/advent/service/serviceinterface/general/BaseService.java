package advent.service.serviceinterface.general;

import org.springframework.data.domain.Page;

public interface BaseService<T> {
    T addNew(T entityBody);
    Page<T> getAll(int pageNo, int pageSize, String sortBy);
    T get(Long entityId);
    T edit(Long entityId, T entityBody);
    T delete(Long entityId);
}
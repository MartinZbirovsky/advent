package advent.service.erviceinterface.general;

import java.util.List;

public interface BaseService<T> {
    T addNew(T entityBody);
    List<T> getAll();
    T getById(Long entityId);
    T deleteById(Long entityId);
    T editById(Long entityId, T entityBody);
}
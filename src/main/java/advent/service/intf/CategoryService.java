package advent.service.intf;

import advent.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @see #addNew(Category)
 * @see #getAll(int, int, String)
 * @see #findByName(String)
 * @see #get(Long)
 * @see #edit(Long, Category)
 * @see #delete(Long)
 */
@Service
public interface CategoryService{
    /**
     *
     * @param entityBody
     * @return
     */
    Category addNew(Category entityBody);

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    Page<Category> getAll(int pageNo, int pageSize, String sortBy);

    /**
     *
     * @param categoryName
     * @return
     */
    Optional<Category> findByName(String categoryName);

    /**
     *
     * @param entityId
     * @return
     */
    Category get(Long entityId);

    /**
     *
     * @param entityId
     * @param entityBody
     * @return
     */
    Category edit(Long entityId, Category entityBody);

    /**
     *
     * @param entityId
     * @return
     */
    Category delete(Long entityId);
}

package advent.service.Interface;

import advent.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService{
    Category addNew(Category entityBody);
    Page<Category> getAll(int pageNo, int pageSize, String sortBy);
    Category findByName(String categoryName);
    Category get(Long entityId);
    Category edit(Long entityId, Category entityBody);
    Category delete(Long entityId);
}

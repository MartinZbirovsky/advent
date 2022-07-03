package advent.service.impl;

import advent.model.Category;
import advent.repository.CategoryRepository;
import advent.service.intf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @see advent.service.intf.CategoryService
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category addNew(Category category) {
        categoryRepository.findByName(category.getName()).ifPresent(data -> {
            throw new EntityNotFoundException("Same category exist");
        });
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return categoryRepository.findAll(paging);
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    @Override
    public Category get(Long categoryId) {
       return categoryRepository.findById(categoryId)
               .orElseThrow(() -> new EntityNotFoundException("Category " + categoryId + "not found"));
    }

    @Override
    @Transactional
    public Category delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category " + categoryId + " not found"));
        //category.getAds().forEach(ads -> ads.setCategory(null));
        categoryRepository.save(category);
        categoryRepository.delete(category);
        return category;
    }

    @Override
    public Category edit(Long categoryId, Category category) {
        return categoryRepository.findById(categoryId)
                .map(cat -> {
                    cat.setName(category.getName());
                    return categoryRepository.save(cat);
                })
                .orElseGet(() -> {
                    category.setId(categoryId);
                    return categoryRepository.save(category);
                });
    }
}

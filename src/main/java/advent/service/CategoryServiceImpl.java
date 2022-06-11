package advent.service;

import advent.model.Category;
import advent.repository.CategoryRepository;
import advent.service.serviceinterface.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category addNew(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> getAll(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return categoryRepository.findAll(paging);
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
        categoryRepository.deleteById(category.getId());
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

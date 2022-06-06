package advent.service;

import advent.model.Category;
import advent.repository.CategoryRepository;
import advent.service.serviceinterface.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addNew(Category entityBody) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public Category getById(Long entityId) {
        return null;
    }

    @Override
    public Category deleteById(Long entityId) {
        return null;
    }

    @Override
    public Category editById(Long entityId, Category entityBody) {
        return null;
    }
}

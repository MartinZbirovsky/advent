package advent.controller;

import advent.model.Category;
import advent.service.intf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.cons.GeneralCons.PAGE_NUMBER;
import static advent.cons.GeneralCons.PAGE_SIZE;

@RestController
@RequestMapping("/api/cat")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public Page<Category> getAllCategories ( @RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                             @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                             @RequestParam(defaultValue = "id") String sortBy){
        return categoryService.getAll(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public Category getCategoryById (@PathVariable Long id){
        return categoryService.get(id);
    }

    @PostMapping("")
    public Category addCategory (@Valid @RequestBody Category category){
        return categoryService.addNew(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.edit(id, category);
    }

    @DeleteMapping("/{id}")
    public Category deleteCategory(@PathVariable Long id) {
        return categoryService.delete(id);
    }
}

package com.example.democrud.controller;

import com.example.democrud.entity.Category;
import com.example.democrud.repository.CategoryRepository;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.service.CategoryService;
import com.example.democrud.service.impl.CategoryServiceImplHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CategoryController class
 *
 * @author TRI
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String test() {
        return "Hello";
    }

    /**
     * func get all Category
     *
     * @param categoryRequest
     * @return
     */
    @PostMapping("/get-all")
    public ResponseEntity findAll() {
        List<CategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/search")
    public ResponseEntity search(@RequestBody CategoryRequest categoryRequest) {
        if (!CategoryServiceImplHelper.isValidBeforeGetList(categoryRequest.getName())) {
            return new ResponseEntity("Vui lòng nhập chiều dài < 40 ký tự", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<CategoryResponse> categories = categoryService.getByName(categoryRequest.getName());
        return ResponseEntity.ok(categories);
    }

    /**
     * func get one Category
     *
     * @param id get
     * @return get one Category
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        CategoryResponse category = categoryService.getOneCategory(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * func add Category
     *
     * @param categoryRequest CategoryRequest
     * @return Category
     */
    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody CategoryRequest categoryRequest) {
        Optional<Category> existedCategory = categoryRepository.findByName(categoryRequest.getName());
        if (existedCategory.isPresent()) {
            return ResponseEntity.internalServerError().body("Đã tồn tại category: " + categoryRequest.getName());
        }
        CategoryResponse newCategory = categoryService.addCategory(categoryRequest);
        return ResponseEntity.ok(newCategory);
    }

    /**
     * fund add multi Category
     *
     * @param categoryRequests AddCategory
     * @return AddsCategory
     */
    @PostMapping("/add-multiple")
    public ResponseEntity<List<CategoryResponse>> addMultipleCategories(@RequestBody List<CategoryRequest> categoryRequests) {
        List<CategoryResponse> responses = categoryService.addCategoryMore(categoryRequests);
        if (responses.isEmpty()) {
            return ResponseEntity.badRequest().body(responses);
        }
        return ResponseEntity.ok(responses);
    }

    /**
     * func update Category
     *
     * @param category Category
     * @return Category
     */
    @PutMapping("/update")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest category) {
        return categoryService.updateCategory(category);
    }

    /**
     * func delete Category
     *
     * @param id long
     * @return boolean
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/list")
    public ResponseEntity<String> deleteCategories(@RequestBody List<Long> categoryIds) {
        categoryService.deleteMultipleCategories(categoryIds);
        return ResponseEntity.ok("Đã xóa các danh mục thành công");
    }

   /* @PostMapping
    public List<CategoryResponse> getAllCategoryPagination(@RequestBody PageRequestDto dto){
        Pageable pageable = new PageRequestDto().getPageable(dto);
        Page<CategoryResponse> categoryResponsePage = categoryRepository.findAll(pageable);
        return (List<CategoryResponse>) categoryResponsePage;
    }*/

}

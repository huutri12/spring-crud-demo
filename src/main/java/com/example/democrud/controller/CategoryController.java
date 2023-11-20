package com.example.democrud.controller;

import com.example.democrud.entity.Category;
import com.example.democrud.repository.CategoryRepository;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.service.CategoryService;
import com.example.democrud.service.Impl.CategoryServiceImplHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * CategoryController class
 *
 * @author TRI
 */
@RestController
@RequestMapping("/api")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/hello")
    @ApiOperation(value = "Get a greeting message", response = String.class)
    public String test() {
        return "Hello, Swagger";
    }

    @PostMapping("/search")
    public ResponseEntity searchCategoriesByName(@RequestBody CategoryRequest categoryRequest) {
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

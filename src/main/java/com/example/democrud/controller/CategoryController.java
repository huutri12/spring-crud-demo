package com.example.democrud.controller;

import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryController class
 *
 * @author TRI
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * func get all Category
     *
     * @return ResponseEntity
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return categoryService.findAll();
    }

    /**
     * func search
     *
     * @param categoryRequest
     * @return ResponseEntity
     */
    @PostMapping("/search")
    public ResponseEntity search(@RequestBody CategoryRequest categoryRequest) {
        String sort = categoryRequest.getSort().name();

        PageRequest pageRequest = PageRequest.of(
                categoryRequest.getPage(),
                categoryRequest.getSize(),
                Sort.Direction.valueOf(sort),
                categoryRequest.getSortByColumn()
        );
        return categoryService.search(categoryRequest, pageRequest);
    }

    /**
     * func get one Category
     *
     * @param id get
     * @return ResponseEntity
     */
    @GetMapping("/get/{id}")
    public ResponseEntity getCategory(@PathVariable Long id) {
        return categoryService.findOne(id);
    }

    /**
     * func add Category
     *
     * @param categoryRequest CategoryRequest
     * @return ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    /**
     * func update Category
     *
     * @param category Category
     * @return ResponseEntity
     */
    @PutMapping("/update")
    public ResponseEntity updateCategory(@RequestBody CategoryRequest category) {
        return categoryService.updateCategory(category);
    }

    /**
     * func delete Category
     *
     * @param id long
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {
        return categoryService.delete(id);
    }

}

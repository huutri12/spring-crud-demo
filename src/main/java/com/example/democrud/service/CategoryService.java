package com.example.democrud.service;

import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * CategoryService interface
 *
 * @author TRI
 */
public interface CategoryService {

    /**
     * func find all
     *
     * @return List<CategoryResponse>
     */
    ResponseEntity<List<CategoryResponse>> findAll();

    /**
     * func search
     *
     * @param categoryRequest
     * @return
     */
    ResponseEntity search(CategoryRequest categoryRequest);

    /**
     * func getOneCategory
     *
     * @param id long
     * @return getOneCategory
     */
    ResponseEntity findOne(Long id);

    /**
     * func add Category
     *
     * @param category Category
     * @return Category
     */
    ResponseEntity add(CategoryRequest category);

    /**
     * func update Category
     *
     * @param category Category
     * @return Category
     */
    ResponseEntity update(CategoryRequest category);

    /**
     * func delete
     *
     * @param id Long
     */
    ResponseEntity delete(Long id);

}

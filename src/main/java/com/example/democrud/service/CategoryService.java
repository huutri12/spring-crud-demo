package com.example.democrud.service;

import com.example.democrud.entity.Category;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * @return List<CategoryResponse>
     */
    List<CategoryResponse> findAll();

    /**
     * func add Category
     *
     * @param category Category
     * @return Category
     */
    CategoryResponse addCategory(CategoryRequest category);

    /**
     * func add more Category
     *
     * @param categoryRequestList Category
     * @return categoryRequestList
     */
    List<CategoryResponse> addCategoryMore(List<CategoryRequest> categoryRequestList);

    /**
     * func update Category
     *
     * @param category Category
     * @return Category
     */
    CategoryResponse updateCategory(CategoryRequest category);

    /**
     * func getOneCategory
     *
     * @param id long
     * @return getOneCategory
     */
    CategoryResponse getOneCategory(long id);

    /**
     * func deleteMultipleCategories
     *
     * @param categoryId deleteMultipleCategories
     */
    void deleteMultipleCategories(List<Long> categoryId);

    ResponseEntity search(CategoryRequest categoryRequest, PageRequest request);

}

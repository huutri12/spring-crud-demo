package com.example.democrud.service;

import com.example.democrud.entity.Category;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * CategoryService interface
 *
 * @author TRI
 */
public interface CategoryService {
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
     * func delete Category
     *
     * @param id long
     * @return boolean
     */
    boolean deleteCategory(long id);

    /**
     * func getAllCategory
     *
     * @return listAllCategory
     */
    Iterable<Category> getAllCategory();

    /**
     * func getOneCategory
     *
     * @param id long
     * @return getOneCategory
     */
    CategoryResponse getOneCategory(long id);

    /**
     * func getFilterByName
     *
     * @param name String
     * @return byName
     */
    List<CategoryResponse> getByName(String name);

    /**
     * func deleteMultipleCategories
     *
     * @param categoryId deleteMultipleCategories
     * @return deleteList
     */
    void deleteMultipleCategories(List<Long> categoryId);

    Page<CategoryResponse> findPaginated(int pageNo, int pageSize);
}

package com.example.democrud.service.impl;

import com.example.democrud.entity.Category;
import com.example.democrud.repository.CategoryRepository;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.service.CategoryService;
import com.example.democrud.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.democrud.utils.Constants.IS_DELETED.NO;

/**
 * CategoryServiceImpl class
 *
 * @author TRI
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private static final String AUTHOR = "TRI";

    private static CategoryResponse convertEntityToResponse(Optional<Category> category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.get().getId());
        categoryResponse.setName(category.get().getName());
        categoryResponse.setCreatedAt(LocalDateTime.now());
        categoryResponse.setUpdatedAt(LocalDateTime.now());
        categoryResponse.setCreatedBy(AUTHOR);
        categoryResponse.setUpdatedBy(AUTHOR);
        categoryResponse.setDeleted(NO);
        return categoryResponse;
    }

    private static Category convertRequestToEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setId(categoryRequest.getId());
        category.setName(categoryRequest.getName());
        category.setAfterName(categoryRequest.getAfterName());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setCreatedBy(AUTHOR);
        category.setUpdatedBy(AUTHOR);
        category.setDeleted(categoryRequest.isDeleted());
        return category;
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        categoryRequest.setDeleted(NO);
        categoryRequest.setAfterName(Helper.removeSign(categoryRequest.getName()));
        Category category = categoryRepository.save(convertRequestToEntity(categoryRequest));
        return convertEntityToResponse(Optional.of(category));
    }

    @Override
    public List<CategoryResponse> addCategoryMore(List<CategoryRequest> categoryRequestList) {
        // TODO: HieuTT: Yêu cầu bổ sung logic là nếu có category trong db thì phải thông báo là category đó đã tồn tại
        List<CategoryResponse> responses = new ArrayList<>();
        if (categoryRequestList != null && !categoryRequestList.isEmpty()) {
            List<Category> categoryList = categoryRequestList.stream().map(e -> convertRequestToEntity(e)).collect(Collectors.toList());

            categoryList = (List<Category>) categoryRepository.saveAll(categoryList);
            // Process after save -> Convert to Response
            List<CategoryResponse> categoryResponseList = categoryList.stream().map(e -> convertEntityToResponse(Optional.ofNullable(e))).collect(Collectors.toList());

            return categoryResponseList;
        }
        return responses;
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryRequest.getId());
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                category.setName(category.getName());
                category = categoryRepository.save(convertRequestToEntity(categoryRequest));
                return convertEntityToResponse(Optional.of(category));
            }
        }
        return null;
    }

    @Override
    public boolean deleteCategory(long id) {
        if (id < 1) {
            return false;
        }
        Optional<Category> category = categoryRepository.findById(id);
        if (category.get().getName() != null) {
            categoryRepository.softDeleteById(category.get().getId());
        }
        return true;
    }

    @Override
    public Iterable<Category> getAllCategory() {
        Iterable<Category> categoryAll = categoryRepository.findAll();
        return categoryAll;
    }

    @Override
    public CategoryResponse getOneCategory(long id) {
        return convertEntityToResponse(categoryRepository.findById(id));
    }

    @Override
    public List<CategoryResponse> getByName(String name) {
        name = name != null ? "%" + name + "%" : null;
        return categoryRepository.findByNameContaining(name).stream().map(e -> convertEntityToResponse(Optional.of(e))).collect(Collectors.toList());
    }

    @Override
    public void deleteMultipleCategories(List<Long> categoryIds) {
        //TODO: bổ sung thêm query để get danh sách ID thực sự tồn tại. Selec * From ID
        for (Long categoryId : categoryIds) {
            categoryRepository.deleteById(categoryId);
        }
    }

    @Override
    public Page<CategoryResponse> findPaginated(int pageNo, int pageSize) {
        return null;
    }

   /* @Override
    public Page<CategoryResponse> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.categoryRepository.findAll(pageable);
    }*/


}




package com.example.democrud.service.impl;

import com.example.democrud.entity.Category;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.democrud.utils.Constants.AUTHOR;
import static com.example.democrud.utils.Constants.IS_DELETED.NO;

public class CategoryServiceImplHelper {
    public static boolean isValidBeforeGetList(String name) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(name) && name.trim().length() > 40) {
            isValid = false;
        }
        return isValid;
    }

    public static CategoryResponse convertEntityToResponse(Optional<Category> category) {
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

    public static Category convertRequestToEntity(CategoryRequest categoryRequest) {
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
}

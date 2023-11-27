package com.example.democrud.service.impl;

import com.example.democrud.entity.CategoryEntity;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

import static com.example.democrud.utils.Constants.AUTHOR;
import static com.example.democrud.utils.Constants.IS_DELETED.NO;

public class CategoryServiceImplHelper {
    /**
     * func isValidBeforeGetList
     *
     * @param name String
     * @return boolean
     */
    public static boolean isValidBeforeGetList(String name) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(name) && name.trim().length() > 40) {
            isValid = false;
        }
        return isValid;
    }

    public static CategoryResponse convertEntityToResponse(CategoryEntity categoryEntity) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(categoryEntity.getId());
        categoryResponse.setName(categoryEntity.getName());
        categoryResponse.setCreatedAt(LocalDateTime.now());
        categoryResponse.setUpdatedAt(LocalDateTime.now());
        categoryResponse.setCreatedBy(AUTHOR);
        categoryResponse.setUpdatedBy(AUTHOR);
        categoryResponse.setDeleted(NO);
        return categoryResponse;
    }

    public static CategoryEntity convertRequestToEntity(CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryRequest.getId());
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setAfterName(categoryRequest.getAfterName());
        categoryEntity.setCreatedAt(LocalDateTime.now());
        categoryEntity.setUpdatedAt(LocalDateTime.now());
        categoryEntity.setCreatedBy(AUTHOR);
        categoryEntity.setUpdatedBy(AUTHOR);
        categoryEntity.setDeleted(categoryRequest.isDeleted());
        return categoryEntity;
    }
}

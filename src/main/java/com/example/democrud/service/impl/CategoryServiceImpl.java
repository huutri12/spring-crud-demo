package com.example.democrud.service.impl;

import com.example.democrud.entity.Category;
import com.example.democrud.repository.CategoryRepository;
import com.example.democrud.request.CategoryRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.service.CategoryService;
import com.example.democrud.utils.Mixin;
import com.example.democrud.utils.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.democrud.service.impl.CategoryServiceImplHelper.convertEntityToResponse;
import static com.example.democrud.service.impl.CategoryServiceImplHelper.convertRequestToEntity;
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

    @Override
    public List<CategoryResponse> findAll() {
        List<CategoryResponse> categoryResponseList = categoryRepository.findAll().stream().map(e -> convertEntityToResponse(Optional.ofNullable(e))).collect(Collectors.toList());
        return categoryResponseList;
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        categoryRequest.setDeleted(NO);
        categoryRequest.setAfterName(Mixin.removeSign(categoryRequest.getName()));
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
    public CategoryResponse getOneCategory(long id) {
        return convertEntityToResponse(categoryRepository.findById(id));
    }

    //    @Override
    public ResponseEntity search(CategoryRequest categoryRequest, PageRequest request) {
        String name = categoryRequest.getName();
        name = name != null ? "%" + name + "%" : null;
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), request.getSort());

        Page<Category> pageTuts = categoryRepository.findByNameContaining(name, pageable);
        List<CategoryResponse> categoryResponses = pageTuts.getContent()
                .stream().map(e -> convertEntityToResponse(Optional.of(e))).collect(Collectors.toList());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setCurrentPage(pageTuts.getNumber());
        pageResponse.setTotalElements(pageTuts.getTotalElements());
        pageResponse.setTotalPages(pageTuts.getTotalPages());
        pageResponse.setContents(categoryResponses);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);

    }

    @Override
    public void deleteMultipleCategories(List<Long> categoryIds) {
        //TODO: bổ sung thêm query để get danh sách ID thực sự tồn tại. Select * From ID
        for (Long categoryId : categoryIds) {
            categoryRepository.deleteById(categoryId);
        }
    }
}




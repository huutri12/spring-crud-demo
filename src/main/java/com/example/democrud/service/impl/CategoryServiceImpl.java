package com.example.democrud.service.impl;

import com.example.democrud.entity.CategoryEntity;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.democrud.service.impl.CategoryServiceImplHelper.*;
import static com.example.democrud.utils.Constants.*;
import static com.example.democrud.utils.Constants.IS_DELETED.NO;
import static com.example.democrud.utils.Constants.SORT_VALUE.ASC;

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
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categoryResponseList = categoryRepository.findAll().stream().map(CategoryServiceImplHelper::convertEntityToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(categoryResponseList);
    }

    @Override
    public ResponseEntity search(CategoryRequest categoryRequest) {
        String sort = categoryRequest.getSort().getValue().isEmpty() ?
                ASC : categoryRequest.getSort().getValue();
        String sortByColumn = categoryRequest.getSort().getValue().isEmpty() ?
                ID_COLUMN : categoryRequest.getSortByColumn().getValue();

        PageRequest pageRequest = PageRequest.of(
                categoryRequest.getPage(),
                categoryRequest.getSize(),
                Sort.Direction.valueOf(sort),
                sortByColumn
        );
        if (!isValidBeforeGetList(categoryRequest.getName())) {
            return new ResponseEntity("Vui lòng nhập chiều dài < 40 ký tự", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String name = categoryRequest.getName();
        name = name != null ? PERCENT + name + PERCENT : null;
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageRequest.getSort());

        Page<CategoryEntity> entities = categoryRepository.findByNameContaining(name, pageable);
        List<CategoryResponse> categoryResponses = entities.getContent()
                .stream().map(CategoryServiceImplHelper::convertEntityToResponse).collect(Collectors.toList());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setCurrentPage(entities.getNumber());
        pageResponse.setTotalElements(entities.getTotalElements());
        pageResponse.setTotalPages(entities.getTotalPages());
        pageResponse.setContents(categoryResponses);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findOne(Long id) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy id", HttpStatus.NOT_FOUND);
        }
        CategoryEntity categoryEntity = optionalCategory.get();
        if (categoryEntity.isDeleted() == IS_DELETED.YES) {
            return new ResponseEntity<>("Loại sản phẩm đã bị xóa", HttpStatus.BAD_REQUEST);
        }
        CategoryResponse categoryResponse = convertEntityToResponse(categoryEntity);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity add(CategoryRequest categoryRequest) {
        Optional<CategoryEntity> existedCategory = categoryRepository.findByNameAndDeletedEqualsFalse(categoryRequest.getName());
        if (existedCategory.isPresent()) {
            return new ResponseEntity("Đã tồn tại category: " + categoryRequest.getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        categoryRequest.setIsDeleted(NO);
        categoryRequest.setAfterName(Mixin.removeSign(categoryRequest.getName()));
        CategoryEntity categoryEntity = categoryRepository.save(convertRequestToEntity(categoryRequest));
        return new ResponseEntity(convertEntityToResponse(categoryEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return new ResponseEntity("Vui lòng nhập thông tin", HttpStatus.BAD_GATEWAY);
        }
        if (categoryRequest.getId() == null) {
            return new ResponseEntity("Vui lòng nhập id", HttpStatus.BAD_GATEWAY);
        }
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryRequest.getId());
        CategoryEntity categoryEntity = categoryOptional.get();
        categoryEntity.setName(categoryEntity.getName());
        categoryEntity = categoryRepository.save(convertRequestToEntity(categoryRequest));
        return new ResponseEntity(convertEntityToResponse(categoryEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy id", HttpStatus.NOT_FOUND);
        }
        CategoryEntity categoryEntity = optionalCategory.get();
        if (categoryEntity.isDeleted() == IS_DELETED.YES) {
            return new ResponseEntity<>("Loại sản phẩm đã bị xóa", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.softDeleteById(id);
        return new ResponseEntity("Xóa thành công", HttpStatus.OK);
    }
}




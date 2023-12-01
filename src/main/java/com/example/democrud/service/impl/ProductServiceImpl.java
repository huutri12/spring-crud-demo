package com.example.democrud.service.impl;

import com.example.democrud.entity.CategoryEntity;
import com.example.democrud.entity.ProductEntity;
import com.example.democrud.repository.CategoryRepository;
import com.example.democrud.repository.ProductRepository;
import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;
import com.example.democrud.service.ProductService;
import com.example.democrud.utils.Constants;
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

import static com.example.democrud.service.impl.CategoryServiceImplHelper.isValidBeforeGetList;
import static com.example.democrud.service.impl.ProductServiceImplHelper.convertEntityToResponse;
import static com.example.democrud.service.impl.ProductServiceImplHelper.convertRequestToEntity;
import static com.example.democrud.utils.Constants.ID_COLUMN;
import static com.example.democrud.utils.Constants.IS_DELETED.NO;
import static com.example.democrud.utils.Constants.PERCENT;
import static com.example.democrud.utils.Constants.SORT_VALUE.ASC;

/**
 * ProductServiceImpl class
 *
 * @author TRI
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> productResponseList = productRepository.findAll().stream().map(ProductServiceImplHelper::convertEntityToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(productResponseList);
    }

    @Override
    public ResponseEntity search(ProductRequest productRequest) {
        String sort = productRequest.getSort().getValue().isEmpty() ?
                ASC : productRequest.getSort().getValue();
        String sortByColumn = productRequest.getSort().getValue().isEmpty() ?
                ID_COLUMN : productRequest.getSortByColumn().getValue();

        PageRequest pageRequest = PageRequest.of(
                productRequest.getPage(),
                productRequest.getSize(),
                Sort.Direction.valueOf(sort),
                sortByColumn
        );
        if (!isValidBeforeGetList(productRequest.getName())) {
            return new ResponseEntity("Vui lòng nhập chiều dài < 40 ký tự", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String name = productRequest.getName();
        name = name != null ? PERCENT + name + PERCENT : null;
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageRequest.getSort());

        Page<ProductEntity> entities = productRepository.findByNameContaining(name, pageable);
        List<ProductResponse> productResponseList = entities.getContent()
                .stream().map(ProductServiceImplHelper::convertEntityToResponse).collect(Collectors.toList());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setCurrentPage(entities.getNumber());
        pageResponse.setTotalElements(entities.getTotalElements());
        pageResponse.setTotalPages(entities.getTotalPages());
        pageResponse.setContents(productResponseList);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findOne(Long id) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy id", HttpStatus.NOT_FOUND);
        }
        ProductEntity productEntity = optionalProduct.get();
        if (productEntity.isDeleted() == Constants.IS_DELETED.YES) {
            return new ResponseEntity<>("Sản phẩm đã bị xóa", HttpStatus.BAD_REQUEST);
        }
        ProductResponse productResponse = convertEntityToResponse(productEntity);
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(productEntity.getIdCategory());
        if (!categoryEntity.isPresent()) {
            return new ResponseEntity<>("Loại sản phẩm đã bị xóa", HttpStatus.BAD_REQUEST);
        }
        productResponse.setNameCategory(categoryEntity.get().getName());

        return new ResponseEntity(productResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity add(ProductRequest productRequest) {
        Optional<ProductEntity> existedProduct = productRepository.findByNameAndDeletedEqualsFalse(productRequest.getName());
        if (existedProduct.isPresent()) {
            return new ResponseEntity("Đã tồn tại sản phẩm: " + productRequest.getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        productRequest.setIsDeleted(NO);
        ProductEntity productEntity = productRepository.save(convertRequestToEntity(productRequest));
        return new ResponseEntity(convertEntityToResponse(productEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(ProductRequest productRequest) {
        if (productRequest == null) {
            return new ResponseEntity("Vui lòng nhập thông tin", HttpStatus.BAD_GATEWAY);
        }
        if (productRequest.getId() == null) {
            return new ResponseEntity("Vui lòng nhập id", HttpStatus.BAD_GATEWAY);
        }
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(productRequest.getIdCategory());
        CategoryEntity categoryEntity = categoryOptional.get();
        categoryEntity.setName(categoryEntity.getName());
        ProductEntity productEntity = productRepository.save(convertRequestToEntity(productRequest));
        return new ResponseEntity(convertEntityToResponse(productEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<ProductEntity> optionalCategory = productRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy id", HttpStatus.NOT_FOUND);
        }
        ProductEntity productEntity = optionalCategory.get();
        if (productEntity.isDeleted() == Constants.IS_DELETED.YES) {
            return new ResponseEntity<>("Sản phẩm đã bị xóa", HttpStatus.BAD_REQUEST);
        }
        productRepository.softDeleteById(id);
        return new ResponseEntity("Xóa thành công", HttpStatus.OK);
    }
}

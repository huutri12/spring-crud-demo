package com.example.democrud.service.impl;

import com.example.democrud.entity.CategoryEntity;
import com.example.democrud.entity.ProductEntity;
import com.example.democrud.repository.CategoryRepository;
import com.example.democrud.repository.ProductRepository;
import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;
import com.example.democrud.service.ProductService;
import com.example.democrud.utils.Constants;
import com.example.democrud.utils.Mixin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.democrud.service.impl.ProductServiceImplHelper.convertEntityToResponse;
import static com.example.democrud.service.impl.ProductServiceImplHelper.convertRequestToEntity;
import static com.example.democrud.utils.Constants.IS_DELETED.NO;

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
    public ResponseEntity addProduct(ProductRequest productRequest) {
        Optional<ProductEntity> existedProduct = productRepository.findByNameAndDeletedEqualsFalse(productRequest.getName());
        if (existedProduct.isPresent()) {
            return new ResponseEntity("Đã tồn tại sản phẩm: " + productRequest.getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        productRequest.setIsDeleted(NO);
        ProductEntity productEntity = productRepository.save(convertRequestToEntity(productRequest));
        return new ResponseEntity(convertEntityToResponse(productEntity), HttpStatus.OK);
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

package com.example.democrud.service.impl;

import com.example.democrud.entity.ProductEntity;
import com.example.democrud.repository.ProductRepository;
import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;
import com.example.democrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ProductServiceImpl class
 *
 * @author TRI
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    private static final String AUTHOR = "TRI";
    private static ProductResponse convertEntityToResponse(Optional<ProductEntity> product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.get().getId());
        productResponse.setName(product.get().getName());
        productResponse.setPrice(product.get().getPrice());
        productResponse.setCreatedAt(LocalDateTime.now());
        productResponse.setUpdatedAt(LocalDateTime.now());
        productResponse.setCreatedBy(AUTHOR);
        productResponse.setUpdatedBy(AUTHOR);
        return productResponse;
    }

    private static ProductEntity convertRequestToEntity(ProductRequest productRequest){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productRequest.getId());
        productEntity.setName(productRequest.getName());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setCreatedBy(AUTHOR);
        productEntity.setUpdatedBy(AUTHOR);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
        return productEntity;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        if (productRequest != null){
            ProductEntity productEntity = productRepository.save(convertRequestToEntity(productRequest));
            return convertEntityToResponse(Optional.of(productEntity));
        }
        return null;
    }
}

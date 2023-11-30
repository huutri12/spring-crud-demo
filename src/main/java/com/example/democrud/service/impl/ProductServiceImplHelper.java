package com.example.democrud.service.impl;

import com.example.democrud.entity.ProductEntity;
import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;

import java.time.LocalDateTime;

import static com.example.democrud.utils.Constants.AUTHOR;

public class ProductServiceImplHelper {

    public static ProductResponse convertEntityToResponse(ProductEntity product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setCreatedAt(LocalDateTime.now());
        productResponse.setUpdatedAt(LocalDateTime.now());
        productResponse.setCreatedBy(AUTHOR);
        productResponse.setUpdatedBy(AUTHOR);
        return productResponse;
    }

    public static ProductEntity convertRequestToEntity(ProductRequest productRequest) {
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
}

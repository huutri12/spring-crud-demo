package com.example.democrud.service.impl;

import com.example.democrud.entity.Product;
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
    private static ProductResponse convertEntityToResponse(Optional<Product> product){
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

    private static Product convertRequestToEntity(ProductRequest productRequest){
        Product product = new Product();
        product.setId(productRequest.getId());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCreatedBy(AUTHOR);
        product.setUpdatedBy(AUTHOR);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        if (productRequest != null){
            Product product = productRepository.save(convertRequestToEntity(productRequest));
            return convertEntityToResponse(Optional.of(product));
        }
        return null;
    }
}

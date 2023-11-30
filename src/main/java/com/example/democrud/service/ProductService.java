package com.example.democrud.service;

import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    /**
     * func add Product
     *
     * @param productRequest ProductRequest
     * @return ResponseEntity
     */
    ResponseEntity add(ProductRequest productRequest);

    ResponseEntity findOne(Long id);

    ResponseEntity delete(Long id);

    ResponseEntity update(ProductRequest categoryRequest);
}

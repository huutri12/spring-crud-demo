package com.example.democrud.service;

import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.response.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<List<ProductResponse>> findAll();

    ResponseEntity search(ProductRequest productRequest);

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

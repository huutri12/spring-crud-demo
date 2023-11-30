package com.example.democrud.service;

import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    /**
     * func add Product
     *
     * @param product Product
     * @return ResponseEntity
     */
    ResponseEntity addProduct(ProductRequest product);

    ResponseEntity findOne(Long id);

    ResponseEntity delete(Long id);

}

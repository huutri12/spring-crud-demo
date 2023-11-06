package com.example.democrud.service;

import com.example.democrud.request.CategoryRequest;
import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.CategoryResponse;
import com.example.democrud.response.ProductResponse;

public interface ProductService {
    /**
     * func add Product
     *
     * @param product Product
     * @return Product
     */
    ProductResponse addProduct(ProductRequest product);
}

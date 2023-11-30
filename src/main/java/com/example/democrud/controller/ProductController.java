package com.example.democrud.controller;

import com.example.democrud.request.ProductRequest;
import com.example.democrud.response.ProductResponse;
import com.example.democrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * func get one Category
     *
     * @param id get
     * @return ResponseEntity
     */
    @GetMapping("/get/{id}")
    public ResponseEntity getCategory(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @PostMapping("/add")
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    /**
     * func delete Category
     *
     * @param id long
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id) {
        return productService.delete(id);
    }
}

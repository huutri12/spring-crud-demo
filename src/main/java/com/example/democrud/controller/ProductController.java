package com.example.democrud.controller;

import com.example.democrud.request.ProductRequest;
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
    public ResponseEntity getProduct(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequest productRequest) {
        return productService.add(productRequest);
    }

    /**
     * func update Category
     *
     * @param category Category
     * @return ResponseEntity
     */
    @PutMapping("/update")
    public ResponseEntity updateProduct(@RequestBody ProductRequest category) {
        return productService.update(category);
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

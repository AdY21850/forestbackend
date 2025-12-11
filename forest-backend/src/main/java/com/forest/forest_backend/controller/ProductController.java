package com.forest.forest_backend.controller;


import com.forest.forest_backend.entity.Product;
import com.forest.forest_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController( ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @GetMapping
    public List<Product>getAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId){
        return productRepository.findByCategoryId(categoryId);

    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productRepository.save(product);
    }
}

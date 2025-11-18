package com.forest.forest_backend.repository;

import com.forest.forest_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByCategoryId(Long categoryId);
}

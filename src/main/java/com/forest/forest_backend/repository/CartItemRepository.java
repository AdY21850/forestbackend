package com.forest.forest_backend.repository;

import com.forest.forest_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface CartItemRepository extends JpaRepository<CartItem ,Long>{
 List<CartItem> findByCartId(Long cartId);
    }


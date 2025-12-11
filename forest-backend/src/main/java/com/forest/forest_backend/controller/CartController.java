package com.forest.forest_backend.controller;

import com.forest.forest_backend.entity.*;
import com.forest.forest_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartController(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestParam Long userId,
                              @RequestParam Long productId,
                              @RequestParam int quantity){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId).orElseGet(()->{
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product Not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    @GetMapping("/{userId}")
    public List<CartItem> getUserCart(@PathVariable Long userId){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("Cart Not Found"));
        return cartItemRepository.findByCartId(cart.getId());
    }
}
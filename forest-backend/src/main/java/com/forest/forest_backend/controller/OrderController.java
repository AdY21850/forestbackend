package com.forest.forest_backend.controller;

import com.forest.forest_backend.entity.*;
import com.forest.forest_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository; // Added to usage if needed

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/checkout/{userId}")
    public Order placeOrder(@PathVariable Long userId){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("Cart Not Found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create Order
        Order newOrder = new Order();
        newOrder.setUser(cart.getUser());
        newOrder.setStatus("CONFIRMED");
        newOrder.setCreatedAt(LocalDateTime.now());

        // This now works safely because we added 'address' to User entity
        String address = cart.getUser().getAddress();
        newOrder.setShippingAddress(address != null ? address : "Default Address");

        newOrder = orderRepository.save(newOrder);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());

            orderItemRepository.save(orderItem);

            BigDecimal itemTotal = cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }

        newOrder.setTotalPrice(totalPrice);
        orderRepository.save(newOrder);

        // Clear Cart
        cartItemRepository.deleteAll(cartItems);

        return newOrder;
    }

    @GetMapping("/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
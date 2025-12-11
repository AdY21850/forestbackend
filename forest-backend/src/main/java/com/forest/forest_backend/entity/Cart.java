package com.forest.forest_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
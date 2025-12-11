package com.forest.forest_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
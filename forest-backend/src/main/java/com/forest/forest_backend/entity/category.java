package com.forest.forest_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="categories")
public class category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    private String imageUrl;
}
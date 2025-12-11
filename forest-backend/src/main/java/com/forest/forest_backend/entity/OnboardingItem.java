package com.forest.forest_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "onboarding_items")
public class OnboardingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    // --- CRITICAL FIX ---
    // Change limit from 255 to 1000 to safely store long Cloudinary URLs
    @Column(length = 1000)
    private String imageUrl;
}
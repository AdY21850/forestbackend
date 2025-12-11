package com.forest.forest_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;


    private String phoneNumber;


    private String address;

    private String profilePictureUrl;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
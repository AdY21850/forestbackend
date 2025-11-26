package com.forest.forest_backend.repository;

import com.forest.forest_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

 Optional<User> findByPhoneNumber(String phoneNumber);
}

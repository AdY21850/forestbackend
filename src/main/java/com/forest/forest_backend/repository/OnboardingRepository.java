package com.forest.forest_backend.repository;

import com.forest.forest_backend.entity.OnboardingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingRepository extends JpaRepository<OnboardingItem, Long> {
}
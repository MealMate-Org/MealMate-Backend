package com.MealMate.MealMateBackend.user.repository;

import com.MealMate.MealMateBackend.user.model.UserAllergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAllergenRepository extends JpaRepository<UserAllergen, Long> {
    List<UserAllergen> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
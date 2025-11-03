package com.MealMate.MealMateBackend.user.repository;

import com.MealMate.MealMateBackend.user.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository extends JpaRepository<Diet, Integer> {
}
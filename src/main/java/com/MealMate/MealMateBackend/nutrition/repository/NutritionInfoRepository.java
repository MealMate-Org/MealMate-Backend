package com.MealMate.MealMateBackend.nutrition.repository;

import com.MealMate.MealMateBackend.nutrition.model.NutritionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionInfoRepository extends JpaRepository<NutritionInfo, Long> {
}
package com.MealMate.MealMateBackend.planner.repository;

import com.MealMate.MealMateBackend.planner.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
}
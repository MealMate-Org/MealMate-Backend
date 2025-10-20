package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanCreateDTO;
import java.util.List;

public interface MealPlanService {
    List<MealPlanDTO> getAllMealPlans();
    MealPlanDTO getMealPlanById(Long id);
    MealPlanDTO createMealPlan(MealPlanCreateDTO mealPlanCreateDTO);
    MealPlanDTO updateMealPlan(Long id, MealPlanDTO mealPlanDTO);
    void deleteMealPlan(Long id);
}
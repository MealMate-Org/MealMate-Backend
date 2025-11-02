package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanCreateDTO;
import java.time.LocalDate;
import java.util.List;

public interface MealPlanService {
    List<MealPlanDTO> getAllMealPlans();
    List<MealPlanDTO> getMealPlansByUserId(Long userId);
    MealPlanDTO getMealPlanById(Long id);
    MealPlanDTO getActiveByUserIdAndDate(Long userId, LocalDate date);
    MealPlanDTO createMealPlan(MealPlanCreateDTO mealPlanCreateDTO);
    MealPlanDTO getOrCreateMealPlanForWeek(Long userId, LocalDate weekStart, LocalDate weekEnd);
    MealPlanDTO updateMealPlan(Long id, MealPlanDTO mealPlanDTO);
    void deleteMealPlan(Long id);
}

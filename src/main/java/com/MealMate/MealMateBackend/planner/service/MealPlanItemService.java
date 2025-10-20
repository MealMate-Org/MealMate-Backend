package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import java.util.List;

public interface MealPlanItemService {
    List<MealPlanItemDTO> getAllMealPlanItems();
    MealPlanItemDTO getMealPlanItemById(Long id);
    MealPlanItemDTO createMealPlanItem(MealPlanItemCreateDTO mealPlanItemCreateDTO);
    MealPlanItemDTO updateMealPlanItem(Long id, MealPlanItemDTO mealPlanItemDTO);
    void deleteMealPlanItem(Long id);
}
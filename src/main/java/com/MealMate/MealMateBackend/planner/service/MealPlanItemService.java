package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import java.time.LocalDate;
import java.util.List;

public interface MealPlanItemService {
    List<MealPlanItemDTO> getAllMealPlanItems();
    List<MealPlanItemDTO> getItemsByMealPlanId(Long mealPlanId);
    List<MealPlanItemDTO> getItemsByMealPlanIdAndDateRange(Long mealPlanId, LocalDate startDate, LocalDate endDate);
    List<MealPlanItemDTO> getItemsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    MealPlanItemDTO getMealPlanItemById(Long id);
    MealPlanItemDTO createMealPlanItem(MealPlanItemCreateDTO mealPlanItemCreateDTO);
    List<MealPlanItemDTO> createMealPlanItemsBatch(List<MealPlanItemCreateDTO> createDTOs);
    MealPlanItemDTO updateMealPlanItem(Long id, MealPlanItemDTO mealPlanItemDTO);
    void deleteMealPlanItem(Long id);
    void deleteMealPlanItemByDetails(Long mealPlanId, LocalDate date, Integer mealTypeId);
}

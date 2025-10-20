package com.MealMate.MealMateBackend.nutrition.service;

import com.MealMate.MealMateBackend.nutrition.dto.NutritionInfoDTO;

public interface NutritionInfoService {
    NutritionInfoDTO getNutritionInfoByRecipeId(Long recipeId);
    NutritionInfoDTO createOrUpdateNutritionInfo(NutritionInfoDTO nutritionInfoDTO);
    void deleteNutritionInfo(Long recipeId);
}
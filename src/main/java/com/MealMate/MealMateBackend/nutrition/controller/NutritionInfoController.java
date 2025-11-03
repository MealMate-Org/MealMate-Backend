package com.MealMate.MealMateBackend.nutrition.controller;

import com.MealMate.MealMateBackend.nutrition.dto.NutritionInfoDTO;
import com.MealMate.MealMateBackend.nutrition.service.NutritionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nutrition-info")
public class NutritionInfoController {

    @Autowired
    private NutritionInfoService nutritionInfoService;

    @GetMapping("/{recipeId}")
    public ResponseEntity<NutritionInfoDTO> getNutritionInfoByRecipeId(@PathVariable Long recipeId) {
        return ResponseEntity.ok(nutritionInfoService.getNutritionInfoByRecipeId(recipeId));
    }

    @PostMapping
    public ResponseEntity<NutritionInfoDTO> createOrUpdateNutritionInfo(@RequestBody NutritionInfoDTO nutritionInfoDTO) {
        return ResponseEntity.ok(nutritionInfoService.createOrUpdateNutritionInfo(nutritionInfoDTO));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteNutritionInfo(@PathVariable Long recipeId) {
        nutritionInfoService.deleteNutritionInfo(recipeId);
        return ResponseEntity.noContent().build();
    }
}
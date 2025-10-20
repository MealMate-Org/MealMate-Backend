package com.MealMate.MealMateBackend.planner.controller;

import com.MealMate.MealMateBackend.planner.dto.MealPlanDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanCreateDTO;
import com.MealMate.MealMateBackend.planner.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal-plans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @GetMapping
    public ResponseEntity<List<MealPlanDTO>> getAllMealPlans() {
        return ResponseEntity.ok(mealPlanService.getAllMealPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlanDTO> getMealPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(mealPlanService.getMealPlanById(id));
    }

    @PostMapping
    public ResponseEntity<MealPlanDTO> createMealPlan(@RequestBody MealPlanCreateDTO mealPlanCreateDTO) {
        return ResponseEntity.ok(mealPlanService.createMealPlan(mealPlanCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlanDTO> updateMealPlan(@PathVariable Long id, @RequestBody MealPlanDTO mealPlanDTO) {
        return ResponseEntity.ok(mealPlanService.updateMealPlan(id, mealPlanDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
        mealPlanService.deleteMealPlan(id);
        return ResponseEntity.noContent().build();
    }
}
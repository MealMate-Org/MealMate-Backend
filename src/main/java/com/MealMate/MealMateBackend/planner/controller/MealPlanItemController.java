package com.MealMate.MealMateBackend.planner.controller;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import com.MealMate.MealMateBackend.planner.service.MealPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal-plan-items")
public class MealPlanItemController {

    @Autowired
    private MealPlanItemService mealPlanItemService;

    @GetMapping
    public ResponseEntity<List<MealPlanItemDTO>> getAllMealPlanItems() {
        return ResponseEntity.ok(mealPlanItemService.getAllMealPlanItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlanItemDTO> getMealPlanItemById(@PathVariable Long id) {
        return ResponseEntity.ok(mealPlanItemService.getMealPlanItemById(id));
    }

    @PostMapping
    public ResponseEntity<MealPlanItemDTO> createMealPlanItem(@RequestBody MealPlanItemCreateDTO mealPlanItemCreateDTO) {
        return ResponseEntity.ok(mealPlanItemService.createMealPlanItem(mealPlanItemCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlanItemDTO> updateMealPlanItem(@PathVariable Long id, @RequestBody MealPlanItemDTO mealPlanItemDTO) {
        return ResponseEntity.ok(mealPlanItemService.updateMealPlanItem(id, mealPlanItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlanItem(@PathVariable Long id) {
        mealPlanItemService.deleteMealPlanItem(id);
        return ResponseEntity.noContent().build();
    }
}
package com.MealMate.MealMateBackend.planner.controller;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import com.MealMate.MealMateBackend.planner.service.MealPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meal-plan-items")
@CrossOrigin(origins = "*")
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

    @GetMapping("/meal-plan/{mealPlanId}")
    public ResponseEntity<List<MealPlanItemDTO>> getItemsByMealPlanId(@PathVariable Long mealPlanId) {
        return ResponseEntity.ok(mealPlanItemService.getItemsByMealPlanId(mealPlanId));
    }

    @GetMapping("/meal-plan/{mealPlanId}/date-range")
    public ResponseEntity<List<MealPlanItemDTO>> getItemsByMealPlanIdAndDateRange(
            @PathVariable Long mealPlanId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(mealPlanItemService.getItemsByMealPlanIdAndDateRange(
            mealPlanId, startDate, endDate));
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<MealPlanItemDTO>> getItemsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(mealPlanItemService.getItemsByUserIdAndDateRange(
            userId, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<MealPlanItemDTO> createMealPlanItem(
            @RequestBody MealPlanItemCreateDTO mealPlanItemCreateDTO) {
        return ResponseEntity.ok(mealPlanItemService.createMealPlanItem(mealPlanItemCreateDTO));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MealPlanItemDTO>> createMealPlanItemsBatch(
            @RequestBody List<MealPlanItemCreateDTO> createDTOs) {
        return ResponseEntity.ok(mealPlanItemService.createMealPlanItemsBatch(createDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlanItemDTO> updateMealPlanItem(
            @PathVariable Long id, 
            @RequestBody MealPlanItemDTO mealPlanItemDTO) {
        return ResponseEntity.ok(mealPlanItemService.updateMealPlanItem(id, mealPlanItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlanItem(@PathVariable Long id) {
        mealPlanItemService.deleteMealPlanItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/meal-plan/{mealPlanId}/date/{date}/meal-type/{mealTypeId}")
    public ResponseEntity<Void> deleteMealPlanItemByDetails(
            @PathVariable Long mealPlanId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable Integer mealTypeId) {
        mealPlanItemService.deleteMealPlanItemByDetails(mealPlanId, date, mealTypeId);
        return ResponseEntity.noContent().build();
    }
}

package com.MealMate.MealMateBackend.planner.controller;

import com.MealMate.MealMateBackend.planner.dto.MealTypeDTO;
import com.MealMate.MealMateBackend.planner.service.MealTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal-types")
public class MealTypeController {

    @Autowired
    private MealTypeService mealTypeService;

    @GetMapping
    public ResponseEntity<List<MealTypeDTO>> getAllMealTypes() {
        return ResponseEntity.ok(mealTypeService.getAllMealTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealTypeDTO> getMealTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(mealTypeService.getMealTypeById(id));
    }

    @PostMapping
    public ResponseEntity<MealTypeDTO> createMealType(@RequestBody MealTypeDTO mealTypeDTO) {
        return ResponseEntity.ok(mealTypeService.createMealType(mealTypeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealTypeDTO> updateMealType(@PathVariable Integer id, @RequestBody MealTypeDTO mealTypeDTO) {
        return ResponseEntity.ok(mealTypeService.updateMealType(id, mealTypeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealType(@PathVariable Integer id) {
        mealTypeService.deleteMealType(id);
        return ResponseEntity.noContent().build();
    }
}
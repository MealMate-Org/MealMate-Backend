package com.MealMate.MealMateBackend.user.controller;

import com.MealMate.MealMateBackend.user.dto.DietDTO;
import com.MealMate.MealMateBackend.user.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diets")
public class DietController {

    @Autowired
    private DietService dietService;

    @GetMapping
    public ResponseEntity<List<DietDTO>> getAllDiets() {
        return ResponseEntity.ok(dietService.getAllDiets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietDTO> getDietById(@PathVariable Integer id) {
        return ResponseEntity.ok(dietService.getDietById(id));
    }

    @PostMapping
    public ResponseEntity<DietDTO> createDiet(@RequestBody DietDTO dietDTO) {
        return ResponseEntity.ok(dietService.createDiet(dietDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietDTO> updateDiet(@PathVariable Integer id, @RequestBody DietDTO dietDTO) {
        return ResponseEntity.ok(dietService.updateDiet(id, dietDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiet(@PathVariable Integer id) {
        dietService.deleteDiet(id);
        return ResponseEntity.noContent().build();
    }
}
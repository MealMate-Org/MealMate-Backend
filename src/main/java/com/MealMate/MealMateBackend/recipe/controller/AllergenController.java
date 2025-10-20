package com.MealMate.MealMateBackend.recipe.controller;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import com.MealMate.MealMateBackend.recipe.service.AllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/allergens")
public class AllergenController {

    @Autowired
    private AllergenService allergenService;

    @GetMapping
    public ResponseEntity<List<AllergenDTO>> getAllAllergens() {
        return ResponseEntity.ok(allergenService.getAllAllergens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergenDTO> getAllergenById(@PathVariable Integer id) {
        return ResponseEntity.ok(allergenService.getAllergenById(id));
    }

    @PostMapping
    public ResponseEntity<AllergenDTO> createAllergen(@RequestBody AllergenDTO allergenDTO) {
        return ResponseEntity.ok(allergenService.createAllergen(allergenDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllergenDTO> updateAllergen(@PathVariable Integer id, @RequestBody AllergenDTO allergenDTO) {
        return ResponseEntity.ok(allergenService.updateAllergen(id, allergenDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Integer id) {
        allergenService.deleteAllergen(id);
        return ResponseEntity.noContent().build();
    }
}
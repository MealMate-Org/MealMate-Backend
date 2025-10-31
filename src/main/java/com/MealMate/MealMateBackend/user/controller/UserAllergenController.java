package com.MealMate.MealMateBackend.user.controller;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import com.MealMate.MealMateBackend.user.service.UserAllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-allergens")
public class UserAllergenController {

    @Autowired
    private UserAllergenService userAllergenService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<AllergenDTO>> getUserAllergens(@PathVariable Long userId) {
        return ResponseEntity.ok(userAllergenService.getUserAllergens(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> saveUserAllergens(
            @PathVariable Long userId,
            @RequestBody List<Integer> allergenIds) {
        userAllergenService.saveUserAllergens(userId, allergenIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAllUserAllergens(@PathVariable Long userId) {
        userAllergenService.deleteAllUserAllergens(userId);
        return ResponseEntity.noContent().build();
    }
}
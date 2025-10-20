package com.MealMate.MealMateBackend.user.controller;

import com.MealMate.MealMateBackend.user.dto.UserPreferenceDTO;
import com.MealMate.MealMateBackend.user.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-preferences")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPreferenceDTO> getUserPreferenceByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userPreferenceService.getUserPreferenceByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserPreferenceDTO> createOrUpdateUserPreference(@RequestBody UserPreferenceDTO userPreferenceDTO) {
        return ResponseEntity.ok(userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserPreference(@PathVariable Long userId) {
        userPreferenceService.deleteUserPreference(userId);
        return ResponseEntity.noContent().build();
    }
}
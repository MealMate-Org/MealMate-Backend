package com.MealMate.MealMateBackend.rating.controller;

import com.MealMate.MealMateBackend.rating.dto.RatingDTO;
import com.MealMate.MealMateBackend.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/{recipeId}/{userId}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable Long recipeId, @PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.getRatingById(recipeId, userId));
    }

    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.ok(ratingService.createRating(ratingDTO));
    }

    @PutMapping("/{recipeId}/{userId}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable Long recipeId, @PathVariable Long userId, @RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.ok(ratingService.updateRating(recipeId, userId, ratingDTO));
    }

    @DeleteMapping("/{recipeId}/{userId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long recipeId, @PathVariable Long userId) {
        ratingService.deleteRating(recipeId, userId);
        return ResponseEntity.noContent().build();
    }
}
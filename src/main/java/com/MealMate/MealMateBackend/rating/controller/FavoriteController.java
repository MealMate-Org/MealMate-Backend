package com.MealMate.MealMateBackend.rating.controller;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import com.MealMate.MealMateBackend.rating.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        return ResponseEntity.ok(favoriteService.getAllFavorites());
    }

    @GetMapping("/{userId}/{recipeId}")
    public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable Long userId, @PathVariable Long recipeId) {
        return ResponseEntity.ok(favoriteService.getFavoriteById(userId, recipeId));
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        return ResponseEntity.ok(favoriteService.createFavorite(favoriteDTO));
    }

    @DeleteMapping("/{userId}/{recipeId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long userId, @PathVariable Long recipeId) {
        favoriteService.deleteFavorite(userId, recipeId);
        return ResponseEntity.noContent().build();
    }
}
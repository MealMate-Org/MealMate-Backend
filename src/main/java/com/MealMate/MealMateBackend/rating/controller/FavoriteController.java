package com.MealMate.MealMateBackend.rating.controller;

import com.MealMate.MealMateBackend.rating.dto.FavoriteDTO;
import com.MealMate.MealMateBackend.rating.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@CrossOrigin(origins = "http://localhost:4200")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        try {
            List<FavoriteDTO> favorites = favoriteService.getAllFavorites();
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{userId}/{recipeId}")
    public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable Long userId, @PathVariable Long recipeId) {
        try {
            FavoriteDTO favorite = favoriteService.getFavoriteById(userId, recipeId);
            return ResponseEntity.ok(favorite);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        try {
            FavoriteDTO created = favoriteService.createFavorite(favoriteDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{userId}/{recipeId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long userId, @PathVariable Long recipeId) {
        try {
            favoriteService.deleteFavorite(userId, recipeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
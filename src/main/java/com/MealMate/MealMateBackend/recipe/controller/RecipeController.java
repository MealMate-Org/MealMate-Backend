package com.MealMate.MealMateBackend.recipe.controller;

import com.MealMate.MealMateBackend.recipe.dto.RecipeDTO;
import com.MealMate.MealMateBackend.recipe.dto.RecipeCreateDTO;
import com.MealMate.MealMateBackend.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(
            @RequestParam(required = false) Boolean isPublic,
            @RequestParam(required = false) Long authorId) {
        
        if (isPublic != null && isPublic) {
            // Solo recetas públicas
            return ResponseEntity.ok(recipeService.getPublicRecipes());
        } else if (authorId != null) {
            // Solo recetas del autor
            return ResponseEntity.ok(recipeService.getRecipesByAuthor(authorId));
        } else {
            // Todas las recetas
            return ResponseEntity.ok(recipeService.getAllRecipes());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeCreateDTO recipeCreateDTO) {
        return ResponseEntity.ok(recipeService.createRecipe(recipeCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
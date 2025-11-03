package com.MealMate.MealMateBackend.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.MealMate.MealMateBackend.recipe.model.Allergen;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private String imagePath;
    private Long authorId;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Double avgRating;
    private Integer ratingCount;
    private List<IngredientItem> ingredients;
    private List<Allergen> allergens;
    private Integer mealTypeId;
}
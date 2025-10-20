package com.MealMate.MealMateBackend.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateDTO {
    private String title;
    private String description;
    private String instructions;
    private String imagePath;
    private Long authorId;
    private Boolean isPublic;
    private List<IngredientItem> ingredients;
}
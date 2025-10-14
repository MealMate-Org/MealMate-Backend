package com.MealMate.MealMateBackend.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientItem {
    private String name;
    private Double quantity;
    private String unit;
}
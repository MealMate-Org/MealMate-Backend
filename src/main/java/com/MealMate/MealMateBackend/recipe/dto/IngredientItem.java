package com.MealMate.MealMateBackend.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientItem {
    private String name;
    private BigDecimal quantity;
    private String unit;
}
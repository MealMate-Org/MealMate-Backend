package com.MealMate.MealMateBackend.nutrition.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nutrition_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInfo {
    @Id
    private Long recipeId;

    @Column(precision = 6, scale = 2)
    private Double calories;

    @Column(precision = 6, scale = 2)
    private Double protein;

    @Column(precision = 6, scale = 2)
    private Double carbs;

    @Column(precision = 6, scale = 2)
    private Double fat;

    @Column(precision = 10, scale = 2)
    private Double portionSize;
}
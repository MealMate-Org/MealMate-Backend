package com.MealMate.MealMateBackend.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanItemDTO {
    private Long id;
    private Long mealPlanId;
    private Long recipeId;
    private Integer mealTypeId;
    private LocalDate date;
}
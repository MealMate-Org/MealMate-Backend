package com.MealMate.MealMateBackend.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Long recipeId;
    private Long userId;
    private Integer score;
}
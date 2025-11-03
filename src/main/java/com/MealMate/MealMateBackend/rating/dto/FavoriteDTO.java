package com.MealMate.MealMateBackend.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {
    private Long userId;
    private Long recipeId;
    private LocalDateTime createdAt;
}
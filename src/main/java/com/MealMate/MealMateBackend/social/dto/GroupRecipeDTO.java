package com.MealMate.MealMateBackend.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRecipeDTO {
    private Long groupId;
    private Long recipeId;
}
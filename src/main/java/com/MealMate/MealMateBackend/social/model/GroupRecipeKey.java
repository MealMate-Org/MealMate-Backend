package com.MealMate.MealMateBackend.social.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRecipeKey {
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "recipe_id")
    private Long recipeId;
}
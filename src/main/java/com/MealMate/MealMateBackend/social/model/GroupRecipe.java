package com.MealMate.MealMateBackend.social.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRecipe {
    @EmbeddedId
    private GroupRecipeKey id;
}
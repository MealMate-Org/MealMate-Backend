package com.MealMate.MealMateBackend.recipe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private com.MealMate.MealMateBackend.user.model.User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private com.MealMate.MealMateBackend.social.model.Group group;
}
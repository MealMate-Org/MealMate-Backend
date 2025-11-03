package com.MealMate.MealMateBackend.rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteKey implements Serializable {
    
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recipe_id")
    private Long recipeId;
}
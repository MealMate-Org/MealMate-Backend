package com.MealMate.MealMateBackend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_allergens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAllergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "allergen_id", nullable = false)
    private com.MealMate.MealMateBackend.recipe.model.Allergen allergen;
}
package com.MealMate.MealMateBackend.recipe.repository;

import com.MealMate.MealMateBackend.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
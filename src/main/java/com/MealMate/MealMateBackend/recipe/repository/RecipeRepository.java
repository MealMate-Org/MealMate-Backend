package com.MealMate.MealMateBackend.recipe.repository;

import com.MealMate.MealMateBackend.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM meal_plan_items WHERE recipe_id = :recipeId;
        DELETE FROM recipe_permissions WHERE recipe_id = :recipeId;
        DELETE FROM group_recipes WHERE recipe_id = :recipeId;
        DELETE FROM favorites WHERE recipe_id = :recipeId;
        DELETE FROM ratings WHERE recipe_id = :recipeId;
        DELETE FROM nutrition_info WHERE recipe_id = :recipeId;
        """, nativeQuery = true)
    void deleteRecipeReferences(@Param("recipeId") Long recipeId);
}
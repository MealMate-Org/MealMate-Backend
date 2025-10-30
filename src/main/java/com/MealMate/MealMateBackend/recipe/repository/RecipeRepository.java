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
    @Query(value = "DELETE FROM meal_plan_items WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteMealPlanItemsByRecipeId(@Param("recipeId") Long recipeId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM group_recipes WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteGroupRecipesByRecipeId(@Param("recipeId") Long recipeId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favorites WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteFavoritesByRecipeId(@Param("recipeId") Long recipeId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ratings WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteRatingsByRecipeId(@Param("recipeId") Long recipeId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM nutrition_info WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteNutritionInfoByRecipeId(@Param("recipeId") Long recipeId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM recipe_allergens WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteRecipeAllergensByRecipeId(@Param("recipeId") Long recipeId);
}
package com.MealMate.MealMateBackend.recipe.service;

import com.MealMate.MealMateBackend.recipe.dto.RecipeDTO;
import com.MealMate.MealMateBackend.recipe.dto.RecipeCreateDTO;
import java.util.List;

public interface RecipeService {
    List<RecipeDTO> getAllRecipes();
    RecipeDTO getRecipeById(Long id);
    RecipeDTO createRecipe(RecipeCreateDTO recipeCreateDTO);
    RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
    void deleteRecipe(Long id);
}
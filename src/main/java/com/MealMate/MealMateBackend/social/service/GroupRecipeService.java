package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.GroupRecipeDTO;
import java.util.List;

public interface GroupRecipeService {
    List<GroupRecipeDTO> getAllGroupRecipes();
    GroupRecipeDTO getGroupRecipeById(Long groupId, Long recipeId);
    GroupRecipeDTO createGroupRecipe(GroupRecipeDTO groupRecipeDTO);
    void deleteGroupRecipe(Long groupId, Long recipeId);
}
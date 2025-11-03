package com.MealMate.MealMateBackend.social.controller;

import com.MealMate.MealMateBackend.social.dto.GroupRecipeDTO;
import com.MealMate.MealMateBackend.social.service.GroupRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group-recipes")
public class GroupRecipeController {

    @Autowired
    private GroupRecipeService groupRecipeService;

    @GetMapping
    public ResponseEntity<List<GroupRecipeDTO>> getAllGroupRecipes() {
        return ResponseEntity.ok(groupRecipeService.getAllGroupRecipes());
    }

    @GetMapping("/{groupId}/{recipeId}")
    public ResponseEntity<GroupRecipeDTO> getGroupRecipeById(@PathVariable Long groupId, @PathVariable Long recipeId) {
        return ResponseEntity.ok(groupRecipeService.getGroupRecipeById(groupId, recipeId));
    }

    @PostMapping
    public ResponseEntity<GroupRecipeDTO> createGroupRecipe(@RequestBody GroupRecipeDTO groupRecipeDTO) {
        return ResponseEntity.ok(groupRecipeService.createGroupRecipe(groupRecipeDTO));
    }

    @DeleteMapping("/{groupId}/{recipeId}")
    public ResponseEntity<Void> deleteGroupRecipe(@PathVariable Long groupId, @PathVariable Long recipeId) {
        groupRecipeService.deleteGroupRecipe(groupId, recipeId);
        return ResponseEntity.noContent().build();
    }
}
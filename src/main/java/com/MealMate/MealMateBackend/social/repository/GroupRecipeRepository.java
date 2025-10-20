package com.MealMate.MealMateBackend.social.repository;

import com.MealMate.MealMateBackend.social.model.GroupRecipe;
import com.MealMate.MealMateBackend.social.model.GroupRecipeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface GroupRecipeRepository extends JpaRepository<GroupRecipe, GroupRecipeKey> {
    
    @Query("SELECT gr FROM GroupRecipe gr WHERE gr.id.groupId = :groupId AND gr.id.recipeId = :recipeId")
    Optional<GroupRecipe> findByGroupIdAndRecipeId(@Param("groupId") Long groupId, @Param("recipeId") Long recipeId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM GroupRecipe gr WHERE gr.id.groupId = :groupId AND gr.id.recipeId = :recipeId")
    void deleteByGroupIdAndRecipeId(@Param("groupId") Long groupId, @Param("recipeId") Long recipeId);
}
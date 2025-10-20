package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.GroupRecipeDTO;
import com.MealMate.MealMateBackend.social.model.GroupRecipe;
import com.MealMate.MealMateBackend.social.repository.GroupRecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupRecipeServiceImpl implements GroupRecipeService {

    @Autowired
    private GroupRecipeRepository groupRecipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GroupRecipeDTO> getAllGroupRecipes() {
        return groupRecipeRepository.findAll().stream()
                .map(groupRecipe -> modelMapper.map(groupRecipe, GroupRecipeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroupRecipeDTO getGroupRecipeById(Long groupId, Long recipeId) {
        GroupRecipe groupRecipe = groupRecipeRepository.findByGroupIdAndRecipeId(groupId, recipeId).orElseThrow(() -> new RuntimeException("GroupRecipe not found"));
        return modelMapper.map(groupRecipe, GroupRecipeDTO.class);
    }

    @Override
    public GroupRecipeDTO createGroupRecipe(GroupRecipeDTO groupRecipeDTO) {
        GroupRecipe groupRecipe = modelMapper.map(groupRecipeDTO, GroupRecipe.class);
        GroupRecipe savedGroupRecipe = groupRecipeRepository.save(groupRecipe);
        return modelMapper.map(savedGroupRecipe, GroupRecipeDTO.class);
    }

    @Override
    public void deleteGroupRecipe(Long groupId, Long recipeId) {
        groupRecipeRepository.deleteByGroupIdAndRecipeId(groupId, recipeId);
    }
}
package com.MealMate.MealMateBackend.nutrition.service;

import com.MealMate.MealMateBackend.nutrition.dto.NutritionInfoDTO;
import com.MealMate.MealMateBackend.nutrition.model.NutritionInfo;
import com.MealMate.MealMateBackend.nutrition.repository.NutritionInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutritionInfoServiceImpl implements NutritionInfoService {

    @Autowired
    private NutritionInfoRepository nutritionInfoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NutritionInfoDTO getNutritionInfoByRecipeId(Long recipeId) {
        NutritionInfo nutritionInfo = nutritionInfoRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("NutritionInfo not found"));
        return modelMapper.map(nutritionInfo, NutritionInfoDTO.class);
    }

    @Override
    public NutritionInfoDTO createOrUpdateNutritionInfo(NutritionInfoDTO nutritionInfoDTO) {
        NutritionInfo nutritionInfo = modelMapper.map(nutritionInfoDTO, NutritionInfo.class);
        NutritionInfo savedNutritionInfo = nutritionInfoRepository.save(nutritionInfo);
        return modelMapper.map(savedNutritionInfo, NutritionInfoDTO.class);
    }

    @Override
    public void deleteNutritionInfo(Long recipeId) {
        nutritionInfoRepository.deleteById(recipeId);
    }
}
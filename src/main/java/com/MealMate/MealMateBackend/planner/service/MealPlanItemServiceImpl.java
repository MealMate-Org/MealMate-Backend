package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import com.MealMate.MealMateBackend.planner.model.MealPlan;
import com.MealMate.MealMateBackend.planner.model.MealPlanItem;
import com.MealMate.MealMateBackend.planner.model.MealType;
import com.MealMate.MealMateBackend.planner.repository.MealPlanItemRepository;
import com.MealMate.MealMateBackend.planner.repository.MealPlanRepository;
import com.MealMate.MealMateBackend.planner.repository.MealTypeRepository;
import com.MealMate.MealMateBackend.recipe.model.Recipe;
import com.MealMate.MealMateBackend.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanItemServiceImpl implements MealPlanItemService {

    @Autowired
    private MealPlanItemRepository mealPlanItemRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Override
    public List<MealPlanItemDTO> getAllMealPlanItems() {
        return mealPlanItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealPlanItemDTO> getItemsByMealPlanId(Long mealPlanId) {
        return mealPlanItemRepository.findByMealPlanId(mealPlanId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealPlanItemDTO> getItemsByMealPlanIdAndDateRange(
            Long mealPlanId, LocalDate startDate, LocalDate endDate) {
        return mealPlanItemRepository.findByMealPlanIdAndDateRange(mealPlanId, startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealPlanItemDTO> getItemsByUserIdAndDateRange(
            Long userId, LocalDate startDate, LocalDate endDate) {
        return mealPlanItemRepository.findByUserIdAndDateRange(userId, startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MealPlanItemDTO getMealPlanItemById(Long id) {
        MealPlanItem mealPlanItem = mealPlanItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MealPlanItem not found"));
        return convertToDTO(mealPlanItem);
    }

    @Override
    @Transactional
    public MealPlanItemDTO createMealPlanItem(MealPlanItemCreateDTO createDTO) {
        MealPlan mealPlan = mealPlanRepository.findById(createDTO.getMealPlanId())
                .orElseThrow(() -> new RuntimeException("MealPlan not found"));
        
        Recipe recipe = recipeRepository.findById(createDTO.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        
        MealType mealType = mealTypeRepository.findById(createDTO.getMealTypeId())
                .orElseThrow(() -> new RuntimeException("MealType not found"));

        var existing = mealPlanItemRepository.findByMealPlanIdAndDateAndMealTypeId(
            createDTO.getMealPlanId(),
            createDTO.getDate(),
            createDTO.getMealTypeId()
        );

        if (existing.isPresent()) {
            MealPlanItem item = existing.get();
            item.setRecipe(recipe);
            MealPlanItem updated = mealPlanItemRepository.save(item);
            return convertToDTO(updated);
        }

        MealPlanItem mealPlanItem = new MealPlanItem();
        mealPlanItem.setMealPlan(mealPlan);
        mealPlanItem.setRecipe(recipe);
        mealPlanItem.setMealType(mealType);
        mealPlanItem.setDate(createDTO.getDate());

        MealPlanItem savedItem = mealPlanItemRepository.save(mealPlanItem);
        return convertToDTO(savedItem);
    }

    @Override
    @Transactional
    public List<MealPlanItemDTO> createMealPlanItemsBatch(List<MealPlanItemCreateDTO> createDTOs) {
        return createDTOs.stream()
                .map(this::createMealPlanItem)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MealPlanItemDTO updateMealPlanItem(Long id, MealPlanItemDTO mealPlanItemDTO) {
        MealPlanItem mealPlanItem = mealPlanItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MealPlanItem not found"));

        if (mealPlanItemDTO.getRecipeId() != null) {
            Recipe recipe = recipeRepository.findById(mealPlanItemDTO.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));
            mealPlanItem.setRecipe(recipe);
        }

        if (mealPlanItemDTO.getMealTypeId() != null) {
            MealType mealType = mealTypeRepository.findById(mealPlanItemDTO.getMealTypeId())
                    .orElseThrow(() -> new RuntimeException("MealType not found"));
            mealPlanItem.setMealType(mealType);
        }

        if (mealPlanItemDTO.getDate() != null) {
            mealPlanItem.setDate(mealPlanItemDTO.getDate());
        }

        MealPlanItem updatedItem = mealPlanItemRepository.save(mealPlanItem);
        return convertToDTO(updatedItem);
    }

    @Override
    @Transactional
    public void deleteMealPlanItem(Long id) {
        mealPlanItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMealPlanItemByDetails(Long mealPlanId, LocalDate date, Integer mealTypeId) {
        mealPlanItemRepository.deleteByMealPlanIdAndDateAndMealTypeId(mealPlanId, date, mealTypeId);
    }

    private MealPlanItemDTO convertToDTO(MealPlanItem item) {
        MealPlanItemDTO dto = new MealPlanItemDTO();
        dto.setId(item.getId());
        dto.setMealPlanId(item.getMealPlan().getId());
        dto.setRecipeId(item.getRecipe().getId());
        dto.setMealTypeId(item.getMealType().getId());
        dto.setDate(item.getDate());
        return dto;
    }
}

package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import com.MealMate.MealMateBackend.planner.model.MealPlanItem;
import com.MealMate.MealMateBackend.planner.repository.MealPlanItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanItemServiceImpl implements MealPlanItemService {

    @Autowired
    private MealPlanItemRepository mealPlanItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MealPlanItemDTO> getAllMealPlanItems() {
        return mealPlanItemRepository.findAll().stream()
                .map(mealPlanItem -> modelMapper.map(mealPlanItem, MealPlanItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MealPlanItemDTO getMealPlanItemById(Long id) {
        MealPlanItem mealPlanItem = mealPlanItemRepository.findById(id).orElseThrow(() -> new RuntimeException("MealPlanItem not found"));
        return modelMapper.map(mealPlanItem, MealPlanItemDTO.class);
    }

    @Override
    public MealPlanItemDTO createMealPlanItem(MealPlanItemCreateDTO mealPlanItemCreateDTO) {
        MealPlanItem mealPlanItem = modelMapper.map(mealPlanItemCreateDTO, MealPlanItem.class);
        MealPlanItem savedMealPlanItem = mealPlanItemRepository.save(mealPlanItem);
        return modelMapper.map(savedMealPlanItem, MealPlanItemDTO.class);
    }

    @Override
    public MealPlanItemDTO updateMealPlanItem(Long id, MealPlanItemDTO mealPlanItemDTO) {
        MealPlanItem mealPlanItem = mealPlanItemRepository.findById(id).orElseThrow(() -> new RuntimeException("MealPlanItem not found"));
        modelMapper.map(mealPlanItemDTO, mealPlanItem);
        MealPlanItem updatedMealPlanItem = mealPlanItemRepository.save(mealPlanItem);
        return modelMapper.map(updatedMealPlanItem, MealPlanItemDTO.class);
    }

    @Override
    public void deleteMealPlanItem(Long id) {
        mealPlanItemRepository.deleteById(id);
    }
}
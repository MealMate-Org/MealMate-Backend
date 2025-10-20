package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanCreateDTO;
import com.MealMate.MealMateBackend.planner.model.MealPlan;
import com.MealMate.MealMateBackend.planner.repository.MealPlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanServiceImpl implements MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MealPlanDTO> getAllMealPlans() {
        return mealPlanRepository.findAll().stream()
                .map(mealPlan -> modelMapper.map(mealPlan, MealPlanDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MealPlanDTO getMealPlanById(Long id) {
        MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("MealPlan not found"));
        return modelMapper.map(mealPlan, MealPlanDTO.class);
    }

    @Override
    public MealPlanDTO createMealPlan(MealPlanCreateDTO mealPlanCreateDTO) {
        MealPlan mealPlan = modelMapper.map(mealPlanCreateDTO, MealPlan.class);
        MealPlan savedMealPlan = mealPlanRepository.save(mealPlan);
        return modelMapper.map(savedMealPlan, MealPlanDTO.class);
    }

    @Override
    public MealPlanDTO updateMealPlan(Long id, MealPlanDTO mealPlanDTO) {
        MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("MealPlan not found"));
        modelMapper.map(mealPlanDTO, mealPlan);
        MealPlan updatedMealPlan = mealPlanRepository.save(mealPlan);
        return modelMapper.map(updatedMealPlan, MealPlanDTO.class);
    }

    @Override
    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
}
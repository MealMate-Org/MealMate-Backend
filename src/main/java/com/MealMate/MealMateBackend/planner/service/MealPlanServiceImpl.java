package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanCreateDTO;
import com.MealMate.MealMateBackend.planner.model.MealPlan;
import com.MealMate.MealMateBackend.planner.repository.MealPlanRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanServiceImpl implements MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MealPlanDTO> getAllMealPlans() {
        return mealPlanRepository.findAll().stream()
                .map(mealPlan -> modelMapper.map(mealPlan, MealPlanDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealPlanDTO> getMealPlansByUserId(Long userId) {
        return mealPlanRepository.findByUserId(userId).stream()
                .map(mealPlan -> modelMapper.map(mealPlan, MealPlanDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MealPlanDTO getMealPlanById(Long id) {
        MealPlan mealPlan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MealPlan not found"));
        return modelMapper.map(mealPlan, MealPlanDTO.class);
    }

    @Override
    public MealPlanDTO getActiveByUserIdAndDate(Long userId, LocalDate date) {
        MealPlan mealPlan = mealPlanRepository.findActiveByUserIdAndDate(userId, date)
                .orElseThrow(() -> new RuntimeException("No active MealPlan found for this date"));
        return modelMapper.map(mealPlan, MealPlanDTO.class);
    }

    @Override
    @Transactional
    public MealPlanDTO createMealPlan(MealPlanCreateDTO mealPlanCreateDTO) {
        User user = userRepository.findById(mealPlanCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var existingPlan = mealPlanRepository.findByUserIdAndDateRange(
            mealPlanCreateDTO.getUserId(),
            mealPlanCreateDTO.getStartDate(),
            mealPlanCreateDTO.getEndDate()
        );

        if (existingPlan.isPresent()) {
            return modelMapper.map(existingPlan.get(), MealPlanDTO.class);
        }

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setStartDate(mealPlanCreateDTO.getStartDate());
        mealPlan.setEndDate(mealPlanCreateDTO.getEndDate());
        mealPlan.setIsActive(true);

        MealPlan savedMealPlan = mealPlanRepository.save(mealPlan);
        return modelMapper.map(savedMealPlan, MealPlanDTO.class);
    }

    @Override
    @Transactional
    public MealPlanDTO getOrCreateMealPlanForWeek(Long userId, LocalDate weekStart, LocalDate weekEnd) {
        var existingPlan = mealPlanRepository.findByUserIdAndDateRange(userId, weekStart, weekEnd);
        
        if (existingPlan.isPresent()) {
            return modelMapper.map(existingPlan.get(), MealPlanDTO.class);
        }

        MealPlanCreateDTO createDTO = new MealPlanCreateDTO();
        createDTO.setUserId(userId);
        createDTO.setStartDate(weekStart);
        createDTO.setEndDate(weekEnd);

        return createMealPlan(createDTO);
    }

    @Override
    @Transactional
    public MealPlanDTO updateMealPlan(Long id, MealPlanDTO mealPlanDTO) {
        MealPlan mealPlan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MealPlan not found"));
        
        mealPlan.setStartDate(mealPlanDTO.getStartDate());
        mealPlan.setEndDate(mealPlanDTO.getEndDate());
        mealPlan.setIsActive(mealPlanDTO.getIsActive());

        MealPlan updatedMealPlan = mealPlanRepository.save(mealPlan);
        return modelMapper.map(updatedMealPlan, MealPlanDTO.class);
    }

    @Override
    @Transactional
    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
}

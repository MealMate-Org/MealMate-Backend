package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealTypeDTO;
import java.util.List;

public interface MealTypeService {
    List<MealTypeDTO> getAllMealTypes();
    MealTypeDTO getMealTypeById(Integer id);
    MealTypeDTO createMealType(MealTypeDTO mealTypeDTO);
    MealTypeDTO updateMealType(Integer id, MealTypeDTO mealTypeDTO);
    void deleteMealType(Integer id);
}
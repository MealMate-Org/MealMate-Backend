package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.DietDTO;
import java.util.List;

public interface DietService {
    List<DietDTO> getAllDiets();
    DietDTO getDietById(Integer id);
    DietDTO createDiet(DietDTO dietDTO);
    DietDTO updateDiet(Integer id, DietDTO dietDTO);
    void deleteDiet(Integer id);
}
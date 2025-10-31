package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import java.util.List;

public interface UserAllergenService {
    List<AllergenDTO> getUserAllergens(Long userId);
    void saveUserAllergens(Long userId, List<Integer> allergenIds);
    void deleteAllUserAllergens(Long userId);
}
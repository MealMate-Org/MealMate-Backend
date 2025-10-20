package com.MealMate.MealMateBackend.recipe.service;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import java.util.List;

public interface AllergenService {
    List<AllergenDTO> getAllAllergens();
    AllergenDTO getAllergenById(Integer id);
    AllergenDTO createAllergen(AllergenDTO allergenDTO);
    AllergenDTO updateAllergen(Integer id, AllergenDTO allergenDTO);
    void deleteAllergen(Integer id);
}
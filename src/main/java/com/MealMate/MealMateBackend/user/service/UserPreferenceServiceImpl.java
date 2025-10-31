package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.UserPreferenceDTO;
import com.MealMate.MealMateBackend.user.model.UserPreference;
import com.MealMate.MealMateBackend.user.model.Diet;
import com.MealMate.MealMateBackend.user.repository.UserPreferenceRepository;
import com.MealMate.MealMateBackend.user.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private DietRepository dietRepository;

    // ✅ CONSTANTES PARA VALIDACIÓN
    private static final int MAX_CALORIES = 999999; // 6 dígitos
    private static final BigDecimal MAX_MACROS = new BigDecimal("9999.99"); // precision 6, scale 2

    @Override
    public UserPreferenceDTO getUserPreferenceByUserId(Long userId) {
        UserPreference preference = userPreferenceRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Preference not found"));
        return convertToDTO(preference);
    }

    @Override
    public UserPreferenceDTO createOrUpdateUserPreference(UserPreferenceDTO userPreferenceDTO) {
        // ✅ VALIDAR VALORES ANTES DE GUARDAR
        validatePreferences(userPreferenceDTO);

        UserPreference preference = new UserPreference();
        preference.setUserId(userPreferenceDTO.getUserId());
        preference.setDailyCaloriesGoal(userPreferenceDTO.getDailyCaloriesGoal());
        preference.setDailyCarbsGoal(userPreferenceDTO.getDailyCarbsGoal());
        preference.setDailyProteinGoal(userPreferenceDTO.getDailyProteinGoal());
        preference.setDailyFatGoal(userPreferenceDTO.getDailyFatGoal());

        // Asignar dieta si existe
        if (userPreferenceDTO.getDietId() != null) {
            Diet diet = dietRepository.findById(userPreferenceDTO.getDietId())
                    .orElseThrow(() -> new RuntimeException("Diet not found"));
            preference.setDiet(diet);
        }

        UserPreference savedPreference = userPreferenceRepository.save(preference);
        return convertToDTO(savedPreference);
    }

    @Override
    public void deleteUserPreference(Long userId) {
        userPreferenceRepository.deleteById(userId);
    }

    // ✅ MÉTODO DE VALIDACIÓN CON MENSAJES CLAROS
    private void validatePreferences(UserPreferenceDTO dto) {
        // Validar calorías
        if (dto.getDailyCaloriesGoal() != null && dto.getDailyCaloriesGoal() > MAX_CALORIES) {
            throw new IllegalArgumentException(
                String.format("Las calorías diarias no pueden exceder %d. Valor ingresado: %d", 
                    MAX_CALORIES, dto.getDailyCaloriesGoal())
            );
        }

        // Validar carbohidratos
        if (dto.getDailyCarbsGoal() != null && dto.getDailyCarbsGoal().compareTo(MAX_MACROS) > 0) {
            throw new IllegalArgumentException(
                String.format("Los carbohidratos diarios no pueden exceder %.2f gramos. Valor ingresado: %.2f", 
                    MAX_MACROS, dto.getDailyCarbsGoal())
            );
        }

        // Validar proteínas
        if (dto.getDailyProteinGoal() != null && dto.getDailyProteinGoal().compareTo(MAX_MACROS) > 0) {
            throw new IllegalArgumentException(
                String.format("Las proteínas diarias no pueden exceder %.2f gramos. Valor ingresado: %.2f", 
                    MAX_MACROS, dto.getDailyProteinGoal())
            );
        }

        // Validar grasas
        if (dto.getDailyFatGoal() != null && dto.getDailyFatGoal().compareTo(MAX_MACROS) > 0) {
            throw new IllegalArgumentException(
                String.format("Las grasas diarias no pueden exceder %.2f gramos. Valor ingresado: %.2f", 
                    MAX_MACROS, dto.getDailyFatGoal())
            );
        }

        // Validar valores negativos
        if (dto.getDailyCaloriesGoal() != null && dto.getDailyCaloriesGoal() < 0) {
            throw new IllegalArgumentException("Las calorías no pueden ser negativas");
        }
        if (dto.getDailyCarbsGoal() != null && dto.getDailyCarbsGoal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los carbohidratos no pueden ser negativos");
        }
        if (dto.getDailyProteinGoal() != null && dto.getDailyProteinGoal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Las proteínas no pueden ser negativas");
        }
        if (dto.getDailyFatGoal() != null && dto.getDailyFatGoal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Las grasas no pueden ser negativas");
        }
    }

    // ✅ CONVERTIR A DTO MANUALMENTE
    private UserPreferenceDTO convertToDTO(UserPreference preference) {
        UserPreferenceDTO dto = new UserPreferenceDTO();
        dto.setUserId(preference.getUserId());
        dto.setDailyCaloriesGoal(preference.getDailyCaloriesGoal());
        dto.setDailyCarbsGoal(preference.getDailyCarbsGoal());
        dto.setDailyProteinGoal(preference.getDailyProteinGoal());
        dto.setDailyFatGoal(preference.getDailyFatGoal());
        dto.setDietId(preference.getDiet() != null ? preference.getDiet().getId() : null);
        return dto;
    }
}
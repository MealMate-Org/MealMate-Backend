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

    // CONSTANTES PARA VALIDACIÓN
    private static final int MAX_CALORIES = 999999;
    private static final BigDecimal MAX_MACROS = new BigDecimal("9999.99");

    @Override
    public UserPreferenceDTO getUserPreferenceByUserId(Long userId) {
        UserPreference preference = userPreferenceRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Preference not found"));
        return convertToDTO(preference);
    }

    @Override
    public UserPreferenceDTO createOrUpdateUserPreference(UserPreferenceDTO userPreferenceDTO) {
        // Validar valores antes de guardar
        validatePreferences(userPreferenceDTO);

        UserPreference preference = new UserPreference();
        preference.setUserId(userPreferenceDTO.getUserId());
        
        // Campos de cálculo automático
        preference.setUseAutomaticCalculation(userPreferenceDTO.getUseAutomaticCalculation());
        preference.setGender(userPreferenceDTO.getGender());
        preference.setAge(userPreferenceDTO.getAge());
        preference.setWeight(userPreferenceDTO.getWeight());
        preference.setHeight(userPreferenceDTO.getHeight());
        preference.setActivityLevel(userPreferenceDTO.getActivityLevel());
        preference.setGoal(userPreferenceDTO.getGoal());
        
        // Campos manuales
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

    // MÉTODO DE VALIDACIÓN CON MENSAJES CLAROS
    private void validatePreferences(UserPreferenceDTO dto) {
        // Si usa cálculo automático, validar campos requeridos
        if (Boolean.TRUE.equals(dto.getUseAutomaticCalculation())) {
            if (dto.getGender() == null || dto.getGender().isEmpty()) {
                throw new IllegalArgumentException("El género es obligatorio para el cálculo automático");
            }
            if (dto.getAge() == null) {
                throw new IllegalArgumentException("La edad es obligatoria para el cálculo automático");
            }
            if (dto.getWeight() == null) {
                throw new IllegalArgumentException("El peso es obligatorio para el cálculo automático");
            }
            if (dto.getHeight() == null) {
                throw new IllegalArgumentException("La altura es obligatoria para el cálculo automático");
            }
            if (dto.getActivityLevel() == null || dto.getActivityLevel().isEmpty()) {
                throw new IllegalArgumentException("El nivel de actividad es obligatorio para el cálculo automático");
            }
            if (dto.getGoal() == null || dto.getGoal().isEmpty()) {
                throw new IllegalArgumentException("El objetivo es obligatorio para el cálculo automático");
            }
            
            // Validar rangos
            if (dto.getAge() < 15 || dto.getAge() > 100) {
                throw new IllegalArgumentException("La edad debe estar entre 15 y 100 años");
            }
            if (dto.getWeight().compareTo(new BigDecimal("30")) < 0 || dto.getWeight().compareTo(new BigDecimal("300")) > 0) {
                throw new IllegalArgumentException("El peso debe estar entre 30 y 300 kg");
            }
            if (dto.getHeight().compareTo(new BigDecimal("100")) < 0 || dto.getHeight().compareTo(new BigDecimal("250")) > 0) {
                throw new IllegalArgumentException("La altura debe estar entre 100 y 250 cm");
            }
        } else {
            // Si es manual, validar límites
            if (dto.getDailyCaloriesGoal() != null && dto.getDailyCaloriesGoal() > MAX_CALORIES) {
                throw new IllegalArgumentException(
                    String.format("Las calorías diarias no pueden exceder %d. Valor ingresado: %d", 
                        MAX_CALORIES, dto.getDailyCaloriesGoal())
                );
            }

            if (dto.getDailyCarbsGoal() != null && dto.getDailyCarbsGoal().compareTo(MAX_MACROS) > 0) {
                throw new IllegalArgumentException(
                    String.format("Los carbohidratos diarios no pueden exceder %.2f gramos. Valor ingresado: %.2f", 
                        MAX_MACROS, dto.getDailyCarbsGoal())
                );
            }

            if (dto.getDailyProteinGoal() != null && dto.getDailyProteinGoal().compareTo(MAX_MACROS) > 0) {
                throw new IllegalArgumentException(
                    String.format("Las proteínas diarias no pueden exceder %.2f gramos. Valor ingresado: %.2f", 
                        MAX_MACROS, dto.getDailyProteinGoal())
                );
            }

            if (dto.getDailyFatGoal() != null && dto.getDailyFatGoal().compareTo(MAX_MACROS) > 0) {
                throw new IllegalArgumentException(
                    String.format("Las grasas diarias no pueden exceder %.2f gramos. Valor ingresado: %.2f", 
                        MAX_MACROS, dto.getDailyFatGoal())
                );
            }
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

    // CONVERTIR A DTO MANUALMENTE
    private UserPreferenceDTO convertToDTO(UserPreference preference) {
        UserPreferenceDTO dto = new UserPreferenceDTO();
        dto.setUserId(preference.getUserId());
        
        // Campos de cálculo automático
        dto.setUseAutomaticCalculation(preference.getUseAutomaticCalculation());
        dto.setGender(preference.getGender());
        dto.setAge(preference.getAge());
        dto.setWeight(preference.getWeight());
        dto.setHeight(preference.getHeight());
        dto.setActivityLevel(preference.getActivityLevel());
        dto.setGoal(preference.getGoal());
        
        // Campos manuales
        dto.setDailyCaloriesGoal(preference.getDailyCaloriesGoal());
        dto.setDailyCarbsGoal(preference.getDailyCarbsGoal());
        dto.setDailyProteinGoal(preference.getDailyProteinGoal());
        dto.setDailyFatGoal(preference.getDailyFatGoal());
        dto.setDietId(preference.getDiet() != null ? preference.getDiet().getId() : null);
        
        return dto;
    }
}

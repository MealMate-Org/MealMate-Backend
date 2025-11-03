package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.UserPreferenceDTO;
import com.MealMate.MealMateBackend.user.model.Diet;
import com.MealMate.MealMateBackend.user.model.UserPreference;
import com.MealMate.MealMateBackend.user.repository.DietRepository;
import com.MealMate.MealMateBackend.user.repository.UserPreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPreferenceServiceImplTest {

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private DietRepository dietRepository;

    @InjectMocks
    private UserPreferenceServiceImpl userPreferenceService;

    private UserPreference userPreference;
    private UserPreferenceDTO userPreferenceDTO;
    private Diet diet;

    @BeforeEach
    void setUp() {
        diet = new Diet();
        diet.setId(1);
        diet.setName("Balanced");

        userPreference = new UserPreference();
        userPreference.setUserId(1L);
        userPreference.setDailyCaloriesGoal(2000);
        userPreference.setDailyCarbsGoal(new BigDecimal("250.00"));
        userPreference.setDailyProteinGoal(new BigDecimal("150.00"));
        userPreference.setDailyFatGoal(new BigDecimal("70.00"));
        userPreference.setDiet(diet);
        userPreference.setUseAutomaticCalculation(false);

        userPreferenceDTO = new UserPreferenceDTO();
        userPreferenceDTO.setUserId(1L);
        userPreferenceDTO.setDailyCaloriesGoal(2000);
        userPreferenceDTO.setDailyCarbsGoal(new BigDecimal("250.00"));
        userPreferenceDTO.setDailyProteinGoal(new BigDecimal("150.00"));
        userPreferenceDTO.setDailyFatGoal(new BigDecimal("70.00"));
        userPreferenceDTO.setDietId(1);
        userPreferenceDTO.setUseAutomaticCalculation(false);
    }

    @Test
    void getUserPreferenceByUserId_ShouldReturnPreference_WhenExists() {
        // Arrange
        when(userPreferenceRepository.findById(1L)).thenReturn(Optional.of(userPreference));

        // Act
        UserPreferenceDTO result = userPreferenceService.getUserPreferenceByUserId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getDailyCaloriesGoal()).isEqualTo(2000);
        verify(userPreferenceRepository, times(1)).findById(1L);
    }

    @Test
    void getUserPreferenceByUserId_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(userPreferenceRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.getUserPreferenceByUserId(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Preference not found");
    }

    @Test
    void createOrUpdateUserPreference_ShouldCreatePreference_WithManualCalculation() {
        // Arrange
        when(dietRepository.findById(1)).thenReturn(Optional.of(diet));
        when(userPreferenceRepository.save(any(UserPreference.class))).thenReturn(userPreference);

        // Act
        UserPreferenceDTO result = userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDailyCaloriesGoal()).isEqualTo(2000);
        verify(userPreferenceRepository, times(1)).save(any(UserPreference.class));
    }

    @Test
    void createOrUpdateUserPreference_ShouldCreatePreference_WithAutomaticCalculation() {
        // Arrange
        userPreferenceDTO.setUseAutomaticCalculation(true);
        userPreferenceDTO.setGender("male");
        userPreferenceDTO.setAge(30);
        userPreferenceDTO.setWeight(new BigDecimal("75.00"));
        userPreferenceDTO.setHeight(new BigDecimal("175.00"));
        userPreferenceDTO.setActivityLevel("moderate");
        userPreferenceDTO.setGoal("maintain");

        when(dietRepository.findById(1)).thenReturn(Optional.of(diet));
        when(userPreferenceRepository.save(any(UserPreference.class))).thenReturn(userPreference);

        // Act
        UserPreferenceDTO result = userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(userPreferenceRepository, times(1)).save(any(UserPreference.class));
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenCaloriesExceedMax() {
        // Arrange
        userPreferenceDTO.setDailyCaloriesGoal(1000000);

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Las calorías diarias no pueden exceder");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenCarbsExceedMax() {
        // Arrange
        userPreferenceDTO.setDailyCarbsGoal(new BigDecimal("10000.00"));

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Los carbohidratos diarios no pueden exceder");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenProteinExceedsMax() {
        // Arrange
        userPreferenceDTO.setDailyProteinGoal(new BigDecimal("10000.00"));

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Las proteínas diarias no pueden exceder");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenFatExceedsMax() {
        // Arrange
        userPreferenceDTO.setDailyFatGoal(new BigDecimal("10000.00"));

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Las grasas diarias no pueden exceder");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenCaloriesNegative() {
        // Arrange
        userPreferenceDTO.setDailyCaloriesGoal(-100);

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Las calorías no pueden ser negativas");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenAutomaticCalculationMissingGender() {
        // Arrange
        userPreferenceDTO.setUseAutomaticCalculation(true);
        userPreferenceDTO.setAge(30);
        userPreferenceDTO.setWeight(new BigDecimal("75.00"));
        userPreferenceDTO.setHeight(new BigDecimal("175.00"));

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El género es obligatorio");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenAutomaticCalculationMissingAge() {
        // Arrange
        userPreferenceDTO.setUseAutomaticCalculation(true);
        userPreferenceDTO.setGender("male");
        userPreferenceDTO.setWeight(new BigDecimal("75.00"));
        userPreferenceDTO.setHeight(new BigDecimal("175.00"));

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La edad es obligatoria");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenAgeOutOfRange() {
        // Arrange
        userPreferenceDTO.setUseAutomaticCalculation(true);
        userPreferenceDTO.setGender("male");
        userPreferenceDTO.setAge(10);
        userPreferenceDTO.setWeight(new BigDecimal("75.00"));
        userPreferenceDTO.setHeight(new BigDecimal("175.00"));
        userPreferenceDTO.setActivityLevel("moderate");
        userPreferenceDTO.setGoal("maintain");

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La edad debe estar entre 15 y 100 años");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenWeightOutOfRange() {
        // Arrange
        userPreferenceDTO.setUseAutomaticCalculation(true);
        userPreferenceDTO.setGender("male");
        userPreferenceDTO.setAge(30);
        userPreferenceDTO.setWeight(new BigDecimal("25.00"));
        userPreferenceDTO.setHeight(new BigDecimal("175.00"));
        userPreferenceDTO.setActivityLevel("moderate");
        userPreferenceDTO.setGoal("maintain");

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El peso debe estar entre 30 y 300 kg");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenHeightOutOfRange() {
        // Arrange
        userPreferenceDTO.setUseAutomaticCalculation(true);
        userPreferenceDTO.setGender("male");
        userPreferenceDTO.setAge(30);
        userPreferenceDTO.setWeight(new BigDecimal("75.00"));
        userPreferenceDTO.setHeight(new BigDecimal("90.00"));
        userPreferenceDTO.setActivityLevel("moderate");
        userPreferenceDTO.setGoal("maintain");

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La altura debe estar entre 100 y 250 cm");
    }

    @Test
    void createOrUpdateUserPreference_ShouldThrowException_WhenDietNotFound() {
        // Arrange
        userPreferenceDTO.setDietId(999);
        when(dietRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Diet not found");
    }

    @Test
    void deleteUserPreference_ShouldDeletePreference() {
        // Arrange
        doNothing().when(userPreferenceRepository).deleteById(1L);

        // Act
        userPreferenceService.deleteUserPreference(1L);

        // Assert
        verify(userPreferenceRepository, times(1)).deleteById(1L);
    }

    @Test
    void createOrUpdateUserPreference_ShouldHandleNullDiet() {
        // Arrange
        userPreferenceDTO.setDietId(null);
        when(userPreferenceRepository.save(any(UserPreference.class))).thenReturn(userPreference);

        // Act
        UserPreferenceDTO result = userPreferenceService.createOrUpdateUserPreference(userPreferenceDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(dietRepository, never()).findById(any());
        verify(userPreferenceRepository, times(1)).save(any(UserPreference.class));
    }
}

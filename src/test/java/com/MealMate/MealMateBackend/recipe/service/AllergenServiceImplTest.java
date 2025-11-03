package com.MealMate.MealMateBackend.recipe.service;

import com.MealMate.MealMateBackend.recipe.dto.AllergenDTO;
import com.MealMate.MealMateBackend.recipe.model.Allergen;
import com.MealMate.MealMateBackend.recipe.repository.AllergenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllergenServiceImplTest {

    @Mock
    private AllergenRepository allergenRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AllergenServiceImpl allergenService;

    private Allergen allergen;
    private AllergenDTO allergenDTO;

    @BeforeEach
    void setUp() {
        allergen = new Allergen();
        allergen.setId(1);
        allergen.setName("Gluten");

        allergenDTO = new AllergenDTO();
        allergenDTO.setId(1);
        allergenDTO.setName("Gluten");
    }

    @Test
    void getAllAllergens_ShouldReturnAllAllergens() {
        // Arrange
        Allergen allergen2 = new Allergen();
        allergen2.setId(2);
        allergen2.setName("Lactose");

        AllergenDTO allergenDTO2 = new AllergenDTO();
        allergenDTO2.setId(2);
        allergenDTO2.setName("Lactose");

        when(allergenRepository.findAll()).thenReturn(Arrays.asList(allergen, allergen2));
        when(modelMapper.map(allergen, AllergenDTO.class)).thenReturn(allergenDTO);
        when(modelMapper.map(allergen2, AllergenDTO.class)).thenReturn(allergenDTO2);

        // Act
        List<AllergenDTO> result = allergenService.getAllAllergens();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Gluten");
        assertThat(result.get(1).getName()).isEqualTo("Lactose");
        verify(allergenRepository, times(1)).findAll();
    }

    @Test
    void getAllergenById_ShouldReturnAllergen_WhenExists() {
        // Arrange
        when(allergenRepository.findById(1)).thenReturn(Optional.of(allergen));
        when(modelMapper.map(allergen, AllergenDTO.class)).thenReturn(allergenDTO);

        // Act
        AllergenDTO result = allergenService.getAllergenById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Gluten");
        verify(allergenRepository, times(1)).findById(1);
    }

    @Test
    void getAllergenById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(allergenRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> allergenService.getAllergenById(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Allergen not found");

        verify(allergenRepository, times(1)).findById(999);
    }

    @Test
    void createAllergen_ShouldCreateAllergen() {
        // Arrange
        when(modelMapper.map(allergenDTO, Allergen.class)).thenReturn(allergen);
        when(allergenRepository.save(any(Allergen.class))).thenReturn(allergen);
        when(modelMapper.map(allergen, AllergenDTO.class)).thenReturn(allergenDTO);

        // Act
        AllergenDTO result = allergenService.createAllergen(allergenDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Gluten");
        verify(allergenRepository, times(1)).save(any(Allergen.class));
    }

    @Test
    void updateAllergen_ShouldUpdateAllergen_WhenExists() {
        // Arrange
        when(allergenRepository.findById(1)).thenReturn(Optional.of(allergen));
        when(allergenRepository.save(any(Allergen.class))).thenReturn(allergen);
        when(modelMapper.map(allergen, AllergenDTO.class)).thenReturn(allergenDTO);
        doNothing().when(modelMapper).map(allergenDTO, allergen);

        allergenDTO.setName("Updated Gluten");

        // Act
        AllergenDTO result = allergenService.updateAllergen(1, allergenDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(allergenRepository, times(1)).save(any(Allergen.class));
    }

    @Test
    void updateAllergen_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(allergenRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> allergenService.updateAllergen(1, allergenDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Allergen not found");

        verify(allergenRepository, times(1)).findById(1);
        verify(allergenRepository, never()).save(any(Allergen.class));
    }

    @Test
    void deleteAllergen_ShouldDeleteAllergen() {
        // Arrange
        doNothing().when(allergenRepository).deleteById(1);

        // Act
        allergenService.deleteAllergen(1);

        // Assert
        verify(allergenRepository, times(1)).deleteById(1);
    }

    @Test
    void getAllAllergens_ShouldReturnEmptyList_WhenNoAllergensExist() {
        // Arrange
        when(allergenRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<AllergenDTO> result = allergenService.getAllAllergens();

        // Assert
        assertThat(result).isEmpty();
        verify(allergenRepository, times(1)).findAll();
    }
}

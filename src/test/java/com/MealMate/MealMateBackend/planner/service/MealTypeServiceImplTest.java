package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealTypeDTO;
import com.MealMate.MealMateBackend.planner.model.MealType;
import com.MealMate.MealMateBackend.planner.repository.MealTypeRepository;
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
class MealTypeServiceImplTest {

    @Mock
    private MealTypeRepository mealTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MealTypeServiceImpl mealTypeService;

    private MealType mealType;
    private MealTypeDTO mealTypeDTO;

    @BeforeEach
    void setUp() {
        mealType = new MealType();
        mealType.setId(1);
        mealType.setName("Breakfast");

        mealTypeDTO = new MealTypeDTO();
        mealTypeDTO.setId(1);
        mealTypeDTO.setName("Breakfast");
    }

    @Test
    void getAllMealTypes_ShouldReturnAllMealTypes() {
        // Arrange
        MealType lunch = new MealType();
        lunch.setId(2);
        lunch.setName("Lunch");

        MealTypeDTO lunchDTO = new MealTypeDTO();
        lunchDTO.setId(2);
        lunchDTO.setName("Lunch");

        when(mealTypeRepository.findAll()).thenReturn(Arrays.asList(mealType, lunch));
        when(modelMapper.map(mealType, MealTypeDTO.class)).thenReturn(mealTypeDTO);
        when(modelMapper.map(lunch, MealTypeDTO.class)).thenReturn(lunchDTO);

        // Act
        List<MealTypeDTO> result = mealTypeService.getAllMealTypes();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Breakfast");
        assertThat(result.get(1).getName()).isEqualTo("Lunch");
        verify(mealTypeRepository, times(1)).findAll();
    }

    @Test
    void getMealTypeById_ShouldReturnMealType_WhenExists() {
        // Arrange
        when(mealTypeRepository.findById(1)).thenReturn(Optional.of(mealType));
        when(modelMapper.map(mealType, MealTypeDTO.class)).thenReturn(mealTypeDTO);

        // Act
        MealTypeDTO result = mealTypeService.getMealTypeById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Breakfast");
        verify(mealTypeRepository, times(1)).findById(1);
    }

    @Test
    void getMealTypeById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(mealTypeRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealTypeService.getMealTypeById(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealType not found");

        verify(mealTypeRepository, times(1)).findById(999);
    }

    @Test
    void createMealType_ShouldCreateMealType() {
        // Arrange
        when(modelMapper.map(mealTypeDTO, MealType.class)).thenReturn(mealType);
        when(mealTypeRepository.save(any(MealType.class))).thenReturn(mealType);
        when(modelMapper.map(mealType, MealTypeDTO.class)).thenReturn(mealTypeDTO);

        // Act
        MealTypeDTO result = mealTypeService.createMealType(mealTypeDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Breakfast");
        verify(mealTypeRepository, times(1)).save(any(MealType.class));
    }

    @Test
    void updateMealType_ShouldUpdateMealType_WhenExists() {
        // Arrange
        when(mealTypeRepository.findById(1)).thenReturn(Optional.of(mealType));
        when(mealTypeRepository.save(any(MealType.class))).thenReturn(mealType);
        when(modelMapper.map(mealType, MealTypeDTO.class)).thenReturn(mealTypeDTO);
        doNothing().when(modelMapper).map(mealTypeDTO, mealType);

        mealTypeDTO.setName("Updated Breakfast");

        // Act
        MealTypeDTO result = mealTypeService.updateMealType(1, mealTypeDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(mealTypeRepository, times(1)).save(any(MealType.class));
    }

    @Test
    void updateMealType_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(mealTypeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealTypeService.updateMealType(1, mealTypeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealType not found");

        verify(mealTypeRepository, times(1)).findById(1);
        verify(mealTypeRepository, never()).save(any(MealType.class));
    }

    @Test
    void deleteMealType_ShouldDeleteMealType() {
        // Arrange
        doNothing().when(mealTypeRepository).deleteById(1);

        // Act
        mealTypeService.deleteMealType(1);

        // Assert
        verify(mealTypeRepository, times(1)).deleteById(1);
    }

    @Test
    void getAllMealTypes_ShouldReturnEmptyList_WhenNoMealTypesExist() {
        // Arrange
        when(mealTypeRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<MealTypeDTO> result = mealTypeService.getAllMealTypes();

        // Assert
        assertThat(result).isEmpty();
        verify(mealTypeRepository, times(1)).findAll();
    }

    @Test
    void getAllMealTypes_ShouldReturnAllTypicalMealTypes() {
        // Arrange
        MealType dinner = new MealType();
        dinner.setId(3);
        dinner.setName("Dinner");

        MealType snack = new MealType();
        snack.setId(4);
        snack.setName("Snack");

        MealTypeDTO dinnerDTO = new MealTypeDTO();
        dinnerDTO.setId(3);
        dinnerDTO.setName("Dinner");

        MealTypeDTO snackDTO = new MealTypeDTO();
        snackDTO.setId(4);
        snackDTO.setName("Snack");

        when(mealTypeRepository.findAll()).thenReturn(Arrays.asList(mealType, dinner, snack));
        when(modelMapper.map(mealType, MealTypeDTO.class)).thenReturn(mealTypeDTO);
        when(modelMapper.map(dinner, MealTypeDTO.class)).thenReturn(dinnerDTO);
        when(modelMapper.map(snack, MealTypeDTO.class)).thenReturn(snackDTO);

        // Act
        List<MealTypeDTO> result = mealTypeService.getAllMealTypes();

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result).extracting(MealTypeDTO::getName)
                .containsExactly("Breakfast", "Dinner", "Snack");
    }
}

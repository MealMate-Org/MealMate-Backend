package com.MealMate.MealMateBackend.nutrition.service;

import com.MealMate.MealMateBackend.nutrition.dto.NutritionInfoDTO;
import com.MealMate.MealMateBackend.nutrition.model.NutritionInfo;
import com.MealMate.MealMateBackend.nutrition.repository.NutritionInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NutritionInfoServiceImplTest {

    @Mock
    private NutritionInfoRepository nutritionInfoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private NutritionInfoServiceImpl nutritionInfoService;

    private NutritionInfo nutritionInfo;
    private NutritionInfoDTO nutritionInfoDTO;

    @BeforeEach
    void setUp() {
        nutritionInfo = new NutritionInfo();
        nutritionInfo.setRecipeId(1L);
        nutritionInfo.setCalories(new BigDecimal("500.00"));
        nutritionInfo.setProtein(new BigDecimal("30.00"));
        nutritionInfo.setCarbs(new BigDecimal("50.00"));
        nutritionInfo.setFat(new BigDecimal("20.00"));
        nutritionInfo.setPortionSize(new BigDecimal("200.00"));

        nutritionInfoDTO = new NutritionInfoDTO();
        nutritionInfoDTO.setRecipeId(1L);
        nutritionInfoDTO.setCalories(new BigDecimal("500.00"));
        nutritionInfoDTO.setProtein(new BigDecimal("30.00"));
        nutritionInfoDTO.setCarbs(new BigDecimal("50.00"));
        nutritionInfoDTO.setFat(new BigDecimal("20.00"));
        nutritionInfoDTO.setPortionSize(new BigDecimal("200.00"));
    }

    @Test
    void getNutritionInfoByRecipeId_ShouldReturnNutritionInfo_WhenExists() {
        // Arrange
        when(nutritionInfoRepository.findById(1L)).thenReturn(Optional.of(nutritionInfo));
        when(modelMapper.map(nutritionInfo, NutritionInfoDTO.class)).thenReturn(nutritionInfoDTO);

        // Act
        NutritionInfoDTO result = nutritionInfoService.getNutritionInfoByRecipeId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRecipeId()).isEqualTo(1L);
        assertThat(result.getCalories()).isEqualByComparingTo(new BigDecimal("500.00"));
        verify(nutritionInfoRepository, times(1)).findById(1L);
    }

    @Test
    void getNutritionInfoByRecipeId_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(nutritionInfoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> nutritionInfoService.getNutritionInfoByRecipeId(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("NutritionInfo not found");
        
        verify(nutritionInfoRepository, times(1)).findById(1L);
    }

    @Test
    void createOrUpdateNutritionInfo_ShouldCreateNutritionInfo() {
        // Arrange
        when(modelMapper.map(nutritionInfoDTO, NutritionInfo.class)).thenReturn(nutritionInfo);
        when(nutritionInfoRepository.save(any(NutritionInfo.class))).thenReturn(nutritionInfo);
        when(modelMapper.map(nutritionInfo, NutritionInfoDTO.class)).thenReturn(nutritionInfoDTO);

        // Act
        NutritionInfoDTO result = nutritionInfoService.createOrUpdateNutritionInfo(nutritionInfoDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRecipeId()).isEqualTo(1L);
        verify(nutritionInfoRepository, times(1)).save(any(NutritionInfo.class));
    }

    @Test
    void deleteNutritionInfo_ShouldDeleteNutritionInfo() {
        // Arrange
        doNothing().when(nutritionInfoRepository).deleteById(1L);

        // Act
        nutritionInfoService.deleteNutritionInfo(1L);

        // Assert
        verify(nutritionInfoRepository, times(1)).deleteById(1L);
    }

    @Test
    void createOrUpdateNutritionInfo_ShouldHandleNullValues() {
        // Arrange
        NutritionInfoDTO dtoWithNulls = new NutritionInfoDTO();
        dtoWithNulls.setRecipeId(1L);
        
        NutritionInfo infoWithNulls = new NutritionInfo();
        infoWithNulls.setRecipeId(1L);

        when(modelMapper.map(dtoWithNulls, NutritionInfo.class)).thenReturn(infoWithNulls);
        when(nutritionInfoRepository.save(any(NutritionInfo.class))).thenReturn(infoWithNulls);
        when(modelMapper.map(infoWithNulls, NutritionInfoDTO.class)).thenReturn(dtoWithNulls);

        // Act
        NutritionInfoDTO result = nutritionInfoService.createOrUpdateNutritionInfo(dtoWithNulls);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRecipeId()).isEqualTo(1L);
        verify(nutritionInfoRepository, times(1)).save(any(NutritionInfo.class));
    }
}

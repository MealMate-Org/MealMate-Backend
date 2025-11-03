package com.MealMate.MealMateBackend.nutrition.controller;

import com.MealMate.MealMateBackend.nutrition.dto.NutritionInfoDTO;
import com.MealMate.MealMateBackend.nutrition.service.NutritionInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NutritionInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NutritionInfoService nutritionInfoService;

    private NutritionInfoDTO nutritionInfoDTO;

    @BeforeEach
    void setUp() {
        nutritionInfoDTO = new NutritionInfoDTO();
        nutritionInfoDTO.setRecipeId(1L);
        nutritionInfoDTO.setCalories(new BigDecimal("500.00"));
        nutritionInfoDTO.setProtein(new BigDecimal("30.00"));
        nutritionInfoDTO.setCarbs(new BigDecimal("50.00"));
        nutritionInfoDTO.setFat(new BigDecimal("20.00"));
        nutritionInfoDTO.setPortionSize(new BigDecimal("200.00"));
    }

    @Test
    @WithMockUser
    void getNutritionInfoByRecipeId_ShouldReturnNutritionInfo() throws Exception {
        // Arrange
        when(nutritionInfoService.getNutritionInfoByRecipeId(1L)).thenReturn(nutritionInfoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/v1/nutrition-info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(1))
                .andExpect(jsonPath("$.calories").value(500.00))
                .andExpect(jsonPath("$.protein").value(30.00));

        verify(nutritionInfoService, times(1)).getNutritionInfoByRecipeId(1L);
    }

    @Test
    @WithMockUser
    void createOrUpdateNutritionInfo_ShouldCreateNutritionInfo() throws Exception {
        // Arrange
        when(nutritionInfoService.createOrUpdateNutritionInfo(any(NutritionInfoDTO.class)))
                .thenReturn(nutritionInfoDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/nutrition-info")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nutritionInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(1))
                .andExpect(jsonPath("$.calories").value(500.00));

        verify(nutritionInfoService, times(1)).createOrUpdateNutritionInfo(any(NutritionInfoDTO.class));
    }

    @Test
    @WithMockUser
    void deleteNutritionInfo_ShouldDeleteNutritionInfo() throws Exception {
        // Arrange
        doNothing().when(nutritionInfoService).deleteNutritionInfo(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/nutrition-info/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(nutritionInfoService, times(1)).deleteNutritionInfo(1L);
    }

    @Test
    @WithMockUser
    void getNutritionInfoByRecipeId_ShouldReturn500_WhenNotFound() throws Exception {
        // Arrange
        when(nutritionInfoService.getNutritionInfoByRecipeId(999L))
                .thenThrow(new RuntimeException("NutritionInfo not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/nutrition-info/999"))
                .andExpect(status().is5xxServerError());

        verify(nutritionInfoService, times(1)).getNutritionInfoByRecipeId(999L);
    }
}
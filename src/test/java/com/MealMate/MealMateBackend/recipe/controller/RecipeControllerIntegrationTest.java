package com.MealMate.MealMateBackend.recipe.controller;

import com.MealMate.MealMateBackend.recipe.dto.RecipeCreateDTO;
import com.MealMate.MealMateBackend.recipe.dto.RecipeDTO;
import com.MealMate.MealMateBackend.recipe.service.RecipeService;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private UserRepository userRepository;

    private RecipeDTO recipeDTO;
    private RecipeCreateDTO recipeCreateDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setTitle("Test Recipe");
        recipeDTO.setDescription("Test Description");
        recipeDTO.setInstructions("Test Instructions");
        recipeDTO.setAuthorId(1L);
        recipeDTO.setIsPublic(true);
        recipeDTO.setCreatedAt(LocalDateTime.now());
        recipeDTO.setAvgRating(0.0);
        recipeDTO.setRatingCount(0);

        recipeCreateDTO = new RecipeCreateDTO();
        recipeCreateDTO.setTitle("Test Recipe");
        recipeCreateDTO.setDescription("Test Description");
        recipeCreateDTO.setInstructions("Test Instructions");
        recipeCreateDTO.setAuthorId(1L);
        recipeCreateDTO.setIsPublic(true);
    }

    @Test
    @WithMockUser
    void getAllRecipes_ShouldReturnAllRecipes_WhenNoFilters() throws Exception {
        // Arrange
        List<RecipeDTO> recipes = Arrays.asList(recipeDTO);
        when(recipeService.getPublicRecipes()).thenReturn(recipes);

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Recipe"));

        verify(recipeService, times(1)).getPublicRecipes();
    }

    @Test
    @WithMockUser
    void getAllRecipes_ShouldReturnPublicRecipes_WhenIsPublicTrue() throws Exception {
        // Arrange
        List<RecipeDTO> recipes = Arrays.asList(recipeDTO);
        when(recipeService.getPublicRecipes()).thenReturn(recipes);

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipes?isPublic=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isPublic").value(true));

        verify(recipeService, times(1)).getPublicRecipes();
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void getAllRecipes_ShouldReturnAuthorRecipes_WhenAuthorIdProvided() throws Exception {
        // Arrange
        List<RecipeDTO> recipes = Arrays.asList(recipeDTO);
        when(recipeService.getRecipesByAuthor(1L)).thenReturn(recipes);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipes?authorId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].authorId").value(1));

        verify(recipeService, times(1)).getRecipesByAuthor(1L);
    }

    @Test
    @WithMockUser
    void getRecipeById_ShouldReturnRecipe_WhenExists() throws Exception {
        // Arrange
        when(recipeService.getRecipeById(1L)).thenReturn(recipeDTO);

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Recipe"));

        verify(recipeService, times(1)).getRecipeById(1L);
    }

    @Test
    @WithMockUser
    void createRecipe_ShouldCreateRecipe() throws Exception {
        // Arrange
        when(recipeService.createRecipe(any(RecipeCreateDTO.class))).thenReturn(recipeDTO);

        // Act & Assert
        mockMvc.perform(post("/api/v1/recipes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Recipe"));

        verify(recipeService, times(1)).createRecipe(any(RecipeCreateDTO.class));
    }

    @Test
    @WithMockUser
    void updateRecipe_ShouldUpdateRecipe() throws Exception {
        // Arrange
        when(recipeService.updateRecipe(eq(1L), any(RecipeDTO.class))).thenReturn(recipeDTO);

        // Act & Assert
        mockMvc.perform(put("/api/v1/recipes/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(recipeService, times(1)).updateRecipe(eq(1L), any(RecipeDTO.class));
    }

    @Test
    @WithMockUser
    void deleteRecipe_ShouldDeleteRecipe() throws Exception {
        // Arrange
        doNothing().when(recipeService).deleteRecipe(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/recipes/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(recipeService, times(1)).deleteRecipe(1L);
    }

    @Test
    @WithMockUser
    void getRecipeById_ShouldReturn500_WhenNotFound() throws Exception {
        // Arrange
        when(recipeService.getRecipeById(999L)).thenThrow(new RuntimeException("Recipe not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipes/999"))
                .andExpect(status().is5xxServerError());
    }
}
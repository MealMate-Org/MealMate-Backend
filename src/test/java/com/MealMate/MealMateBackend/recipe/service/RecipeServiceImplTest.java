package com.MealMate.MealMateBackend.recipe.service;

import com.MealMate.MealMateBackend.recipe.dto.IngredientItem;
import com.MealMate.MealMateBackend.recipe.dto.RecipeCreateDTO;
import com.MealMate.MealMateBackend.recipe.dto.RecipeDTO;
import com.MealMate.MealMateBackend.recipe.model.Allergen;
import com.MealMate.MealMateBackend.recipe.model.Recipe;
import com.MealMate.MealMateBackend.recipe.repository.AllergenRepository;
import com.MealMate.MealMateBackend.recipe.repository.RecipeRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AllergenRepository allergenRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe recipe;
    private RecipeDTO recipeDTO;
    private RecipeCreateDTO recipeCreateDTO;
    private User user;
    private Allergen allergen;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        allergen = new Allergen();
        allergen.setId(1);
        allergen.setName("Gluten");

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test Description");
        recipe.setInstructions("Test Instructions");
        recipe.setAuthor(user);
        recipe.setIsPublic(true);
        recipe.setAvgRating(BigDecimal.ZERO);
        recipe.setRatingCount(0);
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setAllergens(Arrays.asList(allergen));
        recipe.setMealTypeId(1);

        recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setTitle("Test Recipe");
        recipeDTO.setDescription("Test Description");
        recipeDTO.setInstructions("Test Instructions");
        recipeDTO.setAuthorId(1L);
        recipeDTO.setIsPublic(true);
        recipeDTO.setAvgRating(0.0);
        recipeDTO.setRatingCount(0);
        recipeDTO.setAllergens(Arrays.asList(allergen));
        recipeDTO.setMealTypeId(1);

        recipeCreateDTO = new RecipeCreateDTO();
        recipeCreateDTO.setTitle("Test Recipe");
        recipeCreateDTO.setDescription("Test Description");
        recipeCreateDTO.setInstructions("Test Instructions");
        recipeCreateDTO.setAuthorId(1L);
        recipeCreateDTO.setIsPublic(true);
        recipeCreateDTO.setAllergenIds(Arrays.asList(1));
        recipeCreateDTO.setMealTypeId(1);

        IngredientItem ingredient = new IngredientItem();
        ingredient.setName("Flour");
        ingredient.setQuantity(2.0);
        ingredient.setUnit("cups");
        recipeCreateDTO.setIngredients(Arrays.asList(ingredient));

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getAllRecipes_ShouldReturnAllRecipes() {
        // Arrange
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));

        // Act
        List<RecipeDTO> result = recipeService.getAllRecipes();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Recipe");
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById_ShouldReturnRecipe_WhenExists() {
        // Arrange
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        // Act
        RecipeDTO result = recipeService.getRecipeById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Recipe");
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    void getRecipeById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(recipeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> recipeService.getRecipeById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Recipe not found");
    }

    @Test
    void getPublicRecipes_ShouldReturnOnlyPublicRecipes() {
        // Arrange
        Recipe privateRecipe = new Recipe();
        privateRecipe.setId(2L);
        privateRecipe.setTitle("Private Recipe");
        privateRecipe.setIsPublic(false);
        privateRecipe.setAuthor(user);

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe, privateRecipe));

        // Act
        List<RecipeDTO> result = recipeService.getPublicRecipes();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Recipe");
        assertThat(result.get(0).getIsPublic()).isTrue();
    }

    @Test
    void getRecipesByAuthor_ShouldReturnAuthorRecipes() {
        // Arrange
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));

        // Act
        List<RecipeDTO> result = recipeService.getRecipesByAuthor(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void createRecipe_ShouldCreateRecipe() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(allergenRepository.findAllById(Arrays.asList(1))).thenReturn(Arrays.asList(allergen));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Act
        RecipeDTO result = recipeService.createRecipe(recipeCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Recipe");
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void createRecipe_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> recipeService.createRecipe(recipeCreateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void updateRecipe_ShouldUpdateRecipe_WhenExists() {
        // Arrange
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(allergenRepository.findAllById(any())).thenReturn(Arrays.asList(allergen));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        recipeDTO.setTitle("Updated Recipe");

        // Act
        RecipeDTO result = recipeService.updateRecipe(1L, recipeDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void updateRecipe_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> recipeService.updateRecipe(1L, recipeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Recipe not found");
    }

    @Test
    void deleteRecipe_ShouldDeleteRecipe_WhenAuthorized() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        doNothing().when(recipeRepository).deleteRecipeAllergensByRecipeId(1L);
        doNothing().when(recipeRepository).deleteNutritionInfoByRecipeId(1L);
        doNothing().when(recipeRepository).deleteRatingsByRecipeId(1L);
        doNothing().when(recipeRepository).deleteFavoritesByRecipeId(1L);
        doNothing().when(recipeRepository).deleteGroupRecipesByRecipeId(1L);
        doNothing().when(recipeRepository).deleteMealPlanItemsByRecipeId(1L);
        doNothing().when(recipeRepository).deleteById(1L);

        // Act
        recipeService.deleteRecipe(1L);

        // Assert
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRecipe_ShouldThrowException_WhenUnauthorized() {
        // Arrange
        User differentUser = new User();
        differentUser.setId(2L);
        differentUser.setEmail("other@example.com");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("other@example.com");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(userRepository.findByEmail("other@example.com")).thenReturn(Optional.of(differentUser));

        // Act & Assert
        assertThatThrownBy(() -> recipeService.deleteRecipe(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No tienes permiso");
    }

    @Test
    void createRecipe_ShouldHandleEmptyAllergensList() {
        // Arrange
        recipeCreateDTO.setAllergenIds(Arrays.asList());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Act
        RecipeDTO result = recipeService.createRecipe(recipeCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(allergenRepository, never()).findAllById(any());
    }

    @Test
    void createRecipe_ShouldHandleNullAllergensList() {
        // Arrange
        recipeCreateDTO.setAllergenIds(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Act
        RecipeDTO result = recipeService.createRecipe(recipeCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(allergenRepository, never()).findAllById(any());
    }
}

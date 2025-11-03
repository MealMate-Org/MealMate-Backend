package com.MealMate.MealMateBackend.rating.service;

import com.MealMate.MealMateBackend.rating.dto.RatingDTO;
import com.MealMate.MealMateBackend.rating.model.Rating;
import com.MealMate.MealMateBackend.rating.model.RatingKey;
import com.MealMate.MealMateBackend.rating.repository.RatingRepository;
import com.MealMate.MealMateBackend.recipe.model.Recipe;
import com.MealMate.MealMateBackend.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private Rating rating;
    private RatingDTO ratingDTO;
    private Recipe recipe;
    private RatingKey ratingKey;

    @BeforeEach
    void setUp() {
        ratingKey = new RatingKey(1L, 1L);
        
        rating = new Rating();
        rating.setId(ratingKey);
        rating.setScore(5);

        ratingDTO = new RatingDTO();
        ratingDTO.setRecipeId(1L);
        ratingDTO.setUserId(1L);
        ratingDTO.setScore(5);

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe");
        recipe.setAvgRating(BigDecimal.ZERO);
        recipe.setRatingCount(0);
    }

    @Test
    void getAllRatings_ShouldReturnAllRatings() {
        // Arrange
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<RatingDTO> result = ratingService.getAllRatings();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRecipeId()).isEqualTo(1L);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void getRatingById_ShouldReturnRating_WhenExists() {
        // Arrange
        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.of(rating));

        // Act
        RatingDTO result = ratingService.getRatingById(1L, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRecipeId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getScore()).isEqualTo(5);
        verify(ratingRepository, times(1)).findByRecipeIdAndUserId(1L, 1L);
    }

    @Test
    void getRatingById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> ratingService.getRatingById(1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rating not found");

        verify(ratingRepository, times(1)).findByRecipeIdAndUserId(1L, 1L);
    }

    @Test
    void createRating_ShouldCreateNewRating_WhenNotExists() {
        // Arrange
        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(ratingRepository.findByRecipeId(1L)).thenReturn(Arrays.asList(rating));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Act
        RatingDTO result = ratingService.createRating(ratingDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getScore()).isEqualTo(5);
        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void createRating_ShouldUpdateExistingRating_WhenExists() {
        // Arrange
        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(ratingRepository.findByRecipeId(1L)).thenReturn(Arrays.asList(rating));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        ratingDTO.setScore(4);

        // Act
        RatingDTO result = ratingService.createRating(ratingDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void updateRating_ShouldUpdateRating_WhenExists() {
        // Arrange
        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(ratingRepository.findByRecipeId(1L)).thenReturn(Arrays.asList(rating));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        ratingDTO.setScore(3);

        // Act
        RatingDTO result = ratingService.updateRating(1L, 1L, ratingDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void updateRating_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> ratingService.updateRating(1L, 1L, ratingDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rating not found");

        verify(ratingRepository, times(1)).findByRecipeIdAndUserId(1L, 1L);
    }

    @Test
    void deleteRating_ShouldDeleteRating() {
        // Arrange
        doNothing().when(ratingRepository).deleteByRecipeIdAndUserId(1L, 1L);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(ratingRepository.findByRecipeId(1L)).thenReturn(Arrays.asList());
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Act
        ratingService.deleteRating(1L, 1L);

        // Assert
        verify(ratingRepository, times(1)).deleteByRecipeIdAndUserId(1L, 1L);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void createRating_ShouldCalculateAverageCorrectly() {
        // Arrange
        Rating rating1 = new Rating();
        rating1.setId(new RatingKey(1L, 1L));
        rating1.setScore(5);

        Rating rating2 = new Rating();
        rating2.setId(new RatingKey(1L, 2L));
        rating2.setScore(3);

        when(ratingRepository.findByRecipeIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(ratingRepository.findByRecipeId(1L)).thenReturn(Arrays.asList(rating1, rating2));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> {
            Recipe savedRecipe = invocation.getArgument(0);
            assertThat(savedRecipe.getAvgRating()).isEqualByComparingTo(new BigDecimal("4.00"));
            assertThat(savedRecipe.getRatingCount()).isEqualTo(2);
            return savedRecipe;
        });

        // Act
        ratingService.createRating(ratingDTO);

        // Assert
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}

package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanItemCreateDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanItemDTO;
import com.MealMate.MealMateBackend.planner.model.MealPlan;
import com.MealMate.MealMateBackend.planner.model.MealPlanItem;
import com.MealMate.MealMateBackend.planner.model.MealType;
import com.MealMate.MealMateBackend.planner.repository.MealPlanItemRepository;
import com.MealMate.MealMateBackend.planner.repository.MealPlanRepository;
import com.MealMate.MealMateBackend.planner.repository.MealTypeRepository;
import com.MealMate.MealMateBackend.recipe.model.Recipe;
import com.MealMate.MealMateBackend.recipe.repository.RecipeRepository;
import com.MealMate.MealMateBackend.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealPlanItemServiceImplTest {

    @Mock
    private MealPlanItemRepository mealPlanItemRepository;

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private MealTypeRepository mealTypeRepository;

    @InjectMocks
    private MealPlanItemServiceImpl mealPlanItemService;

    private MealPlanItem mealPlanItem;
    private MealPlan mealPlan;
    private Recipe recipe;
    private MealType mealType;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        mealPlan = new MealPlan();
        mealPlan.setId(1L);
        mealPlan.setUser(user);
        mealPlan.setStartDate(LocalDate.now());
        mealPlan.setEndDate(LocalDate.now().plusDays(7));

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe");

        mealType = new MealType();
        mealType.setId(1);
        mealType.setName("Breakfast");

        mealPlanItem = new MealPlanItem();
        mealPlanItem.setId(1L);
        mealPlanItem.setMealPlan(mealPlan);
        mealPlanItem.setRecipe(recipe);
        mealPlanItem.setMealType(mealType);
        mealPlanItem.setDate(LocalDate.now());
    }

    @Test
    void getAllMealPlanItems_ShouldReturnAllItems() {
        // Arrange
        when(mealPlanItemRepository.findAll()).thenReturn(Arrays.asList(mealPlanItem));

        // Act
        List<MealPlanItemDTO> result = mealPlanItemService.getAllMealPlanItems();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(mealPlanItemRepository, times(1)).findAll();
    }

    @Test
    void getItemsByMealPlanId_ShouldReturnItemsForMealPlan() {
        // Arrange
        when(mealPlanItemRepository.findByMealPlanId(1L)).thenReturn(Arrays.asList(mealPlanItem));

        // Act
        List<MealPlanItemDTO> result = mealPlanItemService.getItemsByMealPlanId(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMealPlanId()).isEqualTo(1L);
        verify(mealPlanItemRepository, times(1)).findByMealPlanId(1L);
    }

    @Test
    void getItemsByMealPlanIdAndDateRange_ShouldReturnItemsInRange() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        when(mealPlanItemRepository.findByMealPlanIdAndDateRange(1L, startDate, endDate))
                .thenReturn(Arrays.asList(mealPlanItem));

        // Act
        List<MealPlanItemDTO> result = mealPlanItemService.getItemsByMealPlanIdAndDateRange(1L, startDate, endDate);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMealPlanId()).isEqualTo(1L);
        verify(mealPlanItemRepository, times(1)).findByMealPlanIdAndDateRange(1L, startDate, endDate);
    }

    @Test
    void getItemsByUserIdAndDateRange_ShouldReturnItemsForUser() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        when(mealPlanItemRepository.findByUserIdAndDateRange(1L, startDate, endDate))
                .thenReturn(Arrays.asList(mealPlanItem));

        // Act
        List<MealPlanItemDTO> result = mealPlanItemService.getItemsByUserIdAndDateRange(1L, startDate, endDate);

        // Assert
        assertThat(result).hasSize(1);
        verify(mealPlanItemRepository, times(1)).findByUserIdAndDateRange(1L, startDate, endDate);
    }

    @Test
    void getMealPlanItemById_ShouldReturnItem_WhenExists() {
        // Arrange
        when(mealPlanItemRepository.findById(1L)).thenReturn(Optional.of(mealPlanItem));

        // Act
        MealPlanItemDTO result = mealPlanItemService.getMealPlanItemById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(mealPlanItemRepository, times(1)).findById(1L);
    }

    @Test
    void getMealPlanItemById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(mealPlanItemRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanItemService.getMealPlanItemById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealPlanItem not found");
    }

    @Test
    void createMealPlanItem_ShouldCreateNewItem_WhenNotExists() {
        // Arrange
        MealPlanItemCreateDTO createDTO = new MealPlanItemCreateDTO();
        createDTO.setMealPlanId(1L);
        createDTO.setRecipeId(1L);
        createDTO.setMealTypeId(1);
        createDTO.setDate(LocalDate.now());

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(mealTypeRepository.findById(1)).thenReturn(Optional.of(mealType));
        when(mealPlanItemRepository.findByMealPlanIdAndDateAndMealTypeId(1L, LocalDate.now(), 1))
                .thenReturn(Optional.empty());
        when(mealPlanItemRepository.save(any(MealPlanItem.class))).thenReturn(mealPlanItem);

        // Act
        MealPlanItemDTO result = mealPlanItemService.createMealPlanItem(createDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getMealPlanId()).isEqualTo(1L);
        verify(mealPlanItemRepository, times(1)).save(any(MealPlanItem.class));
    }

    @Test
    void createMealPlanItem_ShouldUpdateExisting_WhenExists() {
        // Arrange
        MealPlanItemCreateDTO createDTO = new MealPlanItemCreateDTO();
        createDTO.setMealPlanId(1L);
        createDTO.setRecipeId(1L);
        createDTO.setMealTypeId(1);
        createDTO.setDate(LocalDate.now());

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(mealTypeRepository.findById(1)).thenReturn(Optional.of(mealType));
        when(mealPlanItemRepository.findByMealPlanIdAndDateAndMealTypeId(1L, LocalDate.now(), 1))
                .thenReturn(Optional.of(mealPlanItem));
        when(mealPlanItemRepository.save(any(MealPlanItem.class))).thenReturn(mealPlanItem);

        // Act
        MealPlanItemDTO result = mealPlanItemService.createMealPlanItem(createDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(mealPlanItemRepository, times(1)).save(any(MealPlanItem.class));
    }

    @Test
    void createMealPlanItem_ShouldThrowException_WhenMealPlanNotFound() {
        // Arrange
        MealPlanItemCreateDTO createDTO = new MealPlanItemCreateDTO();
        createDTO.setMealPlanId(1L);
        createDTO.setRecipeId(1L);
        createDTO.setMealTypeId(1);

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanItemService.createMealPlanItem(createDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealPlan not found");
    }

    @Test
    void createMealPlanItem_ShouldThrowException_WhenRecipeNotFound() {
        // Arrange
        MealPlanItemCreateDTO createDTO = new MealPlanItemCreateDTO();
        createDTO.setMealPlanId(1L);
        createDTO.setRecipeId(1L);
        createDTO.setMealTypeId(1);

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanItemService.createMealPlanItem(createDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Recipe not found");
    }

    @Test
    void createMealPlanItem_ShouldThrowException_WhenMealTypeNotFound() {
        // Arrange
        MealPlanItemCreateDTO createDTO = new MealPlanItemCreateDTO();
        createDTO.setMealPlanId(1L);
        createDTO.setRecipeId(1L);
        createDTO.setMealTypeId(1);

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(mealTypeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanItemService.createMealPlanItem(createDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealType not found");
    }

    @Test
    void createMealPlanItemsBatch_ShouldCreateMultipleItems() {
        // Arrange
        MealPlanItemCreateDTO createDTO1 = new MealPlanItemCreateDTO();
        createDTO1.setMealPlanId(1L);
        createDTO1.setRecipeId(1L);
        createDTO1.setMealTypeId(1);
        createDTO1.setDate(LocalDate.now());

        MealPlanItemCreateDTO createDTO2 = new MealPlanItemCreateDTO();
        createDTO2.setMealPlanId(1L);
        createDTO2.setRecipeId(1L);
        createDTO2.setMealTypeId(2);
        createDTO2.setDate(LocalDate.now());

        MealType lunchType = new MealType();
        lunchType.setId(2);
        lunchType.setName("Lunch");

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(mealTypeRepository.findById(1)).thenReturn(Optional.of(mealType));
        when(mealTypeRepository.findById(2)).thenReturn(Optional.of(lunchType));
        when(mealPlanItemRepository.findByMealPlanIdAndDateAndMealTypeId(any(), any(), any()))
                .thenReturn(Optional.empty());
        when(mealPlanItemRepository.save(any(MealPlanItem.class))).thenReturn(mealPlanItem);

        // Act
        List<MealPlanItemDTO> result = mealPlanItemService.createMealPlanItemsBatch(Arrays.asList(createDTO1, createDTO2));

        // Assert
        assertThat(result).hasSize(2);
        verify(mealPlanItemRepository, times(2)).save(any(MealPlanItem.class));
    }

    @Test
    void updateMealPlanItem_ShouldUpdateItem_WhenExists() {
        // Arrange
        MealPlanItemDTO updateDTO = new MealPlanItemDTO();
        updateDTO.setRecipeId(1L);
        updateDTO.setMealTypeId(1);
        updateDTO.setDate(LocalDate.now().plusDays(1));

        when(mealPlanItemRepository.findById(1L)).thenReturn(Optional.of(mealPlanItem));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(mealTypeRepository.findById(1)).thenReturn(Optional.of(mealType));
        when(mealPlanItemRepository.save(any(MealPlanItem.class))).thenReturn(mealPlanItem);

        // Act
        MealPlanItemDTO result = mealPlanItemService.updateMealPlanItem(1L, updateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(mealPlanItemRepository, times(1)).save(any(MealPlanItem.class));
    }

    @Test
    void updateMealPlanItem_ShouldThrowException_WhenNotFound() {
        // Arrange
        MealPlanItemDTO updateDTO = new MealPlanItemDTO();
        when(mealPlanItemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanItemService.updateMealPlanItem(1L, updateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealPlanItem not found");
    }

    @Test
    void deleteMealPlanItem_ShouldDeleteItem() {
        // Arrange
        doNothing().when(mealPlanItemRepository).deleteById(1L);

        // Act
        mealPlanItemService.deleteMealPlanItem(1L);

        // Assert
        verify(mealPlanItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteMealPlanItemByDetails_ShouldDeleteItemByDetails() {
        // Arrange
        LocalDate date = LocalDate.now();
        doNothing().when(mealPlanItemRepository).deleteByMealPlanIdAndDateAndMealTypeId(1L, date, 1);

        // Act
        mealPlanItemService.deleteMealPlanItemByDetails(1L, date, 1);

        // Assert
        verify(mealPlanItemRepository, times(1)).deleteByMealPlanIdAndDateAndMealTypeId(1L, date, 1);
    }
}

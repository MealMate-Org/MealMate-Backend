package com.MealMate.MealMateBackend.shopping.service;

import com.MealMate.MealMateBackend.planner.model.MealPlan;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingItemDTO;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingListCreateDTO;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingListDTO;
import com.MealMate.MealMateBackend.shopping.model.ShoppingList;
import com.MealMate.MealMateBackend.shopping.repository.ShoppingListRepository;
import com.MealMate.MealMateBackend.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingListServiceImplTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShoppingListServiceImpl shoppingListService;

    private ShoppingList shoppingList;
    private ShoppingListDTO shoppingListDTO;
    private ShoppingListCreateDTO shoppingListCreateDTO;
    private User user;
    private MealPlan mealPlan;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        mealPlan = new MealPlan();
        mealPlan.setId(1L);

        ShoppingItemDTO item1 = new ShoppingItemDTO();
        item1.setName("Milk");
        item1.setQuantity(2.0);
        item1.setUnit("liters");
        item1.setChecked(false);

        ShoppingItemDTO item2 = new ShoppingItemDTO();
        item2.setName("Eggs");
        item2.setQuantity(12.0);
        item2.setUnit("units");
        item2.setChecked(false);

        shoppingList = new ShoppingList();
        shoppingList.setId(1L);
        shoppingList.setUser(user);
        shoppingList.setMealPlan(mealPlan);
        shoppingList.setWeekStartDate(LocalDate.now());
        shoppingList.setWeekEndDate(LocalDate.now().plusDays(7));
        shoppingList.setTitle("Weekly Shopping");
        shoppingList.setCreatedAt(LocalDateTime.now());
        shoppingList.setUpdatedAt(LocalDateTime.now());

        shoppingListDTO = new ShoppingListDTO();
        shoppingListDTO.setId(1L);
        shoppingListDTO.setUserId(1L);
        shoppingListDTO.setMealPlanId(1L);
        shoppingListDTO.setWeekStartDate(LocalDate.now());
        shoppingListDTO.setWeekEndDate(LocalDate.now().plusDays(7));
        shoppingListDTO.setTitle("Weekly Shopping");
        shoppingListDTO.setItems(Arrays.asList(item1, item2));
        shoppingListDTO.setCreatedAt(LocalDateTime.now());
        shoppingListDTO.setUpdatedAt(LocalDateTime.now());

        shoppingListCreateDTO = new ShoppingListCreateDTO();
        shoppingListCreateDTO.setUserId(1L);
        shoppingListCreateDTO.setMealPlanId(1L);
        shoppingListCreateDTO.setWeekStartDate(LocalDate.now());
        shoppingListCreateDTO.setWeekEndDate(LocalDate.now().plusDays(7));
        shoppingListCreateDTO.setTitle("Weekly Shopping");
        shoppingListCreateDTO.setItems(Arrays.asList(item1, item2));
    }

    @Test
    void getAllShoppingLists_ShouldReturnAllLists() {
        // Arrange
        when(shoppingListRepository.findAll()).thenReturn(Arrays.asList(shoppingList));
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        List<ShoppingListDTO> result = shoppingListService.getAllShoppingLists();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(shoppingListRepository, times(1)).findAll();
    }

    @Test
    void getShoppingListsByUserId_ShouldReturnUserLists() {
        // Arrange
        when(shoppingListRepository.findByUserId(1L)).thenReturn(Arrays.asList(shoppingList));
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        List<ShoppingListDTO> result = shoppingListService.getShoppingListsByUserId(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(shoppingListRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getShoppingListById_ShouldReturnList_WhenExists() {
        // Arrange
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        ShoppingListDTO result = shoppingListService.getShoppingListById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Weekly Shopping");
        verify(shoppingListRepository, times(1)).findById(1L);
    }

    @Test
    void getShoppingListById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(shoppingListRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> shoppingListService.getShoppingListById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ShoppingList not found");
    }

    @Test
    void createShoppingList_ShouldCreateList() {
        // Arrange
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        ShoppingListDTO result = shoppingListService.createShoppingList(shoppingListCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Weekly Shopping");
        assertThat(result.getItems()).hasSize(2);
        verify(shoppingListRepository, times(1)).save(any(ShoppingList.class));
    }

    @Test
    void createShoppingList_ShouldHandleNullItems() {
        // Arrange
        shoppingListCreateDTO.setItems(null);
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        ShoppingListDTO result = shoppingListService.createShoppingList(shoppingListCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(shoppingListRepository, times(1)).save(any(ShoppingList.class));
    }

    @Test
    void updateShoppingList_ShouldUpdateList_WhenExists() {
        // Arrange
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        shoppingListDTO.setTitle("Updated Shopping List");

        // Act
        ShoppingListDTO result = shoppingListService.updateShoppingList(1L, shoppingListDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(shoppingListRepository, times(1)).save(any(ShoppingList.class));
    }

    @Test
    void updateShoppingList_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> shoppingListService.updateShoppingList(1L, shoppingListDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ShoppingList not found");
    }

    @Test
    void updateShoppingList_ShouldUpdateItems() {
        // Arrange
        ShoppingItemDTO newItem = new ShoppingItemDTO();
        newItem.setName("Bread");
        newItem.setQuantity(2.0);
        newItem.setUnit("loaves");
        newItem.setChecked(false);

        shoppingListDTO.setItems(Arrays.asList(newItem));

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));
        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        ShoppingListDTO result = shoppingListService.updateShoppingList(1L, shoppingListDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(shoppingListRepository, times(1)).save(any(ShoppingList.class));
    }

    @Test
    void deleteShoppingList_ShouldDeleteList_WhenExists() {
        // Arrange
        when(shoppingListRepository.existsById(1L)).thenReturn(true);
        doNothing().when(shoppingListRepository).deleteById(1L);

        // Act
        shoppingListService.deleteShoppingList(1L);

        // Assert
        verify(shoppingListRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShoppingList_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(shoppingListRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> shoppingListService.deleteShoppingList(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ShoppingList not found");
    }

    @Test
    void createShoppingList_ShouldSetCheckedDefaultValue() {
        // Arrange
        ShoppingItemDTO itemWithoutChecked = new ShoppingItemDTO();
        itemWithoutChecked.setName("Sugar");
        itemWithoutChecked.setQuantity(1.0);
        itemWithoutChecked.setUnit("kg");
        // checked is null

        shoppingListCreateDTO.setItems(Arrays.asList(itemWithoutChecked));

        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);
        when(modelMapper.map(shoppingList, ShoppingListDTO.class)).thenReturn(shoppingListDTO);

        // Act
        ShoppingListDTO result = shoppingListService.createShoppingList(shoppingListCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(shoppingListRepository, times(1)).save(any(ShoppingList.class));
    }
}

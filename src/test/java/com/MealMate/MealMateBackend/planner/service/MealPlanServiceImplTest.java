package com.MealMate.MealMateBackend.planner.service;

import com.MealMate.MealMateBackend.planner.dto.MealPlanCreateDTO;
import com.MealMate.MealMateBackend.planner.dto.MealPlanDTO;
import com.MealMate.MealMateBackend.planner.model.MealPlan;
import com.MealMate.MealMateBackend.planner.repository.MealPlanRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
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
class MealPlanServiceImplTest {

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MealPlanServiceImpl mealPlanService;

    private MealPlan mealPlan;
    private MealPlanDTO mealPlanDTO;
    private MealPlanCreateDTO mealPlanCreateDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        mealPlan = new MealPlan();
        mealPlan.setId(1L);
        mealPlan.setUser(user);
        mealPlan.setStartDate(LocalDate.now());
        mealPlan.setEndDate(LocalDate.now().plusDays(7));
        mealPlan.setIsActive(true);
        mealPlan.setCreatedAt(LocalDateTime.now());

        mealPlanDTO = new MealPlanDTO();
        mealPlanDTO.setId(1L);
        mealPlanDTO.setUserId(1L);
        mealPlanDTO.setStartDate(LocalDate.now());
        mealPlanDTO.setEndDate(LocalDate.now().plusDays(7));
        mealPlanDTO.setIsActive(true);
        mealPlanDTO.setCreatedAt(LocalDateTime.now());

        mealPlanCreateDTO = new MealPlanCreateDTO();
        mealPlanCreateDTO.setUserId(1L);
        mealPlanCreateDTO.setStartDate(LocalDate.now());
        mealPlanCreateDTO.setEndDate(LocalDate.now().plusDays(7));
    }

    @Test
    void getAllMealPlans_ShouldReturnAllMealPlans() {
        // Arrange
        when(mealPlanRepository.findAll()).thenReturn(Arrays.asList(mealPlan));
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        List<MealPlanDTO> result = mealPlanService.getAllMealPlans();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(mealPlanRepository, times(1)).findAll();
    }

    @Test
    void getMealPlansByUserId_ShouldReturnUserMealPlans() {
        // Arrange
        when(mealPlanRepository.findByUserId(1L)).thenReturn(Arrays.asList(mealPlan));
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        List<MealPlanDTO> result = mealPlanService.getMealPlansByUserId(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(mealPlanRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getMealPlanById_ShouldReturnMealPlan_WhenExists() {
        // Arrange
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        MealPlanDTO result = mealPlanService.getMealPlanById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(mealPlanRepository, times(1)).findById(1L);
    }

    @Test
    void getMealPlanById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(mealPlanRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanService.getMealPlanById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealPlan not found");
    }

    @Test
    void getActiveByUserIdAndDate_ShouldReturnActiveMealPlan() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(mealPlanRepository.findActiveByUserIdAndDate(1L, date)).thenReturn(Optional.of(mealPlan));
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        MealPlanDTO result = mealPlanService.getActiveByUserIdAndDate(1L, date);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getIsActive()).isTrue();
        verify(mealPlanRepository, times(1)).findActiveByUserIdAndDate(1L, date);
    }

    @Test
    void getActiveByUserIdAndDate_ShouldThrowException_WhenNoActivePlan() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(mealPlanRepository.findActiveByUserIdAndDate(1L, date)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanService.getActiveByUserIdAndDate(1L, date))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No active MealPlan found");
    }

    @Test
    void createMealPlan_ShouldCreateNewMealPlan() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealPlanRepository.findByUserIdAndDateRange(any(), any(), any())).thenReturn(Optional.empty());
        when(mealPlanRepository.save(any(MealPlan.class))).thenReturn(mealPlan);
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        MealPlanDTO result = mealPlanService.createMealPlan(mealPlanCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        verify(mealPlanRepository, times(1)).save(any(MealPlan.class));
    }

    @Test
    void createMealPlan_ShouldReturnExistingPlan_WhenAlreadyExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealPlanRepository.findByUserIdAndDateRange(any(), any(), any())).thenReturn(Optional.of(mealPlan));
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        MealPlanDTO result = mealPlanService.createMealPlan(mealPlanCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(mealPlanRepository, never()).save(any(MealPlan.class));
    }

    @Test
    void createMealPlan_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanService.createMealPlan(mealPlanCreateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void getOrCreateMealPlanForWeek_ShouldReturnExisting_WhenExists() {
        // Arrange
        LocalDate weekStart = LocalDate.now();
        LocalDate weekEnd = LocalDate.now().plusDays(7);
        when(mealPlanRepository.findByUserIdAndDateRange(1L, weekStart, weekEnd)).thenReturn(Optional.of(mealPlan));
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        MealPlanDTO result = mealPlanService.getOrCreateMealPlanForWeek(1L, weekStart, weekEnd);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(mealPlanRepository, never()).save(any(MealPlan.class));
    }

    @Test
    void getOrCreateMealPlanForWeek_ShouldCreateNew_WhenNotExists() {
        // Arrange
        LocalDate weekStart = LocalDate.now();
        LocalDate weekEnd = LocalDate.now().plusDays(7);
        when(mealPlanRepository.findByUserIdAndDateRange(1L, weekStart, weekEnd)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealPlanRepository.save(any(MealPlan.class))).thenReturn(mealPlan);
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        // Act
        MealPlanDTO result = mealPlanService.getOrCreateMealPlanForWeek(1L, weekStart, weekEnd);

        // Assert
        assertThat(result).isNotNull();
        verify(mealPlanRepository, times(1)).save(any(MealPlan.class));
    }

    @Test
    void updateMealPlan_ShouldUpdateMealPlan_WhenExists() {
        // Arrange
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(mealPlanRepository.save(any(MealPlan.class))).thenReturn(mealPlan);
        when(modelMapper.map(mealPlan, MealPlanDTO.class)).thenReturn(mealPlanDTO);

        mealPlanDTO.setIsActive(false);

        // Act
        MealPlanDTO result = mealPlanService.updateMealPlan(1L, mealPlanDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(mealPlanRepository, times(1)).save(any(MealPlan.class));
    }

    @Test
    void updateMealPlan_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mealPlanService.updateMealPlan(1L, mealPlanDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("MealPlan not found");
    }

    @Test
    void deleteMealPlan_ShouldDeleteMealPlan() {
        // Arrange
        doNothing().when(mealPlanRepository).deleteById(1L);

        // Act
        mealPlanService.deleteMealPlan(1L);

        // Assert
        verify(mealPlanRepository, times(1)).deleteById(1L);
    }
}

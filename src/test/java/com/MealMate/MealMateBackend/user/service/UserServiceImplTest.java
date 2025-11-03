package com.MealMate.MealMateBackend.user.service;

import com.MealMate.MealMateBackend.user.dto.UserCreateDTO;
import com.MealMate.MealMateBackend.user.dto.UserDTO;
import com.MealMate.MealMateBackend.user.model.Role;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.RoleRepository;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private UserCreateDTO userCreateDTO;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("USER");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setRoleId(1);

        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("testuser");
        userCreateDTO.setEmail("test@example.com");
        userCreateDTO.setPassword("password123");
        userCreateDTO.setRoleId(1);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.getUserById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void createUser_ShouldCreateUser() {
        // Arrange
        when(modelMapper.map(userCreateDTO, User.class)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.createUser(userCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userDTO.setUsername("updateduser");

        // Act
        UserDTO result = userService.updateUser(1L, userDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateRole_WhenRoleChanged() {
        // Arrange
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("ADMIN");

        userDTO.setRoleId(2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2)).thenReturn(Optional.of(newRole));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDTO result = userService.updateUser(1L, userDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(roleRepository, times(1)).findById(2);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(1L, userDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void updateUser_ShouldThrowException_WhenRoleNotFound() {
        // Arrange
        userDTO.setRoleId(999);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(1L, userDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Role not found");
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateUser_ShouldNotUpdateRole_WhenRoleUnchanged() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDTO result = userService.updateUser(1L, userDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(roleRepository, never()).findById(any());
    }
}

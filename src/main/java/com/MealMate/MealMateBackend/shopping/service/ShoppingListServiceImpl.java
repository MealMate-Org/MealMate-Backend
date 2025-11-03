package com.MealMate.MealMateBackend.shopping.service;

import com.MealMate.MealMateBackend.shopping.dto.ShoppingListDTO;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingListCreateDTO;
import com.MealMate.MealMateBackend.shopping.model.ShoppingList;
import com.MealMate.MealMateBackend.shopping.model.ShoppingItem;
import com.MealMate.MealMateBackend.shopping.repository.ShoppingListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ShoppingListDTO> getAllShoppingLists() {
        return shoppingListRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShoppingListDTO> getShoppingListsByUserId(Long userId) {
        return shoppingListRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingListDTO getShoppingListById(Long id) {
        ShoppingList shoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingList not found with id: " + id));
        return convertToDTO(shoppingList);
    }

    @Override
    public ShoppingListDTO createShoppingList(ShoppingListCreateDTO shoppingListCreateDTO) {
        ShoppingList shoppingList = convertCreateDTOToEntity(shoppingListCreateDTO);
        shoppingList.setCreatedAt(LocalDateTime.now());
        shoppingList.setUpdatedAt(LocalDateTime.now());

        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
        return convertToDTO(savedShoppingList);
    }

    @Override
    public ShoppingListDTO updateShoppingList(Long id, ShoppingListDTO shoppingListDTO) {
        ShoppingList existingShoppingList = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingList not found with id: " + id));

        existingShoppingList.setItems(convertShoppingItems(shoppingListDTO.getItems()));
        existingShoppingList.setTitle(shoppingListDTO.getTitle());
        existingShoppingList.setUpdatedAt(LocalDateTime.now());

        ShoppingList updatedShoppingList = shoppingListRepository.save(existingShoppingList);
        return convertToDTO(updatedShoppingList);
    }

    @Override
    public void deleteShoppingList(Long id) {
        if (!shoppingListRepository.existsById(id)) {
            throw new RuntimeException("ShoppingList not found with id: " + id);
        }
        shoppingListRepository.deleteById(id);
    }

    private ShoppingListDTO convertToDTO(ShoppingList shoppingList) {
        ShoppingListDTO dto = modelMapper.map(shoppingList, ShoppingListDTO.class);

        if (shoppingList.getUser() != null) {
            dto.setUserId(shoppingList.getUser().getId());
        }
        if (shoppingList.getMealPlan() != null) {
            dto.setMealPlanId(shoppingList.getMealPlan().getId());
        }

        return dto;
    }

    private ShoppingList convertCreateDTOToEntity(ShoppingListCreateDTO dto) {
        ShoppingList entity = new ShoppingList();
        entity.setTitle(dto.getTitle());
        entity.setWeekStartDate(dto.getWeekStartDate());
        entity.setWeekEndDate(dto.getWeekEndDate());
        entity.setItems(convertShoppingItems(dto.getItems()));

        if (dto.getUserId() != null) {
            com.MealMate.MealMateBackend.user.model.User user = new com.MealMate.MealMateBackend.user.model.User();
            user.setId(dto.getUserId());
            entity.setUser(user);
        }

        if (dto.getMealPlanId() != null) {
            com.MealMate.MealMateBackend.planner.model.MealPlan mealPlan = new com.MealMate.MealMateBackend.planner.model.MealPlan();
            mealPlan.setId(dto.getMealPlanId());
            entity.setMealPlan(mealPlan);
        }

        return entity;
    }

    /**
     * Convierte los ShoppingItem del DTO al modelo
     */
    private List<ShoppingItem> convertShoppingItems(
            List<com.MealMate.MealMateBackend.shopping.dto.ShoppingItemDTO> dtoItems) {
        if (dtoItems == null) {
            return null;
        }

        return dtoItems.stream()
                .map(dtoItem -> new ShoppingItem(
                        dtoItem.getName(),
                        dtoItem.getQuantity(),
                        dtoItem.getUnit(),
                        dtoItem.getChecked() != null ? dtoItem.getChecked() : false))
                .collect(Collectors.toList());
    }
}
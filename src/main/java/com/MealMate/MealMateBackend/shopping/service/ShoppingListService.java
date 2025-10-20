package com.MealMate.MealMateBackend.shopping.service;

import com.MealMate.MealMateBackend.shopping.dto.ShoppingListDTO;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingListCreateDTO;
import java.util.List;

public interface ShoppingListService {
    List<ShoppingListDTO> getAllShoppingLists();
    ShoppingListDTO getShoppingListById(Long id);
    ShoppingListDTO createShoppingList(ShoppingListCreateDTO shoppingListCreateDTO);
    ShoppingListDTO updateShoppingList(Long id, ShoppingListDTO shoppingListDTO);
    void deleteShoppingList(Long id);
}
package com.MealMate.MealMateBackend.shopping.controller;

import com.MealMate.MealMateBackend.shopping.dto.ShoppingListDTO;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingListCreateDTO;
import com.MealMate.MealMateBackend.shopping.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-lists")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping
    public ResponseEntity<List<ShoppingListDTO>> getAllShoppingLists() {
        return ResponseEntity.ok(shoppingListService.getAllShoppingLists());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingListDTO>> getShoppingListsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingListService.getShoppingListsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> getShoppingListById(@PathVariable Long id) {
        return ResponseEntity.ok(shoppingListService.getShoppingListById(id));
    }

    @PostMapping
    public ResponseEntity<ShoppingListDTO> createShoppingList(@RequestBody ShoppingListCreateDTO shoppingListCreateDTO) {
        return ResponseEntity.ok(shoppingListService.createShoppingList(shoppingListCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListDTO> updateShoppingList(@PathVariable Long id, @RequestBody ShoppingListDTO shoppingListDTO) {
        return ResponseEntity.ok(shoppingListService.updateShoppingList(id, shoppingListDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return ResponseEntity.noContent().build();
    }
}
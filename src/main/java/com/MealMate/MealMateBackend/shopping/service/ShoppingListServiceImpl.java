package com.MealMate.MealMateBackend.shopping.service;

import com.MealMate.MealMateBackend.shopping.dto.ShoppingListDTO;
import com.MealMate.MealMateBackend.shopping.dto.ShoppingListCreateDTO;
import com.MealMate.MealMateBackend.shopping.model.ShoppingList;
import com.MealMate.MealMateBackend.shopping.repository.ShoppingListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .map(shoppingList -> modelMapper.map(shoppingList, ShoppingListDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingListDTO getShoppingListById(Long id) {
        ShoppingList shoppingList = shoppingListRepository.findById(id).orElseThrow(() -> new RuntimeException("ShoppingList not found"));
        return modelMapper.map(shoppingList, ShoppingListDTO.class);
    }

    @Override
    public ShoppingListDTO createShoppingList(ShoppingListCreateDTO shoppingListCreateDTO) {
        ShoppingList shoppingList = modelMapper.map(shoppingListCreateDTO, ShoppingList.class);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
        return modelMapper.map(savedShoppingList, ShoppingListDTO.class);
    }

    @Override
    public ShoppingListDTO updateShoppingList(Long id, ShoppingListDTO shoppingListDTO) {
        ShoppingList shoppingList = shoppingListRepository.findById(id).orElseThrow(() -> new RuntimeException("ShoppingList not found"));
        modelMapper.map(shoppingListDTO, shoppingList);
        ShoppingList updatedShoppingList = shoppingListRepository.save(shoppingList);
        return modelMapper.map(updatedShoppingList, ShoppingListDTO.class);
    }

    @Override
    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }
}
package com.MealMate.MealMateBackend.recipe.service;

import com.MealMate.MealMateBackend.recipe.dto.RecipeDTO;
import com.MealMate.MealMateBackend.recipe.dto.RecipeCreateDTO;
import com.MealMate.MealMateBackend.recipe.model.Recipe;
import com.MealMate.MealMateBackend.recipe.model.Allergen;
import com.MealMate.MealMateBackend.recipe.repository.RecipeRepository;
import com.MealMate.MealMateBackend.recipe.repository.AllergenRepository;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AllergenRepository allergenRepository;

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        return convertToDTO(recipe);
    }

    @Override
    public List<RecipeDTO> getPublicRecipes() {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getIsPublic() != null && recipe.getIsPublic())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getRecipesByAuthor(Long authorId) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getAuthor().getId().equals(authorId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
@Transactional
public void deleteRecipe(Long id) {
    System.out.println("🗑️ ===== INICIANDO ELIMINACIÓN DE RECETA " + id + " =====");
    
    Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found"));

    System.out.println("✅ Receta encontrada: " + recipe.getTitle());

    try {
        // 1. Limpiar relaciones ManyToMany PRIMERO
        System.out.println("🔄 Limpiando relaciones ManyToMany...");
        if (recipe.getAllergens() != null && !recipe.getAllergens().isEmpty()) {
            System.out.println("   - Alérgenos a limpiar: " + recipe.getAllergens().size());
            recipe.getAllergens().clear();
            recipeRepository.saveAndFlush(recipe);
        }

        // 2. Eliminar referencias en ORDEN CORRECTO
        System.out.println("🔄 Eliminando referencias en otras tablas...");
        
        System.out.println("   - Eliminando meal_plan_items...");
        recipeRepository.deleteMealPlanItemsByRecipeId(id);
        
        System.out.println("   - Eliminando recipe_permissions...");
        recipeRepository.deleteRecipePermissionsByRecipeId(id);
        
        System.out.println("   - Eliminando group_recipes...");
        recipeRepository.deleteGroupRecipesByRecipeId(id);
        
        System.out.println("   - Eliminando favorites...");
        recipeRepository.deleteFavoritesByRecipeId(id);
        
        System.out.println("   - Eliminando ratings...");
        recipeRepository.deleteRatingsByRecipeId(id);
        
        System.out.println("   - Eliminando nutrition_info...");
        recipeRepository.deleteNutritionInfoByRecipeId(id);
        
        System.out.println("   - Eliminando recipe_allergens...");
        recipeRepository.deleteRecipeAllergensByRecipeId(id);

        // 3. Flush para asegurar que todo se ejecutó
        recipeRepository.flush();

        // 4. AHORA SÍ eliminar la receta
        System.out.println("🗑️ Eliminando la receta principal...");
        recipeRepository.deleteById(id);
        recipeRepository.flush();
        
        System.out.println("✅ ===== RECETA " + id + " ELIMINADA EXITOSAMENTE =====");
    } catch (Exception e) {
        System.err.println("❌ ===== ERROR ELIMINANDO RECETA " + id + " =====");
        System.err.println("Tipo de error: " + e.getClass().getName());
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Causa raíz: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A"));
        e.printStackTrace();
        throw new RuntimeException("Error al eliminar la receta: " + e.getMessage(), e);
    }
}

    @Override
    @Transactional
    public RecipeDTO createRecipe(RecipeCreateDTO recipeCreateDTO) {
        User author = userRepository.findById(recipeCreateDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeCreateDTO.getTitle());
        recipe.setDescription(recipeCreateDTO.getDescription());
        recipe.setInstructions(recipeCreateDTO.getInstructions());
        recipe.setImagePath(recipeCreateDTO.getImagePath());
        recipe.setAuthor(author);
        recipe.setIsPublic(recipeCreateDTO.getIsPublic());
        recipe.setIngredients(convertDtoIngredientsToModel(recipeCreateDTO.getIngredients()));
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setAvgRating(BigDecimal.ZERO);
        recipe.setRatingCount(0);
        recipe.setMealTypeId(recipeCreateDTO.getMealTypeId());

        if (recipeCreateDTO.getAllergenIds() != null && !recipeCreateDTO.getAllergenIds().isEmpty()) {
            List<Allergen> allergens = allergenRepository.findAllById(recipeCreateDTO.getAllergenIds());
            recipe.setAllergens(allergens);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        return convertToDTO(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setImagePath(recipeDTO.getImagePath());
        recipe.setIsPublic(recipeDTO.getIsPublic());
        recipe.setIngredients(convertDtoIngredientsToModel(recipeDTO.getIngredients()));
        recipe.setUpdatedAt(LocalDateTime.now());
        recipe.setMealTypeId(recipeDTO.getMealTypeId());

        if (recipeDTO.getAllergens() != null) {
            List<Integer> allergenIds = recipeDTO.getAllergens().stream()
                    .map(Allergen::getId)
                    .collect(Collectors.toList());
            List<Allergen> allergens = allergenRepository.findAllById(allergenIds);
            recipe.setAllergens(allergens);
        }

        Recipe updatedRecipe = recipeRepository.save(recipe);
        return convertToDTO(updatedRecipe);
    }

    // ===========================
    // MÉTODOS DE CONVERSIÓN
    // ===========================

    private RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setInstructions(recipe.getInstructions());
        dto.setImagePath(recipe.getImagePath());
        dto.setAuthorId(recipe.getAuthor().getId());
        dto.setIsPublic(recipe.getIsPublic());
        dto.setCreatedAt(recipe.getCreatedAt());
        dto.setUpdatedAt(recipe.getUpdatedAt());
        dto.setDeletedAt(recipe.getDeletedAt());
        dto.setAvgRating(recipe.getAvgRating() != null ? recipe.getAvgRating().doubleValue() : 0.0);
        dto.setRatingCount(recipe.getRatingCount() != null ? recipe.getRatingCount() : 0);
        dto.setIngredients(convertModelIngredientsToDto(recipe.getIngredients()));
        dto.setAllergens(recipe.getAllergens());
        dto.setMealTypeId(recipe.getMealTypeId());
        return dto;
    }

    private List<com.MealMate.MealMateBackend.recipe.model.IngredientItem> convertDtoIngredientsToModel(
            List<com.MealMate.MealMateBackend.recipe.dto.IngredientItem> dtoIngredients) {

        if (dtoIngredients == null) {
            return null;
        }

        return dtoIngredients.stream()
                .map(dto -> new com.MealMate.MealMateBackend.recipe.model.IngredientItem(
                        dto.getName(),
                        dto.getQuantity(),
                        dto.getUnit()))
                .collect(Collectors.toList());
    }

    private List<com.MealMate.MealMateBackend.recipe.dto.IngredientItem> convertModelIngredientsToDto(
            List<com.MealMate.MealMateBackend.recipe.model.IngredientItem> modelIngredients) {

        if (modelIngredients == null) {
            return null;
        }

        return modelIngredients.stream()
                .map(model -> new com.MealMate.MealMateBackend.recipe.dto.IngredientItem(
                        model.getName(),
                        model.getQuantity(),
                        model.getUnit()))
                .collect(Collectors.toList());
    }
}
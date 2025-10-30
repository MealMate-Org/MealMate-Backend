package com.MealMate.MealMateBackend.recipe.controller;

import com.MealMate.MealMateBackend.recipe.dto.RecipeDTO;
import com.MealMate.MealMateBackend.recipe.dto.RecipeCreateDTO;
import com.MealMate.MealMateBackend.recipe.service.RecipeService;
import com.MealMate.MealMateBackend.user.model.User;
import com.MealMate.MealMateBackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(
            @RequestParam(required = false) Boolean isPublic,
            @RequestParam(required = false) Long authorId) {
        
        if (authorId != null) {
            // Obtener usuario autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = null;
            
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                String email = auth.getName();
                currentUser = userRepository.findByEmail(email).orElse(null);
            }
            
            // Obtener recetas del autor
            List<RecipeDTO> authorRecipes = recipeService.getRecipesByAuthor(authorId);
            
            // Si el usuario autenticado es el autor, devolver TODAS sus recetas (públicas y privadas)
            if (currentUser != null && currentUser.getId().equals(authorId)) {
                System.out.println("✅ Usuario " + currentUser.getId() + " solicitando SUS propias recetas");
                return ResponseEntity.ok(authorRecipes);
            } else {
                // Si es otro usuario o no autenticado, devolver solo las públicas
                System.out.println("⚠️ Usuario diferente o no autenticado solicitando recetas de " + authorId);
                List<RecipeDTO> publicRecipes = authorRecipes.stream()
                        .filter(RecipeDTO::getIsPublic)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(publicRecipes);
            }
        } else if (isPublic != null && !isPublic) {
            // Si explícitamente se pide isPublic=false, devolver todas (requiere permisos admin)
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } else {
            // Por defecto: solo recetas públicas (SECURE BY DEFAULT)
            return ResponseEntity.ok(recipeService.getPublicRecipes());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getRecipeById(id);
        
        // Si la receta es privada, verificar que el usuario sea el autor
        if (!recipe.getIsPublic()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
                throw new RuntimeException("Debes iniciar sesión para ver esta receta");
            }
            
            String email = auth.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            if (!recipe.getAuthorId().equals(currentUser.getId())) {
                throw new RuntimeException("No tienes permiso para ver esta receta privada");
            }
        }
        
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeCreateDTO recipeCreateDTO) {
        return ResponseEntity.ok(recipeService.createRecipe(recipeCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
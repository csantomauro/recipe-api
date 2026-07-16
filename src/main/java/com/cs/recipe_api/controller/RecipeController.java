package com.cs.recipe_api.controller;

import com.cs.recipe_api.dto.CreateRecipeRequestDto;
import com.cs.recipe_api.dto.RecipeResponseDto;
import com.cs.recipe_api.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(@Valid @RequestBody CreateRecipeRequestDto request) {
        RecipeResponseDto created = recipeService.createRecipe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }
}

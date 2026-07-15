package com.cs.recipe_api.service;

import com.cs.recipe_api.dto.CreateRecipeRequestDto;
import com.cs.recipe_api.dto.RecipeIngredientRequestDto;
import com.cs.recipe_api.dto.RecipeIngredientResponseDto;
import com.cs.recipe_api.dto.RecipeResponseDto;
import com.cs.recipe_api.exception.RecipeNotFoundException;
import com.cs.recipe_api.exception.UserNotFoundException;
import com.cs.recipe_api.model.Ingredient;
import com.cs.recipe_api.model.Recipe;
import com.cs.recipe_api.model.RecipeIngredient;
import com.cs.recipe_api.model.User;
import com.cs.recipe_api.repository.RatingRepository;
import com.cs.recipe_api.repository.RecipeRepository;
import com.cs.recipe_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final IngredientService ingredientService;

    @Transactional
    public RecipeResponseDto createRecipe(CreateRecipeRequestDto request) {
        User author = userRepository.findByUsername(request.getAuthorUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + request.getAuthorUsername())
                );
        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setCuisine(request.getCuisine());
        recipe.setAuthor(author);

        for (RecipeIngredientRequestDto ingredientDto : request.getIngredients()){
            Ingredient ingredient = ingredientService.findOrCreate(ingredientDto.getIngredientName());

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setQuantity(ingredientDto.getQuantity());
            recipeIngredient.setUnit(ingredientDto.getUnit());

            recipe.getIngredients().add(recipeIngredient);
        }
        Recipe saved = recipeRepository.save(recipe);
        return toResponseDto(saved);
    }

    public RecipeResponseDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
        return toResponseDto(recipe);
    }

    private RecipeResponseDto toResponseDto(Recipe recipe) {
        List<RecipeIngredientResponseDto> ingredientDtos = recipe.getIngredients().stream()
                .map(ri -> new RecipeIngredientResponseDto(
                        ri.getIngredient().getName(),
                        ri.getQuantity(),
                        ri.getUnit()
                ))
                .toList();

        Double avgRating = ratingRepository.findAverageScoreByRecipeId(recipe.getId());

        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getCuisine(),
                recipe.getAuthor().getUsername(),
                recipe.getCreatedAt(),
                ingredientDtos,
                avgRating
        );
    }
}

package com.cs.recipe_api;

import com.cs.recipe_api.dto.CreateRecipeRequestDto;
import com.cs.recipe_api.dto.RecipeIngredientRequestDto;
import com.cs.recipe_api.dto.RecipeResponseDto;
import com.cs.recipe_api.exception.UserNotFoundException;
import com.cs.recipe_api.model.Ingredient;
import com.cs.recipe_api.model.Recipe;
import com.cs.recipe_api.model.User;
import com.cs.recipe_api.repository.RatingRepository;
import com.cs.recipe_api.repository.RecipeRepository;
import com.cs.recipe_api.repository.UserRepository;
import com.cs.recipe_api.service.IngredientService;
import com.cs.recipe_api.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private RecipeService recipeService;

    private CreateRecipeRequestDto requestDto;
    private User author;
    private Ingredient flour;

    @BeforeEach
    void setUp() {
        RecipeIngredientRequestDto ingredientRequest = new RecipeIngredientRequestDto();
        ingredientRequest.setIngredientName("Flour");
        ingredientRequest.setQuantity(200.0);
        ingredientRequest.setUnit("g");

        requestDto = new CreateRecipeRequestDto();
        requestDto.setTitle("Pizza");
        requestDto.setDescription("Simple pizza dough");
        requestDto.setCuisine("Italian");
        requestDto.setAuthorUsername("mario");
        requestDto.setIngredients(List.of(ingredientRequest));

        author = new User();
        author.setId(1L);
        author.setUsername("mario");

        flour = new Ingredient();
        flour.setId(1L);
        flour.setName("Flour");
    }

    @Test
    void createRecipe_shouldSucceed_whenAuthorExists() {
        when(userRepository.findByUsername("mario")).thenReturn(Optional.of(author));
        when(ingredientService.findOrCreate("Flour")).thenReturn(flour);
        when(ratingRepository.findAverageScoreByRecipeId(any())).thenReturn(null);

        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> {
            Recipe r = invocation.getArgument(0);
            r.setId(1L);
            return r;
        });

        RecipeResponseDto result = recipeService.createRecipe(requestDto);

        assertThat(result.getTitle()).isEqualTo("Pizza");
        assertThat(result.getAuthorUsername()).isEqualTo("mario");
        assertThat(result.getIngredients()).hasSize(1);
        assertThat(result.getIngredients().get(0).getIngredientName()).isEqualTo("Flour");
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void createRecipe_shouldThrow_whenAuthorDoesNotExist() {
        when(userRepository.findByUsername("mario")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.createRecipe(requestDto))
                .isInstanceOf(UserNotFoundException.class);

        verify(recipeRepository, never()).save(any(Recipe.class));
    }
}

package com.cs.recipe_api.service;

import com.cs.recipe_api.dto.CreateRatingRequestDto;
import com.cs.recipe_api.exception.RatingAlreadyExistsException;
import com.cs.recipe_api.exception.RecipeNotFoundException;
import com.cs.recipe_api.exception.UserNotFoundException;
import com.cs.recipe_api.model.Rating;
import com.cs.recipe_api.model.Recipe;
import com.cs.recipe_api.model.User;
import com.cs.recipe_api.repository.RatingRepository;
import com.cs.recipe_api.repository.RecipeRepository;
import com.cs.recipe_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public void rateRecipe(Long recipeId, CreateRatingRequestDto request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + recipeId));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getUsername()));
        if (ratingRepository.existsByRecipeIdAndUserId(recipe.getId(), user.getId())) {
            throw new RatingAlreadyExistsException(
                    "User " + request.getUsername() + " already rated recipe " + recipeId);
        }

        Rating rating = new Rating();
        rating.setRecipe(recipe);
        rating.setUser(user);
        rating.setScore(request.getScore());

        ratingRepository.save(rating);
    }
}

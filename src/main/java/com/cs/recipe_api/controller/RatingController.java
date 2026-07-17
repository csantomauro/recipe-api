package com.cs.recipe_api.controller;

import com.cs.recipe_api.dto.CreateRatingRequestDto;
import com.cs.recipe_api.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/{recipeId}/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Void> rateRecipe(
            @PathVariable Long recipeId,
            @Valid @RequestBody CreateRatingRequestDto request
            ) {
        ratingService.rateRecipe(recipeId, request);
        return ResponseEntity.noContent().build();
    }
}

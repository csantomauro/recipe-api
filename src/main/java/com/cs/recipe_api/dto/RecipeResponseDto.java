package com.cs.recipe_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeResponseDto {
    private Long id;
    private String title;
    private String description;
    private String cuisine;
    private String authorUsername;
    private LocalDateTime createdAt;
    private List<RecipeIngredientResponseDto> ingredients;
    private Double averageRating;
}

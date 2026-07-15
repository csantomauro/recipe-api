package com.cs.recipe_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeIngredientResponseDto {
    private String ingredientName;
    private Double quantity;
    private String unit;
}

package com.cs.recipe_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientResponseDto {
    private Long id;
    private String name;
}

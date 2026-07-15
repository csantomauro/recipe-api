package com.cs.recipe_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRecipeRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Cuisine is required")
    private String cuisine;

    @NotBlank(message = "Author username is required")
    private String authorUsername;

    @NotEmpty(message = "At least one ingredient is required")
    @Valid
    private List<RecipeIngredientRequestDto> ingredients;
}

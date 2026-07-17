package com.cs.recipe_api.specification;

import com.cs.recipe_api.model.Recipe;
import com.cs.recipe_api.model.RecipeIngredient;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecification {

    public static Specification<Recipe> hasCuisine(String cuisine) {
        return (root, query, criteriaBuilder) -> {
            if (cuisine == null || cuisine.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("cuisine")), cuisine.toLowerCase());
        };
    }

    public static Specification<Recipe> titleContains(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Recipe> hasIngredient(String ingredientName) {
        return (root, query, criteriaBuilder) -> {
            if (ingredientName == null || ingredientName.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            Join<Recipe, RecipeIngredient> ingredientsJoin = root.join("ingredients");
            query.distinct(true);
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(ingredientsJoin.get("ingredient").get("name")),
                    ingredientName.toLowerCase()
            );
        };
    }
}

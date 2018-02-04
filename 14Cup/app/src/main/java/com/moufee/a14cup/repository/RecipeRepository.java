package com.moufee.a14cup.repository;

/**
 * A repository for recipes
 */

public class RecipeRepository {
    private static RecipeRepository sRecipeRepository;

    private RecipeRepository() {

    }

    public static RecipeRepository get() {
        if (sRecipeRepository == null) {
            sRecipeRepository = new RecipeRepository();
        }
        return sRecipeRepository;
    }
}

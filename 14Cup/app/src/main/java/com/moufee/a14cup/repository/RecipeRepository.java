package com.moufee.a14cup.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository for recipes
 */
@Singleton
public class RecipeRepository {
    private static RecipeRepository sRecipeRepository;

    @Inject
    public RecipeRepository() {

    }


}

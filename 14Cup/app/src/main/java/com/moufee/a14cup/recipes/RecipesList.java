package com.moufee.a14cup.recipes;

import java.util.List;

/**
 * Created by tyler on 3/11/18.
 */

public class RecipesList {

    public RecipesList() {
    }

    public List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }

    public class Hit {
        Recipe recipe;

        public Recipe getRecipe() {
            return recipe;
        }

        @Override
        public String toString() {
            return recipe.toString();
        }
    }

    @Override
    public String toString() {
        return hits.toString();
    }
}
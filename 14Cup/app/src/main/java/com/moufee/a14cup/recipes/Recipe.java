package com.moufee.a14cup.recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyler on 2/18/18.
 * Represents a recipe from the Edamam API
 */

public class Recipe {
    // Recipe Object Attributes
    public String label; // recipe name
    public String image; // image url
    public String url;
    public List<String> healthLabels;
    public List<String> cautions;
    public List<String> ingredientLines;
    public List<Ingredient> ingredients; // list of ingredients for the recipe
    public double calories;

    public Recipe() {
        label = "";
        ingredients = new ArrayList<>();
    }


    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public String getIngredientText(int pos) {
        return this.ingredients.get(pos).getText();
    }

    @Override
    public String toString() {
        return label;
    }
}
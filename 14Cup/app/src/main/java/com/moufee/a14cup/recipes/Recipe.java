package com.moufee.a14cup.recipes;

import java.util.List;

/**
 * Created by tyler on 2/18/18.
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

    public Recipe(){

    }


    public Recipe(String label, List<String> ingredientLines){
        this.label = label;
        this.ingredientLines = ingredientLines;
    }

    public Recipe(String label, String url, List<String> ingredientLines){
        this.label = label;
        this.ingredientLines = ingredientLines;
    }
}

class Ingredient{

    public String text;
}
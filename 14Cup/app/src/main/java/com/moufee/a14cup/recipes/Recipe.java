package com.moufee.a14cup.recipes;

import java.util.ArrayList;
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
        this.ingredients = new ArrayList<>();

        for(String ing : ingredientLines){
            this.ingredients.add(new Ingredient(ing));
        }
    }

    public List<Ingredient> getIngredients(){
        return this.ingredients;
    }

    public String getIngredientText(int pos){
        return this.ingredients.get(pos).getText();
    }

    @Override
    public String toString() {
        return label;
    }
}



/*class Ingredient{

    public String text;

    public Ingredient(String name){
        this.text = name;
    }

    public String getText(){
        return this.text;
    }
}*/
package com.moufee.a14cup.ui.recipes.dummy;

import com.moufee.a14cup.recipes.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tyler on 2/18/18.
 */

public class DummyRecipes {

    public static final List<Recipe> ITEMS = new ArrayList<Recipe>();

    static {
        ArrayList<String> ing_tortilla_soup = new ArrayList<String>(Arrays.asList("2 14- to 19-ounce cans chicken noodle soup",
                "1 cup frozen corn kernels",
                "1 teaspoon hot sauce",
                "2 cups tortilla chips",
                "1 avocado, cut into pieces"));
        ArrayList<String> ing_avacado_soup = new ArrayList<String>(Arrays.asList("1 cup cold buttermilk",
                "3/4 cup bottled clam juice",
                "1/2 cup ice water",
                "1 tablespoon fresh lime juice",
                "Salt and pepper",
                "Jumbo lump crab meat",
                "Crème fraîche, fried tortilla strips, and finely chopped chives (optional)"));
        ArrayList<String> ing_macaroni_soup = new ArrayList<String>(Arrays.asList("1 quart Brown Soup Stock",
                "Salt",
                "1/4 cup macaroni, broken in half-inch pieces",
                "Pepper"));
        String url_macaroni_soup = "http://www.foodista.com/recipe/53HSY34R/macaroni-soup";
        String url_avacado_soup = "http://www.seriouseats.com/recipes/2008/05/chilled-avocado-soup-with-crab-recipe.html";
        String url_tortilla_soup = "http://www.realsimple.com/food-recipes/browse-all-recipes/tortilla-soup-00000000006997/index.html";

        ITEMS.add(new Recipe("Tortilla Soup", url_tortilla_soup, ing_tortilla_soup));
        ITEMS.add(new Recipe("Avacado Soup", url_avacado_soup, ing_avacado_soup));
        ITEMS.add(new Recipe("Macaroni Soup", url_macaroni_soup, ing_macaroni_soup));

    }
}

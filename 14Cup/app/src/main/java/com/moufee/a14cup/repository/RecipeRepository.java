package com.moufee.a14cup.repository;

import com.moufee.a14cup.recipes.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository for recipes
 */

@Singleton
public class RecipeRepository {

    private static RecipeRepository sRecipeRepository;
    private String ApplicationID;
    private String ApplicationKey;
    private String RecipeURL = "https://api.edamam.com/search?";

    @Inject
    public RecipeRepository(String ID, String Key) {
        ApplicationID = ID;
        ApplicationKey = Key;
    }

    public boolean UpdateRepository(String ID, String Key) {
        ApplicationID = ID;
        ApplicationKey = Key;
        return true;
    }

    /*
    *  Arguments:
    *  1.) Search - The type of item you are looking for (ie chicken, beef, salt, etc)
    *  2.) Start - The starting location (Should be zero unless you are continuing a search)
    *  3.) Finish - The finishing location (Should be the number of items unless you are continuing a search)
    */
    public String GetRecipies(String Search, Integer Start, Integer Finish) throws IOException {
        String SearchURL = RecipeURL + "app_id=" + ApplicationID + "&app_key=" + ApplicationKey +
                "&from=" + Start + "&to=" + Finish + "&q=" + Search;


        URL url = new URL(SearchURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }


}

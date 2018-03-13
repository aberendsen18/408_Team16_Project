package com.moufee.a14cup.api;

import com.moufee.a14cup.recipes.RecipesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ben on 3/11/18.
 */

public interface EdamamService {

    @GET("search?app_id=d58595bb&app_key=7f0cfb732e1cac7d786e0458756084b4")
    Call<RecipesList> searchRecipes(@Query("q") String query, @Query("from") Integer from, @Query("to") Integer to);

}

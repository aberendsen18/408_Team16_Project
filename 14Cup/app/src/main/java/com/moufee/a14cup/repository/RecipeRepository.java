package com.moufee.a14cup.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.moufee.a14cup.api.EdamamService;
import com.moufee.a14cup.recipes.RecipesList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A repository for recipes
 */

@Singleton
public class RecipeRepository {

    private static RecipeRepository sRecipeRepository;
    private String ApplicationID;
    private String ApplicationKey;
    private EdamamService mEdamamService;

    @Inject
    public RecipeRepository(EdamamService edamamService) {
        mEdamamService = edamamService;
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
    public LiveData<RecipesList> GetRecipies(String search, Integer start, Integer finish) {
        final MutableLiveData<RecipesList> result = new MutableLiveData<>();
        Call<RecipesList> recipesListCall = mEdamamService.searchRecipes(search, start, finish);
        recipesListCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(Call<RecipesList> call, Response<RecipesList> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RecipesList> call, Throwable t) {

            }
        });
        return result;

    }


}

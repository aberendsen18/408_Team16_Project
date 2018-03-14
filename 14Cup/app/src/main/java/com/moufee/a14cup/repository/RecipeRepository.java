package com.moufee.a14cup.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

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


    private EdamamService mEdamamService;
    private static final String TAG = "RECIPE_REPOSITORY";

    @Inject
    public RecipeRepository(EdamamService edamamService) {
        mEdamamService = edamamService;
    }


    /**
     * Gets recipes for a given query
     *
     * @param search The query string, the type of recipe to search for (ie chicken, beef, salt, etc)
     * @param start  The starting location (Should be zero unless you are continuing a search)
     * @param finish The finishing location (Should be the number of items unless you are continuing a search)
     * @return A LiveData containing the RecipesList of results
     */
    public LiveData<RecipesList> getRecipes(String search, Integer start, Integer finish) {
        final MutableLiveData<RecipesList> result = new MutableLiveData<>();
        result.setValue(null);
        Call<RecipesList> recipesListCall = mEdamamService.searchRecipes(search, start, finish);
        recipesListCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(Call<RecipesList> call, Response<RecipesList> response) {
                if (!response.isSuccessful())
                    result.setValue(new RecipesList());
                else
                    result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RecipesList> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                result.setValue(new RecipesList());
            }
        });
        return result;

    }


}

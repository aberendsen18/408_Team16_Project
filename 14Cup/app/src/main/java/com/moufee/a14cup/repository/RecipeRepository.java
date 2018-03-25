package com.moufee.a14cup.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.moufee.a14cup.api.EdamamService;
import com.moufee.a14cup.recipes.RecipesList;
import com.moufee.a14cup.util.Resource;

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
    public LiveData<Resource<RecipesList>> getRecipes(String search, Integer start, Integer finish) {
        final MutableLiveData<Resource<RecipesList>> result = new MutableLiveData<>();
        result.setValue(Resource.<RecipesList>loading(null));
        Call<RecipesList> recipesListCall = mEdamamService.searchRecipes(search, start, finish);
        recipesListCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(@NonNull Call<RecipesList> call, @NonNull Response<RecipesList> response) {
                if (!response.isSuccessful()){
                    // Bug Number 9
                    //result.setValue(Resource.<RecipesList>error("API Error.", null));
                }
                else{
                    result.setValue(Resource.success(response.body()));
                }

            }

            @Override
            public void onFailure(@NonNull Call<RecipesList> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                result.setValue(Resource.<RecipesList>error("HTTP Error", null));
            }
        });
        return result;

    }


}

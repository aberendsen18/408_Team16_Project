package com.moufee.a14cup.ui.recipes;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.recipes.RecipesList;
import com.moufee.a14cup.repository.RecipeRepository;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tyler on 3/4/18.
 */

public class RecipeViewModel extends ViewModel {

    private ShoppingListRepository mShoppingListRepository;
    private UserRepository mUserRepository;
    private RecipeRepository mRecipeRepository;

    private LiveData<List<ShoppingList>> mListLiveData;
    //private Recipe mSelectedRecipe;
    private MutableLiveData<Recipe> mSelectedRecipe = new MutableLiveData<>();
    private MutableLiveData<RecipesList> mRecipesList = new MutableLiveData<>();
    private LiveData<FirebaseUser> mCurrentUser;

    @Inject
    public RecipeViewModel(RecipeRepository recipeRepository, ShoppingListRepository shoppingListRepository, UserRepository userRepository){
        mShoppingListRepository = shoppingListRepository;
        mUserRepository = userRepository;
        mRecipeRepository = recipeRepository;
        mCurrentUser = mUserRepository.getCurrentUser();
        mListLiveData = Transformations.switchMap(mCurrentUser, new Function<FirebaseUser, LiveData<List<ShoppingList>>>() {
            @Override
            public LiveData<List<ShoppingList>> apply(FirebaseUser input) {
                if (input == null)
                    return null;
                return mShoppingListRepository.getShoppingLists(input.getUid());
            }
        });

        // Get list of recipes from the user query (LiveData)
        // Store the query string



    }

    // Store the state of the selected recipe from the RecipeFragment
    public void setSelectedRecipe(Recipe recipe){
        mSelectedRecipe.setValue(recipe);
    }

    public void setRecipesList(RecipesList recipeList){
        mRecipesList.setValue(recipeList);
    }

    public LiveData<RecipesList> getRecipesList(){
        return mRecipesList;
    }

    public Recipe getSelectedRecipe(){
        return mSelectedRecipe.getValue();
    }

    public LiveData<Recipe> getSelectedLiveDataRecipe(){
        return mSelectedRecipe;
    }
}

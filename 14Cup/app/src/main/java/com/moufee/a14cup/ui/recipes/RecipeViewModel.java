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

    private LiveData<List<ShoppingList>> mShoppingListLiveData;
    private MutableLiveData<Recipe> mSelectedRecipe = new MutableLiveData<>();
    private LiveData<RecipesList> mRecipesList;
    private LiveData<FirebaseUser> mCurrentUser;
    private MutableLiveData<String> mQuery = new MutableLiveData<>();

    @Inject
    public RecipeViewModel(RecipeRepository recipeRepository, ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        mShoppingListRepository = shoppingListRepository;
        mUserRepository = userRepository;
        mRecipeRepository = recipeRepository;
        mCurrentUser = mUserRepository.getCurrentUser();
        mShoppingListLiveData = Transformations.switchMap(mCurrentUser, new Function<FirebaseUser, LiveData<List<ShoppingList>>>() {
            @Override
            public LiveData<List<ShoppingList>> apply(FirebaseUser input) {
                if (input == null)
                    return null;
                return mShoppingListRepository.getShoppingLists(input.getUid());
            }
        });

        mRecipesList = Transformations.switchMap(mQuery, new Function<String, LiveData<RecipesList>>() {
            @Override
            public LiveData<RecipesList> apply(String input) {
                return mRecipeRepository.getRecipes(input, 0, 10);
            }
        });

    }

    // Store the state of the selected recipe from the RecipeFragment
    public void setSelectedRecipe(Recipe recipe) {
        mSelectedRecipe.setValue(recipe);
    }

    public LiveData<RecipesList> getRecipesList() {
        return mRecipesList;
    }

    public void setQuery(String query) {
        mQuery.setValue(query);
    }

    public LiveData<Recipe> getSelectedRecipe() {
        return mSelectedRecipe;
    }

    public LiveData<List<ShoppingList>> getShoppingLists() {
        return mShoppingListLiveData;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return mCurrentUser;
    }
}

package com.moufee.a14cup.ui.recipes;

/**
 * Created by matti on 3/13/2018.
 */

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.recipes.RecipesList;
import com.moufee.a14cup.repository.CategoryRepository;
import com.moufee.a14cup.repository.RecipeRepository;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;
import com.moufee.a14cup.ui.list.ListViewModel;
import com.moufee.a14cup.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Ben on 2/10/18.
 * Tests for the {@link RecipeViewModel}
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class RecipesTest {

    private RecipeViewModel mRecipeViewModel;
    private UserRepository mUserRepository;
    private RecipeRepository mRecipeRepository;
    private MutableLiveData<FirebaseUser> mFirebaseUserLiveData;
    private FirebaseUser mFirebaseUser;
    private ShoppingListRepository mListRepository;
    private Recipe TestRecipe;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() {
        mUserRepository = mock(UserRepository.class);
        mRecipeRepository = mock(RecipeRepository.class);
        mFirebaseUser = mock(FirebaseUser.class);
        mListRepository = mock(ShoppingListRepository.class);
        mFirebaseUserLiveData = new MutableLiveData<>();

        when(mFirebaseUser.getUid()).thenReturn("foo");
        when(mUserRepository.getCurrentUser()).thenReturn(mFirebaseUserLiveData);

        Recipe TestRecipe = new Recipe();

        mRecipeViewModel = new RecipeViewModel(mRecipeRepository, mListRepository, mUserRepository);
    }

    @Test
    public void notNull() {
        assertThat(mRecipeViewModel.getRecipesList(), notNullValue());
        assertThat(mRecipeViewModel.getSelectedRecipe(), notNullValue());
        assertThat(mRecipeViewModel.getShoppingLists(), notNullValue());
    }


    @Test
    public void testSelectList() {
        mRecipeViewModel.setSelectedRecipe(TestRecipe);
        assertEquals(mRecipeViewModel.getSelectedRecipe().getValue(), TestRecipe);
    }

    @Test
    public void sendResultToUI() {
        MutableLiveData<RecipesList> recipesLiveData = new MutableLiveData<>();
        when(mRecipeViewModel.getRecipesList()).thenReturn(recipesLiveData);
        mRecipeViewModel = new RecipeViewModel(mRecipeRepository, mListRepository, mUserRepository);
        LiveData<RecipesList> resultLists = mRecipeViewModel.getRecipesList();
        Observer<RecipesList> listObserver = mock(Observer.class);
        resultLists.observeForever(listObserver);
        mFirebaseUserLiveData.setValue(mFirebaseUser);
        verify(listObserver, never()).onChanged(any(RecipesList.class));
        RecipesList recipesList = mock(RecipesList.class);
        recipesLiveData.setValue(recipesList);
        verify(listObserver).onChanged(any(RecipesList.class));
    }


}

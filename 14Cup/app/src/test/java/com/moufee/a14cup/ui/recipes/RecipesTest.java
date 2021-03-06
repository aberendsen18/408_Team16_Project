package com.moufee.a14cup.ui.recipes;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.recipes.RecipesList;
import com.moufee.a14cup.repository.RecipeRepository;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;
import com.moufee.a14cup.util.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


/**
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
    private Recipe mTestRecipe;

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

        mTestRecipe = new Recipe();

        mRecipeViewModel = new RecipeViewModel(mRecipeRepository, mListRepository, mUserRepository);
    }

    @Test
    public void notNull() {
        assertThat(mRecipeViewModel.getRecipesList(), notNullValue());
        assertThat(mRecipeViewModel.getSelectedRecipe(), notNullValue());
        assertThat(mRecipeViewModel.getShoppingLists(), notNullValue());
    }


    @Test
    public void testSelectRecipe() {
        mRecipeViewModel.setSelectedRecipe(mTestRecipe);
        assertEquals(mRecipeViewModel.getSelectedRecipe().getValue(), mTestRecipe);
    }

    @Test
    public void sendResultToUI() {
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        MutableLiveData<Resource<RecipesList>> recipesLiveData = new MutableLiveData<>();
        when(mRecipeRepository.getRecipes(anyString(), anyInt(), anyInt())).thenReturn(recipesLiveData);
        mRecipeViewModel = new RecipeViewModel(mRecipeRepository, mListRepository, mUserRepository);
        LiveData<Resource<RecipesList>> resultLists = mRecipeViewModel.getRecipesList();
        Observer<Resource<RecipesList>> listObserver = mock(Observer.class);
        resultLists.observeForever(listObserver);
        mFirebaseUserLiveData.setValue(mFirebaseUser);
        verify(listObserver, never()).onChanged(any(Resource.class));
        Resource<RecipesList> listResource = Resource.success(new RecipesList());
        RecipesList recipesList = mock(RecipesList.class);
        recipesLiveData.setValue(listResource);
        verify(listObserver, never()).onChanged(listResource);
        mRecipeViewModel.setQuery("apples");
        verify(mRecipeRepository).getRecipes(queryCaptor.capture(), anyInt(), anyInt());
        assertThat(queryCaptor.getValue(), is("apples"));
        verify(listObserver).onChanged(listResource);
    }


    @Test
    public void resetSameQuery() {
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        mRecipeViewModel.getRecipesList().observeForever(mock(Observer.class));
        mRecipeViewModel.setQuery("APPLES");
        verify(mRecipeRepository).getRecipes(queryCaptor.capture(), anyInt(), anyInt());
        assertEquals(queryCaptor.getValue(), "apples");
        reset(mRecipeRepository);
        mRecipeViewModel.setQuery("apples");
        verifyNoMoreInteractions(mRecipeRepository);
        mRecipeViewModel.setQuery("Oranges");
        verify(mRecipeRepository).getRecipes(queryCaptor.capture(), anyInt(), anyInt());
        assertEquals(queryCaptor.getValue(), "oranges");
    }


}

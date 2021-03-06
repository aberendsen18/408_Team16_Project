package com.moufee.a14cup.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.moufee.a14cup.ui.categorySorting.CategorySortViewModel;
import com.moufee.a14cup.ui.list.ListViewModel;
import com.moufee.a14cup.ui.recipes.RecipeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Ben on 2/6/18.
 */

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel.class)
    abstract ViewModel bindRecipeViewModel(RecipeViewModel recipeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategorySortViewModel.class)
    abstract ViewModel bindSortViewModel(CategorySortViewModel categorySortViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ShoppingListAppViewModelFactory factory);
}

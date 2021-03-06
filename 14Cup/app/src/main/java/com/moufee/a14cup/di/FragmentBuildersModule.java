package com.moufee.a14cup.di;

import com.moufee.a14cup.ui.categorySorting.CategorySortFragment;
import com.moufee.a14cup.ui.categorySorting.CategorySortListFragment;
import com.moufee.a14cup.ui.list.ListDetailFragment;
import com.moufee.a14cup.ui.recipes.RecipeFragment;
import com.moufee.a14cup.ui.recipes.RecipeInfoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Defines the Fragments that may be injected
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ListDetailFragment contributeShoppingListDetailFragment();

    @ContributesAndroidInjector
    abstract RecipeInfoFragment contributeRecipeInfoFragment();

    @ContributesAndroidInjector
    abstract CategorySortListFragment contributeCategorySortListFragment();

    @ContributesAndroidInjector
    abstract CategorySortFragment contributeCategorySortFragment();

    @ContributesAndroidInjector
    abstract RecipeFragment contributeRecipeFragment();
}

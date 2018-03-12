package com.moufee.a14cup.di;

import com.moufee.a14cup.MainActivity;
import com.moufee.a14cup.ui.categorySorting.CategorySortActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * The Dagger Module for MainActivity
 * Allows MainActivity and Fragments it hosts to use dependency injection
 */
@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract CategorySortActivity contributeCategorySortActivity();
}

package com.moufee.a14cup.di;

import com.moufee.a14cup.ui.list.ListDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Defines the Fragments that may be injected
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ListDetailFragment contributeShoppingListDetailFragment();
}

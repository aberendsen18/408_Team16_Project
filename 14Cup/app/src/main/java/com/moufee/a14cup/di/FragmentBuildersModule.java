package com.moufee.a14cup.di;

import com.moufee.a14cup.ui.list.ShoppingListDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Ben on 2/6/18.
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ShoppingListDetailFragment contributeShoppingListDetailFragment();
}

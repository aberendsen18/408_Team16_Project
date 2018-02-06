package com.moufee.a14cup.di;

import com.moufee.a14cup.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Ben on 2/6/18.
 */
@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}

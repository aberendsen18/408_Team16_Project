package com.moufee.a14cup;

import android.app.Activity;
import android.app.Application;

import com.moufee.a14cup.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Ben on 2/6/18.
 */

public class ShoppingListApp extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        // bind application instance to dagger graph
        DaggerAppComponent.builder().application(this).build().inject(this);

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}

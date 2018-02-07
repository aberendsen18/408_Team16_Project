package com.moufee.a14cup.di;

import android.app.Application;

import com.moufee.a14cup.ShoppingListApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Provides access to DI graph for the app
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, MainActivityModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(ShoppingListApp app);
}

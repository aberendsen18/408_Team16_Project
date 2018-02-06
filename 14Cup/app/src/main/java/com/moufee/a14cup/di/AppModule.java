package com.moufee.a14cup.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ben on 2/6/18.
 */

@Module
public class AppModule {
    @Singleton
    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    FirebaseFirestore provideFirestore() {
        return FirebaseFirestore.getInstance();
    }
}

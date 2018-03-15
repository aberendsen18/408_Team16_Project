package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.util.FirebaseAuthLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository for Users
 * Allows the current FirebaseUser to be retrieved
 */
@Singleton
public class UserRepository {
    private FirebaseAuth mFirebaseAuth;


    @Inject
    public UserRepository(FirebaseAuth auth) {
        this.mFirebaseAuth = auth;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return Transformations.map(new FirebaseAuthLiveData(mFirebaseAuth), new Function<FirebaseAuth, FirebaseUser>() {
            @Override
            public FirebaseUser apply(FirebaseAuth auth) {
                return auth.getCurrentUser();
            }
        });
    }

    public FirebaseUser getUserSync() {
        return mFirebaseAuth.getCurrentUser();
    }


}

package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.util.FirebaseAuthLiveData;

/**
 * A repository for Users
 * Allows the current FirebaseUser to be retrieved
 */

public class UserRepository {
    private FirebaseAuth mFirebaseAuth;

    private static UserRepository sUserRepository;

    private UserRepository(FirebaseAuth auth) {
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

    public static UserRepository get() {
        if (sUserRepository == null) {
            //we could do this with dependency injection instead
            sUserRepository = new UserRepository(FirebaseAuth.getInstance());
        }
        return sUserRepository;
    }
}

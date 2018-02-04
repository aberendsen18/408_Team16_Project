package com.moufee.a14cup.repository;

import android.arch.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.util.FirebaseUserLiveData;

/**
 * A repository for Users
 * Allows the current FirebaseUser to be retrieved
 */

public class UserRepository {
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mFirebaseAuth;

    private static UserRepository sUserRepository;

    private UserRepository(FirebaseAuth auth) {
        this.mFirebaseAuth = auth;
        mCurrentUser = auth.getCurrentUser();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return new FirebaseUserLiveData(mFirebaseAuth);
    }

    public static UserRepository get() {
        if (sUserRepository == null) {
            //we could do this with dependency injection instead
            sUserRepository = new UserRepository(FirebaseAuth.getInstance());
        }
        return sUserRepository;
    }
}

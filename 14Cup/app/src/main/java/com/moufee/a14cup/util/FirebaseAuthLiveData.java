package com.moufee.a14cup.util;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A LiveData for Firebase Users
 */

public class FirebaseAuthLiveData extends LiveData<FirebaseAuth> {
    private final FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener = new MyListener();

    public FirebaseAuthLiveData(FirebaseAuth auth) {
        this.mFirebaseAuth = auth;
    }

    @Override
    protected void onActive() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onInactive() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    private class MyListener implements FirebaseAuth.AuthStateListener {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            setValue(firebaseAuth);
        }
    }
}

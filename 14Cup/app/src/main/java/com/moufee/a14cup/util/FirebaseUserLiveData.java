package com.moufee.a14cup.util;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ListenerRegistration;

/**
 * A LiveData for Firebase Users
 */

public class FirebaseUserLiveData extends LiveData<FirebaseUser> {
    private final FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener = new MyListener();
    private ListenerRegistration registration;

    public FirebaseUserLiveData(FirebaseAuth auth) {
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
            setValue(firebaseAuth.getCurrentUser());
        }
    }
}

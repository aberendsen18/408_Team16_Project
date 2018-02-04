package com.moufee.a14cup.util;

import android.arch.lifecycle.LiveData;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A LiveData for Firestore Queries
 */

public class FirestoreQueryLiveData extends LiveData<QuerySnapshot> {
    private final Query query;
    private EventListener<QuerySnapshot> listener = new MyListener();
    private ListenerRegistration registration;

    public FirestoreQueryLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        registration = query.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        if (registration != null)
            registration.remove();
    }

    private class MyListener implements EventListener<QuerySnapshot> {
        @Override
        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
            setValue(documentSnapshots);
        }
    }
}

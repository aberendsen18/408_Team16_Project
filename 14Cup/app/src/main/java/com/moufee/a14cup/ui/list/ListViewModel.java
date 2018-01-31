package com.moufee.a14cup.ui.list;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.util.FirestoreQueryLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * A ViewModel for ListActivity
 */

public class ListViewModel extends ViewModel {
    private static final CollectionReference LISTS_REF =
            FirebaseFirestore.getInstance().collection("lists");

    private final FirestoreQueryLiveData liveData = new FirestoreQueryLiveData(LISTS_REF);

    private final LiveData<List<ShoppingList>> lists = Transformations.map(liveData, new Function<QuerySnapshot, List<ShoppingList>>() {
        @Override
        public List<ShoppingList> apply(QuerySnapshot input) {
            List<ShoppingList> lists = new ArrayList<>();
            if (input != null)
                for (DocumentSnapshot doc : input) {
                    if (doc.get("name") != null) {
                        lists.add(doc.toObject(ShoppingList.class));
                    }
                }
            return lists;
        }
    });


    public FirestoreQueryLiveData getLiveData() {
        return liveData;
    }

    public LiveData<List<ShoppingList>> getLists() {
        return lists;
    }
}

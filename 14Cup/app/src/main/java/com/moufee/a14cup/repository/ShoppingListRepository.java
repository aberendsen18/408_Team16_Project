package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.util.FirestoreQueryLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * A repository for ShoppingLists
 */

public class ShoppingListRepository {
    private final CollectionReference listsCollection;

    private static ShoppingListRepository sShoppingListRepository;

    public ShoppingListRepository(CollectionReference listsCollection) {
        this.listsCollection = listsCollection;
    }

    public static ShoppingListRepository get() {
        if (sShoppingListRepository == null)
            sShoppingListRepository = new ShoppingListRepository(FirebaseFirestore.getInstance().collection("lists"));
        return sShoppingListRepository;
    }

    public LiveData<List<ShoppingList>> getShoppingLists() {
        return Transformations.map(new FirestoreQueryLiveData(listsCollection), new Function<QuerySnapshot, List<ShoppingList>>() {
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
    }
}

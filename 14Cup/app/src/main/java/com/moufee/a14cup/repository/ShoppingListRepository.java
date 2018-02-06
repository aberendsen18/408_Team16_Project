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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository for ShoppingLists
 */
@Singleton
public class ShoppingListRepository {
    private final CollectionReference listsCollection;


    @Inject
    public ShoppingListRepository(FirebaseFirestore firebaseFirestore) {
        this.listsCollection = firebaseFirestore.collection("lists");
    }



    public LiveData<Map<String, ShoppingList>> getShoppingListsHashMap()

    {
        return Transformations.map(new FirestoreQueryLiveData(listsCollection), new Function<QuerySnapshot, Map<String, ShoppingList>>() {
            @Override
            public Map<String, ShoppingList> apply(QuerySnapshot input) {
                Map<String, ShoppingList> lists = new HashMap<>();
                if (input != null)
                    for (DocumentSnapshot doc : input) {
                        if (doc.get("name") != null) {
                            ShoppingList list = doc.toObject(ShoppingList.class);
                            list.id = doc.getId();
                            lists.put(doc.getId(), list);
                        }
                    }
                return lists;
            }
        });
    }

    public LiveData<List<ShoppingList>> getShoppingLists() {
        return Transformations.map(new FirestoreQueryLiveData(listsCollection), new Function<QuerySnapshot, List<ShoppingList>>() {
            @Override
            public List<ShoppingList> apply(QuerySnapshot input) {
                List<ShoppingList> lists = new ArrayList<>();
                if (input != null)
                    for (DocumentSnapshot doc : input) {
                        if (doc.get("name") != null) {
                            ShoppingList list = doc.toObject(ShoppingList.class);
                            list.id = doc.getId();
                            lists.add(list);
                        }
                    }
                return lists;
            }
        });
    }
}

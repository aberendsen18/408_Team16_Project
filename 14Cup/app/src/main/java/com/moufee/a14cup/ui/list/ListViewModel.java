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
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.util.FirestoreQueryLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * A ViewModel for MainActivity
 */

public class ListViewModel extends ViewModel {
    private static final CollectionReference LISTS_REF =
            FirebaseFirestore.getInstance().collection("lists");
    private static final ShoppingListRepository sShoppingListRepository = ShoppingListRepository.get();


    public LiveData<List<ShoppingList>> getLists() {
        return sShoppingListRepository.getShoppingLists();
    }
}

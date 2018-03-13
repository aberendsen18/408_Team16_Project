package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.util.FirestoreDocumentLiveData;
import com.moufee.a14cup.util.FirestoreQueryLiveData;

import java.util.ArrayList;
import java.util.List;

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

    public LiveData<List<ShoppingList>> getShoppingLists(String userID) {
        return Transformations.map(new FirestoreQueryLiveData(listsCollection.whereEqualTo("owner", userID)), new Function<QuerySnapshot, List<ShoppingList>>() {
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

    public LiveData<List<ShoppingListItem>> getItemsForList(String listID) {
        return Transformations.map(new FirestoreQueryLiveData(listsCollection.document(listID).collection("items")), new Function<QuerySnapshot, List<ShoppingListItem>>() {
            @Override
            public List<ShoppingListItem> apply(QuerySnapshot input) {
                List<ShoppingListItem> items = new ArrayList<>();
                if (input != null)
                    for (DocumentSnapshot doc : input) {
                        if (doc.get("name") != null) {
                            ShoppingListItem item = doc.toObject(ShoppingListItem.class);
                            item.id = doc.getId();
                            items.add(item);
                        }
                    }
                return items;
            }
        });
    }

    public LiveData<ShoppingList> getList(String listID) {
        return Transformations.map(new FirestoreDocumentLiveData(listsCollection.document(listID)), new Function<DocumentSnapshot, ShoppingList>() {
            @Override
            public ShoppingList apply(DocumentSnapshot input) {
                if (input == null || !input.exists()) return null;
                ShoppingList list = input.toObject(ShoppingList.class);
                list.id = input.getId();
                return list;
            }
        });
    }

    public void addList(ShoppingList list) {
        listsCollection.add(list);
    }

    public void updateList(ShoppingList list) {
        listsCollection.document(list.id).set(list);
    }

    public void addItem(String listID, ShoppingListItem item) {
        listsCollection.document(listID).collection("items").add(item);
    }

    public void updateItem(String listID, ShoppingListItem item) {
        listsCollection.document(listID).collection("items").document(item.id).set(item);
    }

    public void deleteItem(String listID, String itemID) {
        listsCollection.document(listID).collection("items").document(itemID).delete();
    }

    public void deleteList(String listID) {
        listsCollection.document(listID).delete();
    }
}

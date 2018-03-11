package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.util.FirestoreDocumentLiveData;
import com.moufee.a14cup.util.FirestoreQueryLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Travis Kovacic on 3/2/2018.
 */

@Singleton
public class CategoryRepository {

    private final CollectionReference categorySortCollection;


    @Inject
    public CategoryRepository(FirebaseFirestore firebaseFirestore) {
        this.categorySortCollection = firebaseFirestore.collection("sortOrders");
    }


    public LiveData<List<CategorySortOrder>> getSortOrders(String userID) {
        return Transformations.map(new FirestoreQueryLiveData(categorySortCollection), new Function<QuerySnapshot, List<CategorySortOrder>>() {
            @Override
            public List<CategorySortOrder> apply(QuerySnapshot input) {
                List<CategorySortOrder> result = new ArrayList<>();
                if (input != null)
                    for (DocumentSnapshot doc : input) {
                        if (doc.get("name") != null) {
                            CategorySortOrder order = doc.toObject(CategorySortOrder.class);
                            order.id = doc.getId();
                            result.add(order);
                        }
                    }
                return result;
            }
        });
    }

    public LiveData<Map<String, CategorySortOrder>> getSortOrdersMap(String userID) {
        return Transformations.map(new FirestoreQueryLiveData(categorySortCollection), new Function<QuerySnapshot, Map<String, CategorySortOrder>>() {
            @Override
            public Map<String, CategorySortOrder> apply(QuerySnapshot input) {
                Map<String, CategorySortOrder> result = new HashMap<>();
                for (DocumentSnapshot doc : input) {
                    if (doc.get("name") != null) {
                        CategorySortOrder order = doc.toObject(CategorySortOrder.class);
                        order.id = doc.getId();
                        result.put(doc.getId(), order);
                    }
                }
                return result;
            }
        });
    }

    public LiveData<CategorySortOrder> getSortOrder(String sortOrderID) {
        return Transformations.map(new FirestoreDocumentLiveData(categorySortCollection.document(sortOrderID)), new Function<DocumentSnapshot, CategorySortOrder>() {
            @Override
            public CategorySortOrder apply(DocumentSnapshot input) {
                CategorySortOrder order = input.toObject(CategorySortOrder.class);
                order.id = input.getId();
                return order;
            }
        });
    }

    public void updateSortOrder(CategorySortOrder sortOrder) {
        categorySortCollection.document(sortOrder.id).set(sortOrder);
    }

    public void addSortOrder(CategorySortOrder sortOrder) {
        categorySortCollection.add(sortOrder);
    }

    public void deleteSortOrder(CategorySortOrder sortOrder) {
        categorySortCollection.document(sortOrder.id).delete();
    }


}

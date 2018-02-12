package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import javax.inject.Inject;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by tyler on 2/11/18.
 */

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ShoppingListRepositoryTest {

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;
    private ShoppingListRepository mShoppingListRepository;
    private DocumentReference mDocumentReference;
    private UserRepository mUserRepository;


    @Before
    public void setup(){
        mCollectionReference = mock(CollectionReference.class);
        //mUserRepository = mock(UserRepository.class);
        mFirebaseFirestore = mock(FirebaseFirestore.class);
        when(mFirebaseFirestore.collection("lists")).thenReturn(mCollectionReference);
        mShoppingListRepository = new ShoppingListRepository(mFirebaseFirestore);
    }

    @Test
    public void createNewShoppingList() throws Exception{
        // add new shopping list
        ShoppingList TestList = new ShoppingList("test_name","test_user" );

        // verfiy shopping addList calls add method of Firebase CollectionReference
        mShoppingListRepository.addList(TestList);
        verify(mCollectionReference).add(TestList);
    }

    @Test
    public void addItemToShoppingList() throws Exception{

        ShoppingListItem testItem = new ShoppingListItem();
        testItem.name = "test_item";
        testItem.category = "test_cat";

        DocumentReference documentReference = mock(DocumentReference.class);
        CollectionReference collectionReference = mock(CollectionReference.class);
        when(mCollectionReference.document(anyString())).thenReturn(documentReference);
        when(documentReference.collection("items")).thenReturn(collectionReference);

        mShoppingListRepository.addItem("test_id", testItem);
        verify(mCollectionReference).document("test_id");
        verify(collectionReference).add(testItem);


    }
}

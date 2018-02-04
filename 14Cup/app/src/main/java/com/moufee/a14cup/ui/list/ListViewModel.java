package com.moufee.a14cup.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;

import java.util.List;

/**
 * A ViewModel for MainActivity
 */

public class ListViewModel extends ViewModel {

    private final ShoppingListRepository mShoppingListRepository = ShoppingListRepository.get();
    private final UserRepository mUserRepository = UserRepository.get();
    private LiveData<List<ShoppingList>> mListLiveData;
    private LiveData<FirebaseUser> mCurrentUser;

    public ListViewModel() {
        mListLiveData = mShoppingListRepository.getShoppingLists();
        mCurrentUser = mUserRepository.getCurrentUser();
    }

    public LiveData<List<ShoppingList>> getLists() {
        return mListLiveData;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return mCurrentUser;
    }
}

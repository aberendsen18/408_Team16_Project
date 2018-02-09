package com.moufee.a14cup.ui.list;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * A ViewModel for MainActivity
 */

public class ListViewModel extends ViewModel {

    private ShoppingListRepository mShoppingListRepository;
    private UserRepository mUserRepository;
    private LiveData<List<ShoppingList>> mListLiveData;
    private LiveData<FirebaseUser> mCurrentUser;
    private MutableLiveData<String> mSelectedListID = new MutableLiveData<>();

    @Inject
    public ListViewModel(ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        mShoppingListRepository = shoppingListRepository;
        mUserRepository = userRepository;
        mCurrentUser = mUserRepository.getCurrentUser();
        mListLiveData = Transformations.switchMap(mCurrentUser, new Function<FirebaseUser, LiveData<List<ShoppingList>>>() {
            @Override
            public LiveData<List<ShoppingList>> apply(FirebaseUser input) {
                if (input == null)
                    return null;
                return mShoppingListRepository.getShoppingLists(input.getUid());
            }
        });
    }

    public LiveData<List<ShoppingList>> getLists() {
        return mListLiveData;
    }

    public LiveData<Map<String, ShoppingList>> getHashLists() {
        return mShoppingListRepository.getShoppingListsHashMap();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return mCurrentUser;
    }

    public void setSelectedListID(String ID) {
        mSelectedListID.setValue(ID);
    }

}

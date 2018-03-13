package com.moufee.a14cup.ui.list;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.repository.CategoryRepository;
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
    private CategoryRepository mCategoryRepository;
    private LiveData<List<ShoppingList>> mListLiveData;
    private LiveData<FirebaseUser> mCurrentUser;
    private MutableLiveData<String> mSelectedListID = new MutableLiveData<>();
    private LiveData<List<ShoppingListItem>> mCurrentListItems;
    private LiveData<List<CategorySortOrder>> mSortOrders;
    private LiveData<ShoppingList> mSelectedList;
    private LiveData<Map<String, CategorySortOrder>> mSortOrdersMap;
    private LiveData<CategorySortOrder> mCurrentSortOrder;

    @Inject
    public ListViewModel(ShoppingListRepository shoppingListRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        mShoppingListRepository = shoppingListRepository;
        mCategoryRepository = categoryRepository;
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
        mCurrentListItems = Transformations.switchMap(mSelectedListID, new Function<String, LiveData<List<ShoppingListItem>>>() {
            @Override
            public LiveData<List<ShoppingListItem>> apply(String input) {
                if (input == null)
                    return null;
                return mShoppingListRepository.getItemsForList(input);
            }
        });
        mSelectedList = Transformations.switchMap(mSelectedListID, new Function<String, LiveData<ShoppingList>>() {
            @Override
            public LiveData<ShoppingList> apply(String input) {
                return mShoppingListRepository.getList(input);
            }
        });
        mSortOrders = Transformations.switchMap(mCurrentUser, new Function<FirebaseUser, LiveData<List<CategorySortOrder>>>() {
            @Override
            public LiveData<List<CategorySortOrder>> apply(FirebaseUser input) {
                if (input == null) return null;
                return mCategoryRepository.getSortOrders(input.getUid());
            }
        });
        mCurrentSortOrder = Transformations.switchMap(mSelectedList, new Function<ShoppingList, LiveData<CategorySortOrder>>() {
            @Override
            public LiveData<CategorySortOrder> apply(ShoppingList input) {
                if (input == null)
                    return null;
                String sortID = input.sortOrders.get(mCurrentUser.getValue().getUid());
                if (sortID == null) return null;
                return mCategoryRepository.getSortOrder(sortID);
            }
        });

    }

    public LiveData<CategorySortOrder> getCurrentSortOrder() {
        return mCurrentSortOrder;
    }

    public LiveData<List<CategorySortOrder>> getSortOrders() {
        return mSortOrders;
    }

    public LiveData<List<ShoppingList>> getLists() {
        return mListLiveData;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return mCurrentUser;
    }

    public LiveData<ShoppingList> getSelectedList() {
        return mSelectedList;
    }

    public void setSelectedListID(String ID) {
        mSelectedListID.setValue(ID);
    }

    public LiveData<List<ShoppingListItem>> getCurrentListItems() {
        return mCurrentListItems;
    }

    public LiveData<String> getSelectedListID() {
        return mSelectedListID;
    }
}

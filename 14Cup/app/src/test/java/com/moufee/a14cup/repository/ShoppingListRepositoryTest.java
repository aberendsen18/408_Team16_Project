package com.moufee.a14cup.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
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

/**
 * Created by tyler on 2/11/18.
 */

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ShoppingListRepositoryTest {

    @Inject
    ShoppingListRepository mShoppingListRepository;

    @Inject
    UserRepository mUserRepository;

    private LiveData<List<ShoppingList>> mListLiveData;
    private LiveData<FirebaseUser> mCurrentUser;
    private MutableLiveData<String> mSelectedListID = new MutableLiveData<>();
    private LiveData<List<ShoppingListItem>> mCurrentListItems;

    public LiveData<List<ShoppingList>> getLists() {
        return mListLiveData;
    }

    @Before
    public void setup(){
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
    }

    @Test
    public void createNewShoppingList() throws Exception{
        String TestName = "New List";
        ShoppingList TestList = new ShoppingList();
        TestList.name = TestName;
        TestList.owner = mUserRepository.getCurrentUser().getValue().getUid();

        mShoppingListRepository.addList(TestList);

        assertEquals( (getLists().getValue().contains(TestList)), true);
    }

    @Test
    public void addItemToShoppingList() throws Exception{


    }
}

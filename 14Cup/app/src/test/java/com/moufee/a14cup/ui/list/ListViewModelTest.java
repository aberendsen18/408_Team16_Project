package com.moufee.a14cup.ui.list;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;
import com.moufee.a14cup.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Ben on 2/10/18.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ListViewModelTest {

    private ListViewModel mListViewModel;
    private UserRepository mUserRepository;
    private ShoppingListRepository mListRepository;
    private FirebaseUser mFirebaseUser;
    private MutableLiveData<FirebaseUser> mFirebaseUserLiveData;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() {
        mFirebaseUser = mock(FirebaseUser.class);
        mUserRepository = mock(UserRepository.class);
        mListRepository = mock(ShoppingListRepository.class);
        mFirebaseUserLiveData = new MutableLiveData<>();

        when(mFirebaseUser.getUid()).thenReturn("foo");
        when(mUserRepository.getCurrentUser()).thenReturn(mFirebaseUserLiveData);

        mListViewModel = new ListViewModel(mListRepository, mUserRepository);
    }

    @Test
    public void notNull() {
        assertThat(mListViewModel.getLists(), notNullValue());
        assertThat(mListViewModel.getCurrentListItems(), notNullValue());
        assertThat(mListViewModel.getSelectedListID(), notNullValue());
        assertThat(mListViewModel.getCurrentUser(), notNullValue());
    }


    @Test
    public void testSelectList() {
        mListViewModel.getCurrentListItems().observeForever(mock(Observer.class));
        mListViewModel.setSelectedListID("foo");
        verify(mListRepository).getItemsForList("foo");
    }

    @Test
    public void sendResultToUI() {
        MutableLiveData<List<ShoppingList>> listsLiveData = new MutableLiveData<>();
        when(mListRepository.getShoppingLists(anyString())).thenReturn(listsLiveData);
        mListViewModel = new ListViewModel(mListRepository, mUserRepository);
        LiveData<List<ShoppingList>> resultLists = mListViewModel.getLists();
        Observer<List<ShoppingList>> listObserver = mock(Observer.class);
        resultLists.observeForever(listObserver);
        mFirebaseUserLiveData.setValue(mFirebaseUser);
        verify(listObserver, never()).onChanged(any(List.class));
        List<ShoppingList> lists = TestUtil.createLists("A List", "foo", 5);
        listsLiveData.setValue(lists);
        verify(listObserver).onChanged(lists);
    }


}

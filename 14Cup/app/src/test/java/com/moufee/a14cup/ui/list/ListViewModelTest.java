package com.moufee.a14cup.ui.list;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.repository.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Created by Ben on 2/10/18.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ListViewModelTest {

    private ListViewModel mListViewModel;
    private UserRepository mUserRepository;
    private ShoppingListRepository mListRepository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() {
        mUserRepository = mock(UserRepository.class);
        mListRepository = mock(ShoppingListRepository.class);
        mListViewModel = new ListViewModel(mListRepository, mUserRepository);
    }

    @Test
    public void notNull() {
        assertThat(mListViewModel.getLists(), notNullValue());
        assertThat(mListViewModel.getCurrentListItems(), notNullValue());
        assertThat(mListViewModel.getSelectedListID(), notNullValue());
    }


    @Test
    public void testSelectList() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        mListViewModel.getCurrentListItems().observeForever(mock(Observer.class));
        mListViewModel.setSelectedListID("foo");
        verify(mListRepository).getItemsForList("foo");
    }


}

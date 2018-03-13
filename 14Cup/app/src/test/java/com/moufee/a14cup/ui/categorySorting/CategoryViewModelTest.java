package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.repository.CategoryRepository;
import com.moufee.a14cup.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Travis Kovacic on 3/4/2018.
 * Tests for the {@link CategorySortViewModel}
 */

public class CategoryViewModelTest {

    private CategorySortViewModel cViewModel;
    private CategoryRepository cRepository;
    private UserRepository mUserRepository;
    private FirebaseUser mFirebaseUser;
    private MutableLiveData<FirebaseUser> mFirebaseUserLiveData;

    @Before
    public void setup() {
        mFirebaseUser = mock(FirebaseUser.class);
        mUserRepository = mock(UserRepository.class);
        cRepository = mock(CategoryRepository.class);
        mFirebaseUserLiveData = new MutableLiveData<>();

        when(mFirebaseUser.getUid()).thenReturn("foo");
        when(mUserRepository.getCurrentUser()).thenReturn(mFirebaseUserLiveData);

        cViewModel = new CategorySortViewModel(cRepository, mUserRepository);
    }

    @Test
    public void notNull() throws Exception {
        assertThat(cViewModel.getSortOrders(), notNullValue());
        assertThat(cViewModel.getCurrentSort(), notNullValue());
    }

}

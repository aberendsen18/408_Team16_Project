package com.moufee.a14cup.ui.categorySorting;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.repository.CategoryRepository;
import com.moufee.a14cup.repository.UserRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Travis Kovacic on 2/16/2018.
 */

public class CategorySortViewModel extends ViewModel {

    private LiveData<List<CategorySortOrder>> mSortOrders;
    private LiveData<Map<String, CategorySortOrder>> mSortOrdersMap;
    private MutableLiveData<CategorySortOrder> mCurrentSort;
    private MutableLiveData<String> mCurrentSortID = new MutableLiveData<>();
    private CategoryRepository mCategoryRepository;
    private UserRepository mUserRepository;


    @Inject
    public CategorySortViewModel(final CategoryRepository categoryRepository, UserRepository userRepository) {
        this.mUserRepository = userRepository;
        this.mCategoryRepository = categoryRepository;
        mSortOrders = Transformations.switchMap(mUserRepository.getCurrentUser(), new Function<FirebaseUser, LiveData<List<CategorySortOrder>>>() {
            @Override
            public LiveData<List<CategorySortOrder>> apply(FirebaseUser input) {
                return mCategoryRepository.getSortOrders(input.getUid());
            }
        });
        mSortOrdersMap = Transformations.switchMap(mUserRepository.getCurrentUser(), new Function<FirebaseUser, LiveData<Map<String, CategorySortOrder>>>() {
            @Override
            public LiveData<Map<String, CategorySortOrder>> apply(FirebaseUser input) {
                return mCategoryRepository.getSortOrdersMap(input.getUid());
            }
        });
        mCurrentSort = new MutableLiveData<>();
    }


    public LiveData<List<CategorySortOrder>> getSortOrders() {
        return mSortOrders;
    }

    public LiveData<CategorySortOrder> getCurrentSort() {
        return mCurrentSort;
    }

    public void setCurrentSort(CategorySortOrder order) {
        mCurrentSort.setValue(order);
    }
}

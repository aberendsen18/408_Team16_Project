package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.moufee.a14cup.R;
import com.moufee.a14cup.SingleFragmentActivity;
import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.repository.CategoryRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Ben on 2/25/18.
 */

public class CategorySortActivity extends SingleFragmentActivity implements HasSupportFragmentInjector, CategorySortListFragment.OnCategorySortListInteractionListener {

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;

    @Inject
    CategoryRepository cListRepository;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private CategorySortListViewModel viewModel;

    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, CategorySortActivity.class);
    }

    @Override
    protected Fragment createFragment() {

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(CategorySortListViewModel.class);

        return CategorySortListFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSelectSortList(CategorySortList list) {
        viewModel.CurrentSort = list;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.single_fragment_container, CategorySortFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }
}

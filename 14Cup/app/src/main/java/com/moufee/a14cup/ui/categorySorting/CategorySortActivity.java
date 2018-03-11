package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.moufee.a14cup.R;
import com.moufee.a14cup.SingleFragmentActivity;
import com.moufee.a14cup.categorySorts.CategorySortOrder;

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
    ViewModelProvider.Factory mViewModelFactory;

    private CategorySortViewModel viewModel;
    private ActionBar mActionBar;

    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, CategorySortActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return CategorySortListFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(CategorySortViewModel.class);
        mActionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Manage Category Sorting");
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().findFragmentById(R.id.single_fragment_container) instanceof CategorySortListFragment)
                    mActionBar.setTitle("Manage Category Sorting");
            }
        });
    }

    @Override
    public void onSelectSortList(CategorySortOrder order) {
        viewModel.setCurrentSort(order.id);
        getSupportActionBar().setTitle(order.name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.single_fragment_container, CategorySortFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            if (getSupportFragmentManager().findFragmentById(R.id.single_fragment_container) instanceof CategorySortFragment) {
                getSupportFragmentManager().popBackStack();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }
}

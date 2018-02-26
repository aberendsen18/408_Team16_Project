package com.moufee.a14cup.ui.categorySorting;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.moufee.a14cup.SingleFragmentActivity;

/**
 * Created by Ben on 2/25/18.
 */

public class CategorySortActivity extends SingleFragmentActivity {

    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, CategorySortActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return CategorySortListFragment.newInstance();
    }
}

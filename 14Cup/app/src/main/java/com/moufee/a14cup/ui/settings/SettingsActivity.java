package com.moufee.a14cup.ui.settings;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.moufee.a14cup.R;
import com.moufee.a14cup.ui.categorySorting.CategorySortActivity;

public class SettingsActivity extends AppCompatActivity implements SettingsFragment.PreferenceInteractionListener {

    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        android.app.FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);

        if (fragment == null) {
            fm.beginTransaction()
                    .replace(R.id.single_fragment_container, SettingsFragment.getInstance())
                    .commit();
        }
    }

    @Override
    public void onChooseCategorySortPreferences() {
        startActivity(CategorySortActivity.getIntent(getApplicationContext()));
    }
}

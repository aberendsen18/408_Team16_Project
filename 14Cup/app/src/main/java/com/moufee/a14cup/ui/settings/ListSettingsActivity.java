package com.moufee.a14cup.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.moufee.a14cup.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 * <p>
 * Not currently in use.
 * Opted for the simpler approach of just including list settings in the main options menu
 */
public class ListSettingsActivity extends AppCompatActivity {

    private static final String KEY_LIST_ID = "list_id";
    private String mListID;

    public static Intent getIntent(Context packageContext, String listID) {
        Intent intent = new Intent(packageContext, ListSettingsActivity.class);
        intent.putExtra(KEY_LIST_ID, listID);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_settings);
        Intent intent = getIntent();
        mListID = intent.getStringExtra(KEY_LIST_ID);
        setupActionBar();
    }


    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || SharingPreferenceFragment.class.getName().equals(fragmentName)
                || SortingPreferenceFragment.class.getName().equals(fragmentName);
    }

}

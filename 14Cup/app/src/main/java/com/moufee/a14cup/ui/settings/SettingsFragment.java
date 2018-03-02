package com.moufee.a14cup.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.moufee.a14cup.R;

/**
 * Created by Ben on 2/20/18.
 */

public class SettingsFragment extends PreferenceFragment {
    private PreferenceInteractionListener mListener;

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_prefs);
        CharSequence key = getResources().getString(R.string.pref_manage_category_sort_key);
        Preference categorySortPreference = findPreference(key);
        categorySortPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mListener.onChooseCategorySortPreferences();
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        Log.d("ATTACH", "onAttach: ");
        super.onAttach(context);
        if (context instanceof PreferenceInteractionListener) {
            mListener = (PreferenceInteractionListener) context;
            Log.d("SETTINGS_FRAGMENT", "onAttach: setting listener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttach((Context) activity);
    }

    public interface PreferenceInteractionListener {
        void onChooseCategorySortPreferences();
    }
}

package com.moufee.a14cup.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.moufee.a14cup.R;

/**
 * Created by Ben on 3/10/18.
 */

public class SortingPreferenceFragment extends PreferenceFragment {

    private static final String KEY_LIST_ID = "list_id";

    public SortingPreferenceFragment() {
    }

    public static SortingPreferenceFragment newInstance(String listID) {
        SortingPreferenceFragment fragment = new SortingPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(KEY_LIST_ID, listID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorting_preference, container, false);
        ArrayAdapter<String> sortSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        sortSpinnerAdapter.addAll("Walmart", "Another Sort Order");
        Spinner spinner = view.findViewById(R.id.sortOrderSelector);
        spinner.setAdapter(sortSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result = (String) parent.getItemAtPosition(position);
                Log.d("SELECTED", "onItemSelected: " + result);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}

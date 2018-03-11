package com.moufee.a14cup.ui.settings;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.ui.common.StringDiffUtilCallback;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link PreferenceFragment} subclass for displaying list sharing settings.
 * Use the {@link SharingPreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharingPreferenceFragment extends PreferenceFragment {

    private SharedUserAdapter mUserAdapter;
    private static final String KEY_LIST_ID = "list_id";
    private String mListID = "";


    public SharingPreferenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SharingPreferenceFragment.
     */
    public static SharingPreferenceFragment newInstance(String listID) {
        SharingPreferenceFragment fragment = new SharingPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(KEY_LIST_ID, listID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mListID = getArguments().getString(KEY_LIST_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sharing_preference, container, false);
        RecyclerView sharingRecyclerView = view.findViewById(R.id.sharedPeopleListView);
        sharingRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mUserAdapter = new SharedUserAdapter(new StringDiffUtilCallback());
        sharingRecyclerView.setAdapter(mUserAdapter);
        List<String> mockUsers = Arrays.asList("ben@test.com", "test@test.com");
        mUserAdapter.submitList(mockUsers);


        return view;
    }

}

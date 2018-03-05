package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Travis Kovacic on 2/12/2018.
 * This fragment displays the list of category sort orders
 * The use can then select a sort order to edit it
 */

public class CategorySortListFragment extends Fragment {

    @Inject
    CategoryRepository cListRepository;

    @Inject
    ViewModelProvider.Factory mFactory;

    private OnCategorySortListInteractionListener mListener;
    private CategorySortViewModel mViewModel;
    private RecyclerView recyclerView;
    private CategorySortListRecyclerViewAdapter recyclerViewAdapter;

    public CategorySortListFragment() {
    }

    public static CategorySortListFragment newInstance() {
        return new CategorySortListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(), mFactory).get(CategorySortViewModel.class);
        recyclerViewAdapter = new CategorySortListRecyclerViewAdapter(new ArrayList<CategorySortOrder>(), mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorysorting_title_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        return view;
    }

    private void setListeners() {
        // viewmodel
        mViewModel.getSortOrders().observe(this, new Observer<List<CategorySortOrder>>() {
            @Override
            public void onChanged(@Nullable List<CategorySortOrder> categorySortOrders) {
                if (categorySortOrders != null)
                    recyclerViewAdapter.setSortList(categorySortOrders);
                else
                    recyclerViewAdapter.setSortList(new ArrayList<CategorySortOrder>());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            mViewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(CategorySortViewModel.class);
            setListeners();
        }
        if (context instanceof OnCategorySortListInteractionListener) {
            mListener = (OnCategorySortListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategorySortListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCategorySortListInteractionListener {
        void onSelectSortList(CategorySortOrder order);
    }

}

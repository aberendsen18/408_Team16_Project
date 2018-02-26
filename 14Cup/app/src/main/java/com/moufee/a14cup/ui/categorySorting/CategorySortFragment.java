package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.SortCategory;

import java.util.ArrayList;

/**
 * Created by Travis Kovacic on 2/16/2018.
 */

public class CategorySortFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private CategorySortViewModel viewModel;
    private RecyclerView recyclerView;
    private CategorySortRecyclerViewAdapter recyclerViewAdapter;

    public CategorySortFragment(){
    }
    public static CategorySortFragment newInstance() {
        CategorySortFragment fragment = new CategorySortFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CategorySortViewModel.class);
        recyclerViewAdapter = new CategorySortRecyclerViewAdapter(new ArrayList<SortCategory>(), mListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorysorting_sort_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        setListeners();
        return view;
    }

    private void setListeners() {
        // viewmodel
        ArrayList<SortCategory> categories = viewModel.getCategories();
        if(categories != null){
            recyclerViewAdapter.setCategories(categories);
        } else {
            recyclerViewAdapter.setCategories(new ArrayList<SortCategory>());
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSortCategoryFragmentInteraction(SortCategory category);
    }

}

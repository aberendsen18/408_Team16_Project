package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.CategorySortingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortingListFragment extends Fragment{

    private OnListFragmentInteractionListener mListener;
    private SortListViewModel viewModel;
    private RecyclerView recyclerView;
    private CategorySortingRecyclerViewAdapter recyclerViewAdapter;

    public CategorySortingListFragment(){
    }

    public static  CategorySortingListFragment newInstance() { return new CategorySortingListFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SortListViewModel.class);
        recyclerViewAdapter = new CategorySortingRecyclerViewAdapter(new ArrayList<CategorySortingList>(), mListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylists_list, container, false);

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
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof CategorySortingListFragment.OnListFragmentInteractionListener) {
            mListener = (CategorySortingListFragment.OnListFragmentInteractionListener) context;
        } else {
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
        void onListFragmentInteraction(CategorySortingList list);
    }

}

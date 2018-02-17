package com.moufee.a14cup.ui.categorySorting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.categorySorts.CategorySortingList;
import com.moufee.a14cup.databinding.FragmentCategorysortingTitleBinding;
import com.moufee.a14cup.ui.categorySorting.CategorySortListFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<CategorySortingList> lists;
    private final OnListFragmentInteractionListener mListener;

    public CategorySortListRecyclerViewAdapter(ArrayList<CategorySortingList> lists, OnListFragmentInteractionListener listener) {
        this.lists = lists;
        mListener = listener;
    }

    public void setSortList(ArrayList<CategorySortingList> lists) {
        this.lists = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentCategorysortingTitleBinding binding = FragmentCategorysortingTitleBinding.inflate(inflater, parent, false);
        return new CategoryTitleHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((CategoryTitleHolder) holder).bind(lists.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

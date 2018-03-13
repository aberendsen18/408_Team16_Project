package com.moufee.a14cup.ui.categorySorting;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.databinding.FragmentCategorysortingTitleBinding;

import java.util.List;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortListRecyclerViewAdapter extends ListAdapter<CategorySortOrder, CategoryTitleHolder> {
    private final CategorySortListFragment.OnCategorySortListInteractionListener mListener;

    public CategorySortListRecyclerViewAdapter(CategorySortListFragment.OnCategorySortListInteractionListener listener) {
        super(CategorySortOrder.DIFF_CALLBACK);
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryTitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentCategorysortingTitleBinding binding = FragmentCategorysortingTitleBinding.inflate(inflater, parent, false);
        return new CategoryTitleHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryTitleHolder holder, int position) {
        holder.bind(getItem(position), mListener);
    }
}

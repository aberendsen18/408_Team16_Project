package com.moufee.a14cup.ui.categorySorting;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.databinding.FragmentCategorysortingSortBinding;

import java.util.List;

/**
 * Created by Travis Kovacic on 2/16/2018.
 */

public class CategorySortRecyclerViewAdapter extends ListAdapter<String, CategorySortHolder> {

    protected CategorySortRecyclerViewAdapter() {
        super(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(String oldItem, String newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(String oldItem, String newItem) {
                return true;
            }
        });
    }

    @Override
    public CategorySortHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentCategorysortingSortBinding binding = FragmentCategorysortingSortBinding.inflate(inflater, parent, false);
        return new CategorySortHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySortHolder holder, int position) {
        holder.bind(getItem(position));
    }
}

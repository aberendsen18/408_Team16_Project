package com.moufee.a14cup.ui.categorySorting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.databinding.FragmentCategorysortingSortBinding;

import java.util.List;

/**
 * Created by Travis Kovacic on 2/16/2018.
 */

public class CategorySortRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> categories;

    public CategorySortRecyclerViewAdapter(List<String> categories) {
        this.categories = categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentCategorysortingSortBinding binding = FragmentCategorysortingSortBinding.inflate(inflater, parent, false);
        return new CategorySortHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((CategorySortHolder) holder).bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

package com.moufee.a14cup.ui.categorySorting;

import android.support.v7.widget.RecyclerView;

import com.moufee.a14cup.categorySorts.SortCategory;
import com.moufee.a14cup.databinding.FragmentCategorysortingSortBinding;

/**
 * Created by Travis Kovacic on 2/15/2018.
 */

public class CategorySortHolder extends RecyclerView.ViewHolder{

    public FragmentCategorysortingSortBinding mBinding;

    public CategorySortHolder( FragmentCategorysortingSortBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(SortCategory category) {
        mBinding.setCategorySortListCategory(category);
        mBinding.executePendingBindings();
    }

}

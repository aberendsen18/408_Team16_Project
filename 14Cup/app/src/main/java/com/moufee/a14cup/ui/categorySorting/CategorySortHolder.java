package com.moufee.a14cup.ui.categorySorting;

import android.support.v7.widget.RecyclerView;

import com.moufee.a14cup.categorySorts.CategorySortingListCategory;
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

    public void bind(CategorySortingListCategory category, CategorySortFragment.OnListFragmentInteractionListener listener) {
        mBinding.setCategorySortListCategory(category);
        mBinding.setListener1(listener);
        mBinding.executePendingBindings();
    }

}

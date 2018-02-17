package com.moufee.a14cup.ui.categorySorting;

import android.support.v7.widget.RecyclerView;

import com.moufee.a14cup.categorySorts.CategorySortingList;
import com.moufee.a14cup.databinding.FragmentCategorysortingTitleBinding;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategoryTitleHolder extends RecyclerView.ViewHolder{

    public FragmentCategorysortingTitleBinding mBinding;

    public CategoryTitleHolder(FragmentCategorysortingTitleBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(CategorySortingList list, CategorySortListFragment.OnListFragmentInteractionListener listener) {
        mBinding.setCategorySortTitle(list);
        mBinding.setListener(listener);
        mBinding.executePendingBindings();
    }

}

package com.moufee.a14cup.ui.categorySorting;

import android.support.v7.widget.RecyclerView;

import com.moufee.a14cup.categorySorts.CategorySortingList;
import com.moufee.a14cup.databinding.FragmentCategorysortingTitleBinding;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortHolder extends RecyclerView.ViewHolder{

    public FragmentCategorysortingTitleBinding mBinding;

    public CategorySortHolder(FragmentCategorysortingTitleBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(CategorySortingList list, CategorySortingListFragment.OnListFragmentInteractionListener listener) {
        mBinding.setCategorySortList(list);
        mBinding.setListener(listener);
        mBinding.executePendingBindings();
    }

}

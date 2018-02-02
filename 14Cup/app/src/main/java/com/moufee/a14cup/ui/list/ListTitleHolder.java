package com.moufee.a14cup.ui.list;

import android.support.v7.widget.RecyclerView;

import com.moufee.a14cup.databinding.FragmentListTitleBinding;
import com.moufee.a14cup.lists.ShoppingList;

/**
 * A ViewHolder for List Titles
 */

public class ListTitleHolder extends RecyclerView.ViewHolder {
    public FragmentListTitleBinding mBinding;

    public ListTitleHolder(FragmentListTitleBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(ShoppingList list) {
        mBinding.setShoppingList(list);
        mBinding.executePendingBindings();
    }
}

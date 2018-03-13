package com.moufee.a14cup.ui.list;

import android.support.v7.widget.RecyclerView;

import com.moufee.a14cup.databinding.FragmentShoppinglistitemBinding;
import com.moufee.a14cup.lists.ShoppingListItem;

/**
 * A ViewHolder for ShoppingListItems
 */

public class ListItemHolder extends RecyclerView.ViewHolder {

    public FragmentShoppinglistitemBinding mBinding;

    public ListItemHolder(FragmentShoppinglistitemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(ShoppingListItem item, OnListItemInteractionListener listener) {
        mBinding.setItem(item);
        mBinding.setListener(listener);
        mBinding.executePendingBindings();
    }
}

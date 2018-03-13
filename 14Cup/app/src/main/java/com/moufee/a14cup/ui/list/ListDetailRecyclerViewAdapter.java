package com.moufee.a14cup.ui.list;

import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.databinding.FragmentShoppinglistitemBinding;
import com.moufee.a14cup.lists.ShoppingListItem;

/**
 * Adapter for list items
 * TODO: Create an abstract version of this to reduce duplicated code
 */
public class ListDetailRecyclerViewAdapter extends ListAdapter<ShoppingListItem, ListItemHolder> {

    public ListDetailRecyclerViewAdapter(OnListItemInteractionListener listener) {
        super(ShoppingListItem.DIFF_CALLBACK);
        mListener = listener;
    }

    private final OnListItemInteractionListener mListener;


    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentShoppinglistitemBinding binding = FragmentShoppinglistitemBinding.inflate(inflater, parent, false);
        return new ListItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ListItemHolder holder, int position) {
        holder.bind(getItem(position), mListener);
    }
}

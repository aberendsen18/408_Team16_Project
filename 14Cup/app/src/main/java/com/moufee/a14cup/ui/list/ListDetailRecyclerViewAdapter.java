package com.moufee.a14cup.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.databinding.FragmentShoppinglistitemBinding;
import com.moufee.a14cup.lists.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for list items
 * TODO: Create an abstract version of this to reduce duplicated code
 */
public class ListDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShoppingListItem> items;
//    private final MyListsFragment.OnListFragmentInteractionListener mListener;

    public ListDetailRecyclerViewAdapter() {
        this.items = new ArrayList<>();

    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentShoppinglistitemBinding binding = FragmentShoppinglistitemBinding.inflate(inflater, parent, false);
        return new ListItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ListItemHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

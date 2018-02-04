package com.moufee.a14cup.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moufee.a14cup.databinding.FragmentListTitleBinding;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.ui.list.MyListsFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingList} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyListsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShoppingList> lists;
    private final OnListFragmentInteractionListener mListener;

    public MyListsRecyclerViewAdapter(List<ShoppingList> lists, OnListFragmentInteractionListener listener) {
        this.lists = lists;
        mListener = listener;
    }

    public void setLists(List<ShoppingList> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentListTitleBinding binding = FragmentListTitleBinding.inflate(inflater, parent, false);
        return new ListTitleHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ListTitleHolder) holder).bind(lists.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

package com.moufee.a14cup.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moufee.a14cup.R;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.ui.list.MyListsFragment.OnListFragmentInteractionListener;
import com.moufee.a14cup.ui.list.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingList} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyListsRecyclerViewAdapter extends RecyclerView.Adapter<MyListsRecyclerViewAdapter.ViewHolder> {

    private List<ShoppingList> lists;
    private final OnListFragmentInteractionListener mListener;

    public MyListsRecyclerViewAdapter(List<ShoppingList> lists, OnListFragmentInteractionListener listener) {
        this.lists = lists;
        mListener = listener;
    }

    public void setLists(List<ShoppingList> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mylists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = lists.get(position);
        holder.mIdView.setText("");
        holder.mContentView.setText(lists.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ShoppingList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
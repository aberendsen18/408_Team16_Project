package com.moufee.a14cup.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.lists.ShoppingListItem;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingListDetailFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private ListViewModel mViewModel;
    private ListDetailRecyclerViewAdapter mRecyclerViewAdapter = new ListDetailRecyclerViewAdapter();
    @Inject
    ViewModelProvider.Factory mFactory;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListDetailFragment() {
    }

    @SuppressWarnings("unused")
    public static ShoppingListDetailFragment newInstance() {
        ShoppingListDetailFragment fragment = new ShoppingListDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppinglistitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(mRecyclerViewAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            mViewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(ListViewModel.class);
            setListeners();
        }
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    private void setListeners() {
        mViewModel.getCurrentListItems().observe(this, new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingListItem> shoppingListItems) {
                if (shoppingListItems != null) {
                    mRecyclerViewAdapter.setItems(shoppingListItems);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ShoppingListItem item);
    }
}
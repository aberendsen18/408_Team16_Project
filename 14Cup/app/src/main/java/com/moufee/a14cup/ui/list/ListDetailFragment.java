package com.moufee.a14cup.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.moufee.a14cup.R;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.repository.ShoppingListRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListDetailFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private ListViewModel mViewModel;
    private ListDetailRecyclerViewAdapter mRecyclerViewAdapter = new ListDetailRecyclerViewAdapter();
    private RecyclerView mRecyclerView;
    @Inject
    ViewModelProvider.Factory mFactory;
    @Inject
    ShoppingListRepository mListRepository;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListDetailFragment() {
    }

    @SuppressWarnings("unused")
    public static ListDetailFragment newInstance() {
        ListDetailFragment fragment = new ListDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shoppinglistitem_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list_items_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        final TextView newItemEdit = view.findViewById(R.id.item_name_input);
        newItemEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    ShoppingListItem NewItem = new ShoppingListItem();
                    NewItem.name = newItemEdit.getText().toString();
                    mListRepository.addItem(mViewModel.getSelectedListID().getValue(), NewItem);
                    newItemEdit.setText("");
                    return true;
                }
                return false;
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (swipeDir == ItemTouchHelper.RIGHT || swipeDir == ItemTouchHelper.LEFT) {
                    //int itemID = viewHolder.getItemId();
                    String itemID = mRecyclerViewAdapter.getItems().get(viewHolder.getAdapterPosition()).id;
                    String listID = mViewModel.getSelectedListID().getValue();
                    mListRepository.deleteItem(listID , itemID);
                }
                //TODO ALTERNATIVE COLOR CHECK OFF HAS ISSUES
                /*else if (swipeDir == ItemTouchHelper.LEFT) {
                    mRecyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    ColorDrawable bc = (ColorDrawable) viewHolder.itemView.getBackground();
                    if (bc == null) {
                        //viewHolder.itemView.setBackgroundColor(Color.GREEN);

                        viewHolder.itemView.setBackgroundColor(Color.GREEN);
                        //(viewHolder.getOldPosition()).setBackgroundColor(Color.GREEN);
                        //viewHolder.itemView.setSelected(true);
                    }
                    else {
                        if (bc.getColor() == Color.GREEN) {
                            viewHolder.itemView.setBackgroundResource(0);
                        }
                        else {
                            viewHolder.itemView.setBackgroundColor(Color.GREEN);
                        }
                    }
                    mRecyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }*/
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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

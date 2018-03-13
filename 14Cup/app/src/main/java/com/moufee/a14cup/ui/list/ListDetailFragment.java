package com.moufee.a14cup.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.CategoryComparator;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.validation.DataValidation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment that displays a list of Items on a shopping list.
 */
public class ListDetailFragment extends Fragment {

    private OnListItemInteractionListener mListener;
    private ListViewModel mViewModel;
    private ListDetailRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private ArrayAdapter<String> mCategoryAdapter;
    private static final String TAG = "LIST_DETAIL_FRAGMENT";
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

                    //do the data validation
                    String str = DataValidation.validateShoppingListItem(NewItem);
                    if (str.equals("valid")) {
                        mListRepository.addItem(mViewModel.getSelectedListID().getValue(), NewItem);
                        newItemEdit.setText("");
                        return true;
                    } else {
                        //print the error to the screen
                        Toast.makeText(getActivity(), str,
                                Toast.LENGTH_LONG).show();
                    }

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
                if (swipeDir == ItemTouchHelper.RIGHT) {
                    int index = viewHolder.getAdapterPosition();
                    String listID = mViewModel.getSelectedListID().getValue();
                    ShoppingListItem item = mViewModel.getCurrentListItems().getValue().get(index);
                    item.toggleCompletion();
                    mListRepository.updateItem(listID, item);
//                    mRecyclerViewAdapter.notifyItemChanged(index);
                } else if (swipeDir == ItemTouchHelper.LEFT) {
                    int index = viewHolder.getAdapterPosition();
                    String listID = mViewModel.getSelectedListID().getValue();
                    ShoppingListItem item = mViewModel.getCurrentListItems().getValue().get(index);
                    mListRepository.deleteItem(listID, item.id);
                }
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
            mCategoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
            setListeners();
        }
    }

    private void setListeners() {
        mViewModel.getCurrentListItems().observe(this, new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingListItem> shoppingListItems) {
                if (shoppingListItems != null) {
                    CategorySortOrder sortOrder = mViewModel.getCurrentSortOrder().getValue();
                    if (sortOrder != null) {
                        Collections.sort(shoppingListItems, new CategoryComparator(sortOrder));
                    }
                    List<ShoppingListItem> newItems = new ArrayList<>(shoppingListItems);
                    mRecyclerViewAdapter.submitList(newItems);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
        mViewModel.getCurrentSortOrder().observe(this, new Observer<CategorySortOrder>() {
            @Override
            public void onChanged(@Nullable CategorySortOrder categorySortOrder) {
                mCategoryAdapter.clear();
                if (categorySortOrder == null)
                    return;
                mCategoryAdapter.addAll(categorySortOrder.categoryOrder);
                List<ShoppingListItem> items = mViewModel.getCurrentListItems().getValue();
                if (items == null) return;
                Collections.sort(items, new CategoryComparator(categorySortOrder));
                // it doesn't detect the sorting change if the new list refers to the same object
                List<ShoppingListItem> newItems = new ArrayList<>(items);
                mRecyclerViewAdapter.submitList(newItems);
            }
        });
        mListener = new OnListItemInteractionListener() {
            @Override
            public boolean onLongClick(final ShoppingListItem item) {
                if (mCategoryAdapter.getCount() != 0)
                    new AlertDialog.Builder(getActivity())
                            .setTitle(item.name + ": Choose a Category")
                            .setAdapter(mCategoryAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String category = mCategoryAdapter.getItem(which);
                                    item.category = category;
                                    mListRepository.updateItem(mViewModel.getSelectedListID().getValue(), item);
                                }
                            })
                            .show();
                else {
                    Toast.makeText(getActivity(), "Choose a sort order first.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        };
        mRecyclerViewAdapter = new ListDetailRecyclerViewAdapter(mListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

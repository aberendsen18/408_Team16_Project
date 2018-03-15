package com.moufee.a14cup.ui.categorySorting;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.repository.CategoryRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Travis Kovacic on 2/12/2018.
 * This fragment displays the list of category sort orders
 * The use can then select a sort order to edit it
 */

public class CategorySortListFragment extends Fragment {

    @Inject
    CategoryRepository cListRepository;

    @Inject
    ViewModelProvider.Factory mFactory;

    private OnCategorySortListInteractionListener mListener;
    private CategorySortViewModel mViewModel;
    private RecyclerView recyclerView;
    private CategorySortListRecyclerViewAdapter recyclerViewAdapter;

    public CategorySortListFragment() {
    }

    public static CategorySortListFragment newInstance() {
        return new CategorySortListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mViewModel = ViewModelProviders.of(getActivity(), mFactory).get(CategorySortViewModel.class);
        recyclerViewAdapter = new CategorySortListRecyclerViewAdapter(mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorysorting_title_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    CategorySortOrder deleted = mViewModel.getSortOrders().getValue().get(position);
                    cListRepository.deleteSortOrder(deleted);
                }
            };
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(recyclerView);
        }
        return view;
    }

    private void setListeners() {
        // viewmodel
        mViewModel.getSortOrders().observe(this, new Observer<List<CategorySortOrder>>() {
            @Override
            public void onChanged(@Nullable List<CategorySortOrder> categorySortOrders) {
                recyclerViewAdapter.submitList(categorySortOrders);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_sort_order:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("Add Sort Order");
                // reusing add new category UI
                // should probably refactor to make a more general text entry dialog
                final View myView = getLayoutInflater().inflate(R.layout.add_new_category, null);
                EditText editText = myView.findViewById(R.id.category_name);
                editText.setHint("Sort Order Name");

                builder.setView(myView);
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText input = myView.findViewById(R.id.category_name);
                        String sortName = input.getText().toString();
                        CategorySortOrder order = new CategorySortOrder(sortName);
                        order.owner = mViewModel.getCurrentUser().getUid();
                        cListRepository.addSortOrder(order);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                showSoftKeyboard(editText);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.category_sort_list_menu, menu);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            mViewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(CategorySortViewModel.class);
            setListeners();
        }
        if (context instanceof OnCategorySortListInteractionListener) {
            mListener = (OnCategorySortListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategorySortListInteractionListener");
        }
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
    public interface OnCategorySortListInteractionListener {
        void onSelectSortList(CategorySortOrder order);
    }

}

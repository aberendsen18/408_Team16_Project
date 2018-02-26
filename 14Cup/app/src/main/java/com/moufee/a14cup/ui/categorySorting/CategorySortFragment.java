package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.SortCategory;

import java.util.ArrayList;

/**
 * Created by Travis Kovacic on 2/16/2018.
 * This fragment allows the user to edit a category sort order
 * by dragging the categories into the order they want.
 * Users can also add and delete categories.
 */

public class CategorySortFragment extends Fragment {

    private CategorySortViewModel viewModel;
    private RecyclerView recyclerView;
    private CategorySortRecyclerViewAdapter recyclerViewAdapter;

    public CategorySortFragment() {
    }

    public static CategorySortFragment newInstance() {
        return new CategorySortFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        viewModel = ViewModelProviders.of(this).get(CategorySortViewModel.class);
        recyclerViewAdapter = new CategorySortRecyclerViewAdapter(new ArrayList<SortCategory>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorysorting_sort_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        setListeners();
        return view;
    }

    private void setListeners() {
        // viewmodel
        final ArrayList<SortCategory> categories = viewModel.getCategories();
        if (categories != null) {
            recyclerViewAdapter.setCategories(categories);
        } else {
            recyclerViewAdapter.setCategories(new ArrayList<SortCategory>());
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                //todo: actually update database
                SortCategory moved = categories.remove(fromPos);
                categories.add(toPos, moved);
                recyclerViewAdapter.setCategories(categories);
                recyclerViewAdapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //todo: allow swipe to delete
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.category_sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_category:
                //todo: handle creating a category
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

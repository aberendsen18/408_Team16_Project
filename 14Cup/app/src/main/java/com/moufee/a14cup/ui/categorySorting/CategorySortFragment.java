package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.SortCategory;
import com.moufee.a14cup.repository.CategoryRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Travis Kovacic on 2/16/2018.
 * This fragment allows the user to edit a category sort order
 * by dragging the categories into the order they want.
 * Users can also add and delete categories.
 */

public class CategorySortFragment extends Fragment {

    @Inject
    CategoryRepository cListRepository;

    @Inject
    ViewModelProvider.Factory mFactory;

    private CategorySortListViewModel viewModel;
    private RecyclerView recyclerView;
    private CategorySortRecyclerViewAdapter recyclerViewAdapter = new CategorySortRecyclerViewAdapter(new ArrayList<SortCategory>());
    private static final String TAG = "CategorySortFragment";

    public CategorySortFragment() {
    }

    public static CategorySortFragment newInstance() {
        return new CategorySortFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

            protected boolean isElevated = false;
            protected float originalElevation = 0;
            protected float activeElevationChange = 8f;


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
                int pos = viewHolder.getAdapterPosition();
                //cListRepository.deleteCategory(viewModel.CurrentSort, viewModel.CurrentSort.categories.get(pos));
                categories.remove(pos);
                recyclerViewAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (dX == 0 && dY != 0) {
                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    return;
                }

                final View fg = ((CategorySortHolder) viewHolder).mBinding.categoryForeground;

                getDefaultUIUtil().onDrawOver(c, recyclerView, fg, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null && actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    getDefaultUIUtil().onSelected(((CategorySortHolder) viewHolder).mBinding.categoryForeground);
                    updateElevation(viewHolder, true);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                // if we are dragging vertically
                if (dX == 0 && dY != 0) {
                    // prevents default elevation change (?)
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false);
                    return;
                }
                final View fg = ((CategorySortHolder) viewHolder).mBinding.categoryForeground;

                getDefaultUIUtil().onDraw(c, recyclerView, fg, dX, dY, actionState, isCurrentlyActive);

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                Log.d(TAG, "clearView: onClear");
                updateElevation(viewHolder, false);
            }

            protected void updateElevation(@NonNull RecyclerView.ViewHolder holder, boolean elevate) {
                if (elevate) {
                    originalElevation = ViewCompat.getElevation(holder.itemView);
                    float newElevation = activeElevationChange;
                    ViewCompat.setElevation(holder.itemView, newElevation);
                    isElevated = true;
                } else {
                    ViewCompat.setElevation(holder.itemView, 0);
                    originalElevation = 0;
                    isElevated = false;
                }
            }

            /**
             * Finds the elevation of the highest visible viewHolder to make sure the elevated view
             * from {@link #updateElevation(RecyclerView.ViewHolder, boolean)} is above
             * all others.
             *
             * @param recyclerView The RecyclerView to use when determining the height of all the visible ViewHolders
             */
            protected float findMaxElevation(@NonNull RecyclerView recyclerView) {
                float maxChildElevation = 0;

                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View child = recyclerView.getChildAt(i);
                    float elevation = ViewCompat.getElevation(child);

                    if (elevation > maxChildElevation) {
                        maxChildElevation = elevation;
                    }
                }

                return maxChildElevation;
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
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            viewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(CategorySortListViewModel.class);
            setListeners();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

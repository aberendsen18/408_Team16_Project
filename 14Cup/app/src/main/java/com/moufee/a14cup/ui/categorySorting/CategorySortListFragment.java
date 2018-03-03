package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.repository.CategoryRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Travis Kovacic on 2/12/2018.
 * This fragment displays the list of category sort orders
 * The use can then select a sort order to edit it
 */

public class CategorySortListFragment extends Fragment{

    @Inject
    CategoryRepository cListRepository;

    @Inject
    ViewModelProvider.Factory mFactory;

    private OnCategorySortListInteractionListener mListener;
    private CategorySortListViewModel viewModel;
    private RecyclerView recyclerView;
    private CategorySortListRecyclerViewAdapter recyclerViewAdapter;

    public CategorySortListFragment(){
    }

    public static CategorySortListFragment newInstance() { return new CategorySortListFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, mFactory).get(CategorySortListViewModel.class);
        recyclerViewAdapter = new CategorySortListRecyclerViewAdapter(new ArrayList<CategorySortList>(), mListener);
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
        }
        setListeners();
        return view;
    }

    private void setListeners() {
        // viewmodel
        ArrayList<CategorySortList> sortList = viewModel.getSorts();

        if(sortList != null){
            viewModel.CurrentSort = sortList.get(0);
            recyclerViewAdapter.setSortList(sortList);
        }else{
            recyclerViewAdapter.setSortList(new ArrayList<CategorySortList>());
        }
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        /*if (context instanceof AppCompatActivity) {
            viewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(CategorySortListViewModel.class);
            setListeners();
        }*/
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
        void onSelectSortList(CategorySortList list);
    }

}

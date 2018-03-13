package com.moufee.a14cup.ui.recipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.moufee.a14cup.R;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.recipes.Ingredient;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.repository.RecipeRepository;
import com.moufee.a14cup.repository.ShoppingListRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RecipeInfoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyRecipeInfoRecyclerViewAdapter mRecyclerViewAdapter;
    private RecipeViewModel mViewModel;
    private OnListFragmentInteractionListener mListener;
    private TextView mRecipeTitle;
    private Spinner mShoppingListSpinner;
    private Button mSubmittButton;
    private ArrayAdapter<ShoppingList> mAdapter;

    @Inject
    ViewModelProvider.Factory mFactory;
    @Inject
    RecipeRepository mRecipeRepo;
    @Inject
    ShoppingListRepository mShoppingListRepo;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeInfoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipeInfoFragment newInstance(int columnCount) {
        RecipeInfoFragment fragment = new RecipeInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_info_list, container, false);
        Context context = view.getContext();


        mRecipeTitle = (TextView) view.findViewById(R.id.textViewRecipeName);
        mSubmittButton = (Button) view.findViewById(R.id.buttonAddRecipe);

        // User shopping list spinner
        mShoppingListSpinner = (Spinner) view.findViewById(R.id.shoppingListSpinner);

        mAdapter = new ArrayAdapter<ShoppingList>(context, android.R.layout.simple_spinner_dropdown_item);
        mShoppingListSpinner.setAdapter(mAdapter);


        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.ingredientsListRecyclerView);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        //mRecyclerViewAdapter = new MyRecipeInfoRecyclerViewAdapter(mViewModel.getSelectedRecipe(), mListener);
        recyclerView.setAdapter(mRecyclerViewAdapter);

        setListeners();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            mViewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(RecipeViewModel.class);
            mRecyclerViewAdapter = new MyRecipeInfoRecyclerViewAdapter(mViewModel.getSelectedRecipe(), mListener);
        }
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    private void setListeners(){
        mViewModel.getSelectedLiveDataRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if (recipe != null) {
                    mRecyclerViewAdapter.setRecipe(recipe);
                    mRecipeTitle.setText(recipe.label);
                }
            }
        });

        mViewModel.getShoppingLists().observe(this, new Observer<List<ShoppingList>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingList> shoppingLists) {
                mAdapter.clear();
                if(shoppingLists != null){
                    mAdapter.addAll(shoppingLists);
                }
                mShoppingListSpinner.setAdapter(mAdapter);
            }
        });

        mSubmittButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingList userList = (ShoppingList) mShoppingListSpinner.getSelectedItem();
                List<String> checkedIngs = mRecyclerViewAdapter.getCheckedIngrediants();

                for (String ing : checkedIngs){
                    mShoppingListRepo.addItem(userList.id, new ShoppingListItem(ing));
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(Recipe item);
    }
}

package com.moufee.a14cup.ui.recipes;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moufee.a14cup.R;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.recipes.RecipesList;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnRecipeFragmentInteractionListener}
 * interface.
 */
public class RecipeFragment extends Fragment {

    private OnRecipeFragmentInteractionListener mListener;

    @Inject
    RecipeViewModel mRecipeViewModel;
    @Inject
    ViewModelProvider.Factory mFactory;
    private RecyclerView mRecyclerView;
    private MyRecipeRecyclerViewAdapter mAdapter;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipeFragment newInstance(int columnCount) {
        return new RecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        final TextView searchRecipeName = view.findViewById(R.id.recipe_search_input);
        searchRecipeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    mRecipeViewModel.setQuery(searchRecipeName.getText().toString());
                    searchRecipeName.setText("");
                }
                return false;
            }
        });

        // Set the adapter
        Context context = view.getContext();
        mProgressBar = view.findViewById(R.id.loadingPanel);
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyRecipeRecyclerViewAdapter(new ArrayList<RecipesList.Hit>(), mListener);
        mRecyclerView.setAdapter(mAdapter);
        setListeners();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            mListener = (OnRecipeFragmentInteractionListener) context;
        }
        mRecipeViewModel = ViewModelProviders.of((AppCompatActivity) context, mFactory).get(RecipeViewModel.class);

        /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }


    private void setListeners() {
        mRecipeViewModel.getRecipesList().observe(this, new Observer<RecipesList>() {
            @Override
            public void onChanged(@Nullable RecipesList recipesList) {
                if (recipesList != null && recipesList.getHits() != null) {
                    if (recipesList.hits.size() == 0) {
                        Toast.makeText(getActivity(), "Try again in a minute.", Toast.LENGTH_LONG).show();
                    }
                    mAdapter.setValues(recipesList.getHits());
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
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
    public interface OnRecipeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRecipeFragmentInteraction(Recipe item);
    }
}

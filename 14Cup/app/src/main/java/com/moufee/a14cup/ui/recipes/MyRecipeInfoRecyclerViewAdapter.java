package com.moufee.a14cup.ui.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.moufee.a14cup.R;
import com.moufee.a14cup.recipes.Ingredient;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.ui.recipes.RecipeInfoFragment.OnListFragmentInteractionListener;
import com.moufee.a14cup.ui.recipes.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRecipeInfoRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeInfoRecyclerViewAdapter.ViewHolder> {

    private Recipe mRecipe;
    private final OnListFragmentInteractionListener mListener;

    public MyRecipeInfoRecyclerViewAdapter(Recipe recipe, OnListFragmentInteractionListener listener) {

        mRecipe = recipe;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mRecipe.getIngredients().get(position);
        holder.mIdView.setText(Integer.toString(position+1));
        holder.mContentView.setText(mRecipe.getIngredientText(position));
        holder.mContentView.setChecked(mRecipe.getIngredients().get(position).isChecked());


        holder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecipe.getIngredients().get(position).toggleChecked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipe.getIngredients().size();
    }

    public List<String> getCheckedIngrediants(){
        List<String> checkedIngs = new ArrayList<>();

        for(Ingredient ing : mRecipe.getIngredients()){
            if(ing.isChecked()){
                checkedIngs.add(ing.getText());
            }
        }

        return checkedIngs;
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final CheckBox mContentView;
        public Ingredient mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.idRecipeInfo);
            mContentView = (CheckBox) view.findViewById(R.id.checkBoxRecipeInfo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

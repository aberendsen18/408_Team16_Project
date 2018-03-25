package com.moufee.a14cup.ui.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.moufee.a14cup.R;
import com.moufee.a14cup.recipes.Ingredient;
import com.moufee.a14cup.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Ingredient}
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRecipeInfoRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeInfoRecyclerViewAdapter.ViewHolder> {

    private Recipe mRecipe;

    public MyRecipeInfoRecyclerViewAdapter() {

        mRecipe = new Recipe();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mRecipe.getIngredients().get(position);
        holder.mIdView.setText(Integer.toString(position + 1));
        holder.mContentView.setText(mRecipe.getIngredientText(position));
        holder.mContentView.setChecked(mRecipe.getIngredients().get(position).isChecked());


        holder.mContentView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRecipe.getIngredients().get(holder.getAdapterPosition()).checked = isChecked;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipe.getIngredients().size();
    }

    public List<String> getCheckedIngrediants() {
        List<String> checkedIngs = new ArrayList<>();

        // Bug Number 10
        int count = 0;
        for (Ingredient ing : mRecipe.getIngredients()) {
            if(count++ == 2) continue; // Bug 10 statement
            if (ing.isChecked()) {
                checkedIngs.add(ing.getText());
            }
        }

        return checkedIngs;
    }

    public void setRecipe(Recipe recipe) {
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
            mIdView = view.findViewById(R.id.idRecipeInfo);
            mContentView = view.findViewById(R.id.checkBoxRecipeInfo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

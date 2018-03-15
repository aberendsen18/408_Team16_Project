package com.moufee.a14cup.categorySorts;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortOrder {

    public String name;
    public String id;
    public String owner;
    public List<String> categoryOrder = new ArrayList<>();
    public static final DiffCallback DIFF_CALLBACK = new DiffCallback();

    public CategorySortOrder() {
    }

    public CategorySortOrder(String name, List<String> categories) {
        this.name = name;
        this.categoryOrder = categories;
    }

    public CategorySortOrder(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<CategorySortOrder> {
        @Override
        public  boolean areItemsTheSame(CategorySortOrder oldItem, CategorySortOrder newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(CategorySortOrder oldItem, CategorySortOrder newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }

}

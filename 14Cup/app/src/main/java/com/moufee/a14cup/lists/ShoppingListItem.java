package com.moufee.a14cup.lists;

import android.support.v7.util.DiffUtil;

/**
 * An item on a shopping list
 */

public class ShoppingListItem {
    public String name;
    public String id;
    public String category;
    public boolean completed = false;
    public static final DiffCallback DIFF_CALLBACK = new DiffCallback();


    public ShoppingListItem() {
    }

    public ShoppingListItem(String name) {
        this.name = name;
    }

    public ShoppingListItem(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public void toggleCompletion() {
        this.completed = !this.completed;
    }

    @Override
    public String toString() {
        return name;
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<ShoppingListItem> {
        @Override
        public boolean areItemsTheSame(ShoppingListItem oldItem, ShoppingListItem newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(ShoppingListItem oldItem, ShoppingListItem newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }
}

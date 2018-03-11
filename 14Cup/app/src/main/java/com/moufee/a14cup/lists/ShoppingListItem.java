package com.moufee.a14cup.lists;

/**
 * An item on a shopping list
 */

public class ShoppingListItem {
    public String name;
    public String id;
    public String category;
    public boolean completed = false;

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
}

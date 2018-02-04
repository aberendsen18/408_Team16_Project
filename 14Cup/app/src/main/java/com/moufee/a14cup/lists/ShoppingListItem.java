package com.moufee.a14cup.lists;

/**
 * An item on a shopping list
 */

public class ShoppingListItem {
    public String name;
    public String category;

    public ShoppingListItem() {
    }

    @Override
    public String toString() {
        return name;
    }
}

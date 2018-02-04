package com.moufee.a14cup.lists;

import java.util.List;

/**
 * Represents one shopping list
 */

public class ShoppingList {
    public String name;
    public String owner;
    public List<String> sharedUsers;
    public List<ShoppingListItem> items;

    @Override
    public String toString() {
        return name + ": " + items;
    }
}

package com.moufee.a14cup.lists;

import java.util.List;

/**
 * Created by Ben on 1/31/18.
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

package com.moufee.a14cup.lists;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Represents one shopping list
 */

public class ShoppingList {
    public String name;
    public String id;
    public String owner;
    @ServerTimestamp
    public Date createdDate;
    public List<String> sharedUsers;
    public List<ShoppingListItem> items;

    @Override
    public String toString() {
        return id + " : " + name + ": " + items;
    }
}

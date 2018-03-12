package com.moufee.a14cup.lists;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents one shopping list
 */

public class ShoppingList {
    public String name;
    public String id;
    public String owner;
    @ServerTimestamp
    public Date createdDate;
    public Map<String, Boolean> sharedWith = new HashMap<>();
    public Map<String, String> sortOrders = new HashMap<>();

    public ShoppingList() {
    }

    public ShoppingList(String name) {
        this.name = name;
    }

    public ShoppingList(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return id + " : " + name + " " + sortOrders;
    }
}

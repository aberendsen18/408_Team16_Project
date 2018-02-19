package com.moufee.a14cup.util;

import com.moufee.a14cup.lists.ShoppingList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ben on 2/14/18.
 */

public class TestUtil {
    public static List<ShoppingList> createLists(String name, String owner, int number) {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ShoppingList list = new ShoppingList(name + i, owner);
            list.id = UUID.randomUUID().toString();
            shoppingLists.add(list);
        }
        return shoppingLists;
    }
}

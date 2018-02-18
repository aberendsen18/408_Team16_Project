package com.moufee.a14cup.validation;

import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;

/**
 * this is a validation class that checks the user input and makes sure that it is valid
 * Created by andrewberendsen on 2/11/18.
 */
public abstract class DataValidation {

    //private method that validates a shopping list
    public static String validateShoppingList(ShoppingList s) {
        if (s.name == null || s.name.length() == 0 || s.name.isEmpty()) {
            return "The string that you entered is blank!";
        } else if (s.name.length() > 256) {
            return "The string that you entered is too long!";
        }

        return "";
    }

    //private method that validates a shopping list item
    public static String validateShoppingListItem(ShoppingListItem s) {
        if (s.name == null || s.name.length() == 0 || s.name.isEmpty()) {
            return "The string that you entered is blank!";
        } else if (s.name.length() > 256) {
            return "The string that you entered is too long!";
        }

        return "valid";
    }
}

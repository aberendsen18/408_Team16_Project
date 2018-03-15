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
        if (s.name == null || s.name.length() == 0 || s.name.isEmpty() || s.name.trim().length() == 0) {
            return "Please enter a list title!";
        } else if (s.name.length() > 256) {
            return "The title that you entered is too long!";
        }

        return "valid";
    }

    //private method that validates a shopping list item
    public static String validateShoppingListItem(ShoppingListItem s) {
        if (s.name == null || s.name.length() == 0 || s.name.isEmpty() || s.name.trim().length() == 0) {
            return "Please enter an item name!";
        } else if (s.name.length() > 256) {
            return "The item that you entered is too long!";
        }

        return "valid";
    }

    //private method that validates a shopping list item
    public static String validateCategoryName(String category) {
        if (category == null || category.length() == 0 || category.isEmpty() || category.trim().length() == 0) {
            return "Please enter an category name!";
        } else if (category.length() > 256) {
            return "The category that you entered is too long!";
        }

        return "valid";
    }
}

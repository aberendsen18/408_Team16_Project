package com.moufee.a14cup.validation;

import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;

/**
 * this is a validation class that checks the user input and makes sure that it is valid
 * Created by andrewberendsen on 2/11/18.
 */

public class DataValidation {

    //constructor to make an object of this class
    public DataValidation() {

    }


    //private method that validates a shopping list
    public String valid_shopping_list(ShoppingList s) {
        if (s.name == null) {
            return "the string that you entered is null!";
        } else if (s.name.length() == 0) {
            return "the string that you entered is blank!";
        } else if (s.name.length() > 256) {
            return "the string that you entered is too long!";
        }

        return "";
    }

    //private method that validates a shopping list item
    public String valid_shopping_list_item(String s) {
        if (s == null) {
            return "the string that you entered is null!";
        } else if (s.length() == 0) {
            return "the string that you entered is blank!";
        } else if (s.length() > 256) {
            return "the string that you entered is too long!";
        } else if (s.isEmpty()) {
            return "the string that you entered is blank!";
        }

        return "valid";
    }
}

package com.moufee.a14cup.util;


import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.validation.DataValidation;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by andrewberendsen on 2/18/18.
 */

public class TestValidation {

    DataValidation validation = new DataValidation();


    @Test
    public void null_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        String str = validation.valid_shopping_list(newList);
        assertEquals("The string that you entered is blank!", str);
    }

    @Test
    public void empty_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        newList.name = "";
        String str = validation.valid_shopping_list(newList);
        assertEquals("The string that you entered is blank!", str);
    }

    @Test
    public void length_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        String too_long = "";
        for (int i = 0; i < 300; i++) {
            too_long += 'a';
        }
        newList.name = too_long;
        String str = validation.valid_shopping_list(newList);
        assertEquals("The string that you entered is too long!", str);
    }

    @Test
    public void valid_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        newList.name = "list01";
        String str = validation.valid_shopping_list(newList);
        assertEquals("valid", str);
    }


    @Test
    public void null_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        String str = validation.valid_shopping_list_item(newListItem);
        assertEquals("The string that you entered is blank!", str);
    }

    @Test
    public void empty_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        newListItem.name = "";
        String str = validation.valid_shopping_list_item(newListItem);
        assertEquals("The string that you entered is blank!", str);
    }

    @Test
    public void length_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        String too_long = "";
        for (int i = 0; i < 300; i++) {
            too_long += 'a';
        }
        newListItem.name = too_long;
        String str = validation.valid_shopping_list_item(newListItem);
        assertEquals("The string that you entered is too long!", str);
    }

    @Test
    public void valid_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        newListItem.name = "list_item01";
        String str = validation.valid_shopping_list_item(newListItem);
        assertEquals("valid", str);
    }


}

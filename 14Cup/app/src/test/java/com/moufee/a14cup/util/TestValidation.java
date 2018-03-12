package com.moufee.a14cup.util;


import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;
import com.moufee.a14cup.validation.DataValidation;

import org.junit.Test;

import static com.moufee.a14cup.validation.DataValidation.validateCategoryName;
import static com.moufee.a14cup.validation.DataValidation.validateShoppingList;
import static com.moufee.a14cup.validation.DataValidation.validateShoppingListItem;
import static org.junit.Assert.*;
/**
 * Created by andrewberendsen on 2/18/18.
 */

public class TestValidation {




    @Test
    public void null_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        String str = validateShoppingList(newList);
        assertEquals("Please enter a list title!", str);
    }

    @Test
    public void empty_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        newList.name = "";
        String str = validateShoppingList(newList);
        assertEquals("Please enter a list title!", str);
    }

    @Test
    public void length_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        String too_long = "";
        for (int i = 0; i < 300; i++) {
            too_long += 'a';
        }
        newList.name = too_long;
        String str = validateShoppingList(newList);
        assertEquals("The title that you entered is too long!", str);
    }

    @Test
    public void valid_string_list() throws Exception {
        ShoppingList newList = new ShoppingList();
        newList.name = "list01";
        String str = validateShoppingList(newList);
        assertEquals("valid", str);
    }


    @Test
    public void null_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        String str = validateShoppingListItem(newListItem);
        assertEquals("Please enter an item name!", str);
    }

    @Test
    public void empty_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        newListItem.name = "";
        String str = validateShoppingListItem(newListItem);
        assertEquals("Please enter an item name!", str);
    }

    @Test
    public void length_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        String too_long = "";
        for (int i = 0; i < 300; i++) {
            too_long += 'a';
        }
        newListItem.name = too_long;
        String str = validateShoppingListItem(newListItem);
        assertEquals("The item that you entered is too long!", str);
    }

    @Test
    public void valid_string_list_item() throws Exception {
        ShoppingListItem newListItem = new ShoppingListItem();
        newListItem.name = "list_item01";
        String str = validateShoppingListItem(newListItem);
        assertEquals("valid", str);
    }

    @Test
    public void validCategorySortName() throws Exception {

        String name = "list_item01";
        String str = validateCategoryName(name);
        assertEquals("valid", str);
    }

    @Test
    public void nullCategorySortName() throws Exception {
        String name = null;
        String str = validateCategoryName(name);
        assertEquals("Please enter an category name!", str);
    }
    @Test
    public void emptyCategorySortName() throws Exception {
        String name = "";
        String str = validateCategoryName(name);
        assertEquals("Please enter an category name!", str);
    }
    @Test
    public void spacesCategorySortName() throws Exception {
        String name = "    ";
        String str = validateCategoryName(name);
        assertEquals("Please enter an category name!", str);
    }


}

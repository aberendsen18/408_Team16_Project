package com.moufee.a14cup.ui.categorySorting;

import com.moufee.a14cup.categorySorts.CategoryComparator;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.lists.ShoppingListItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Ben on 3/4/18.
 * A test for the CategoryComparator
 */
@RunWith(JUnit4.class)
public class CategoryComparatorTest {

    @Test
    public void sortsCorrectly() {
        List<String> categoryOrder = new ArrayList<>(Arrays.asList("Produce", "Dairy", "Bakery", "Frozen"));
        CategorySortOrder sortOrder = new CategorySortOrder("Walmart", categoryOrder);

        ShoppingList list = new ShoppingList("Walmart");
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(new ShoppingListItem("Milk", "Dairy"));
        items.add(new ShoppingListItem("Ice Cream", "Frozen"));
        items.add(new ShoppingListItem("Coffee"));
        items.add(new ShoppingListItem("Apples", "Produce"));
        items.add(new ShoppingListItem("Bread", "Bakery"));

        // shuffle it a few times to ensure it works for various orderings
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(items);
            Collections.sort(items, new CategoryComparator(sortOrder));
            assertEquals("Apples", items.get(0).name);
            assertEquals("Milk", items.get(1).name);
            assertEquals("Bread", items.get(2).name);
            assertEquals("Ice Cream", items.get(3).name);
            assertEquals("Coffee", items.get(4).name);
        }
    }
}

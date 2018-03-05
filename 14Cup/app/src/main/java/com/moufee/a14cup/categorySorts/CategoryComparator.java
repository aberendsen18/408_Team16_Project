package com.moufee.a14cup.categorySorts;

import com.moufee.a14cup.lists.ShoppingListItem;

import java.util.Comparator;

/**
 * Created by Ben on 3/4/18.
 * Sorts a list of shopping list items based on a category sort order
 * Items with unknown categories are sorted to the end of the list
 */

public class CategoryComparator implements Comparator<ShoppingListItem> {

    private CategorySortOrder mCategorySortOrder;

    public CategoryComparator(CategorySortOrder categorySortOrder) {
        mCategorySortOrder = categorySortOrder;
    }

    @Override
    public int compare(ShoppingListItem firstItem, ShoppingListItem secondItem) {
        int firstItemIndex = mCategorySortOrder.categoryOrder.indexOf(firstItem.category);
        int secondItemIndex = mCategorySortOrder.categoryOrder.indexOf(secondItem.category);

        if (firstItemIndex == -1 && secondItemIndex == -1)
            return 0;
        if (firstItemIndex == -1)
            return 1;
        if (secondItemIndex == -1)
            return -1;

        if (firstItemIndex > secondItemIndex) {
            return 1;
        }

        if (firstItemIndex < secondItemIndex) {
            return -1;
        }

        return 0;
    }
}

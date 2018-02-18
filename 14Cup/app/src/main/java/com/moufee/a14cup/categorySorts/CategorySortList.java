package com.moufee.a14cup.categorySorts;

import java.util.ArrayList;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortList {

    public String name;
    public String id;
    public ArrayList<SortCategory> categories;

    public CategorySortList(String name, ArrayList<SortCategory> categories) {
        this.name = name;
        this.categories = categories;
    }

    public CategorySortList(String name) {
        this.name = name;
    }

}

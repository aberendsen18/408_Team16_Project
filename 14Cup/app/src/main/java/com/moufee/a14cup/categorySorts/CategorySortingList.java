package com.moufee.a14cup.categorySorts;

import java.util.List;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortingList {

    public String name;
    public String id;
    public List<String> categories;

    public CategorySortingList(String name, List<String> categories) {
        this.name = name;
        this.categories = categories;
    }

    public CategorySortingList(String name) {
        this.name = name;
    }

}

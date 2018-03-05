package com.moufee.a14cup.categorySorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortOrder {

    public String name;
    public String id;
    public String owner;
    public List<String> categoryOrder = new ArrayList<>();

    public CategorySortOrder() {
    }

    public CategorySortOrder(String name, List<String> categories) {
        this.name = name;
        this.categoryOrder = categories;
    }

    public CategorySortOrder(String name) {
        this.name = name;
    }

}

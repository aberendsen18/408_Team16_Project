package com.moufee.a14cup.categorySorts;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class SortCategory {

    public String name;
    public String id;
    public int rank;

    public SortCategory(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public SortCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}

package com.moufee.a14cup.ui.categorySorting;

import com.moufee.a14cup.categorySorts.CategorySorter;
import com.moufee.a14cup.categorySorts.SortCategory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by kovac on 2/18/2018.
 */

public class TestSorting {

    CategorySorter c = new CategorySorter();

    @Test
    public void sorting_isCorrect() throws Exception {
        ArrayList<Integer> itemList = new ArrayList<>(Arrays.asList(6,4,3,2,5,1));
        ArrayList<SortCategory> categoryList = new ArrayList<>();

        SortCategory sc1 = new SortCategory("sc1",1);
        categoryList.add(sc1);

        SortCategory sc2 = new SortCategory("sc2",2);
        categoryList.add(sc2);

        SortCategory sc3 = new SortCategory("sc3",3);
        categoryList.add(sc3);

        SortCategory sc4 = new SortCategory("sc4",4);
        categoryList.add(sc4);

        SortCategory sc5 = new SortCategory("sc5",5);
        categoryList.add(sc5);

        SortCategory sc6 = new SortCategory("sc6",6);
        categoryList.add(sc6);

        ArrayList<Integer> expectedResult = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        ArrayList<Integer> actualResult = c.sortList(itemList,categoryList);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void sortingWithExtraItems_isCorrect() throws Exception {
        ArrayList<Integer> itemList = new ArrayList<>(Arrays.asList(6,4,3,2,5,1,7,9,8));
        ArrayList<SortCategory> categoryList = new ArrayList<>();

        SortCategory sc1 = new SortCategory("sc1",1);
        categoryList.add(sc1);

        SortCategory sc2 = new SortCategory("sc2",2);
        categoryList.add(sc2);

        SortCategory sc3 = new SortCategory("sc3",3);
        categoryList.add(sc3);

        SortCategory sc4 = new SortCategory("sc4",4);
        categoryList.add(sc4);

        SortCategory sc5 = new SortCategory("sc5",5);
        categoryList.add(sc5);

        SortCategory sc6 = new SortCategory("sc6",6);
        categoryList.add(sc6);

        ArrayList<Integer> expectedResult = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,9,8));
        ArrayList<Integer> actualResult = c.sortList(itemList,categoryList);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void sortingWithNoItems_isCorrect() throws Exception {
        ArrayList<Integer> itemList = new ArrayList<>();
        ArrayList<SortCategory> categoryList = new ArrayList<>();

        SortCategory sc1 = new SortCategory("sc1",1);
        categoryList.add(sc1);

        SortCategory sc2 = new SortCategory("sc2",2);
        categoryList.add(sc2);

        SortCategory sc3 = new SortCategory("sc3",3);
        categoryList.add(sc3);

        SortCategory sc4 = new SortCategory("sc4",4);
        categoryList.add(sc4);

        SortCategory sc5 = new SortCategory("sc5",5);
        categoryList.add(sc5);

        SortCategory sc6 = new SortCategory("sc6",6);
        categoryList.add(sc6);

        ArrayList<Integer> expectedResult = new ArrayList<>();
        ArrayList<Integer> actualResult = c.sortList(itemList,categoryList);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void sortingWithNoCategories_isCorrect() throws Exception {
        ArrayList<Integer> itemList = new ArrayList<>(Arrays.asList(6,4,3,2,5,1,7,9,8));
        ArrayList<SortCategory> categoryList = new ArrayList<>();

        ArrayList<Integer> expectedResult = itemList;
        ArrayList<Integer> actualResult = c.sortList(itemList,categoryList);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void sortingWithNoItemsAndNoCategories_isCorrect() throws Exception {
        ArrayList<Integer> itemList = new ArrayList<>();
        ArrayList<SortCategory> categoryList = new ArrayList<>();

        ArrayList<Integer> expectedResult = new ArrayList<>();
        ArrayList<Integer> actualResult = c.sortList(itemList,categoryList);

        assertEquals(expectedResult, actualResult);
    }

}

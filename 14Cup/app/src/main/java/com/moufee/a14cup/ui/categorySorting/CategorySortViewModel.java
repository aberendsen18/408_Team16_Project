package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModel;

import com.moufee.a14cup.categorySorts.CategorySortingList;
import com.moufee.a14cup.categorySorts.CategorySortingListCategory;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by Travis Kovacic on 2/16/2018.
 */

public class CategorySortViewModel extends ViewModel {

    private ArrayList<CategorySortingListCategory> listOfCategories;
    public CategorySortingList CurrentSort;


    @Inject
    public CategorySortViewModel(){
        listOfCategories = mockCategories();
        CurrentSort = null;
    }

    public void setListOfCategories (ArrayList<CategorySortingListCategory> c) {listOfCategories = c;}

    public ArrayList<CategorySortingListCategory> getCategories() { return listOfCategories;}

    public ArrayList<CategorySortingListCategory> mockCategories(){
        ArrayList<CategorySortingListCategory> mock = new ArrayList<>();

        for(int j = 0; j < 5; j++){
            CategorySortingListCategory tempC = new CategorySortingListCategory("Category"+j, j);
            mock.add(tempC);
        }

        return  mock;
    }


}

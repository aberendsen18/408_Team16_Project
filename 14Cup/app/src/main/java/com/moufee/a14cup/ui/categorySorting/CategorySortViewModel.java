package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModel;

import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.categorySorts.SortCategory;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Travis Kovacic on 2/16/2018.
 */

public class CategorySortViewModel extends ViewModel {

    private ArrayList<SortCategory> listOfCategories;
    public CategorySortList CurrentSort;


    @Inject
    public CategorySortViewModel(){
        listOfCategories = mockCategories();
        CurrentSort = null;
    }

    public void setListOfCategories (ArrayList<SortCategory> c) {listOfCategories = c;}

    public ArrayList<SortCategory> getCategories() { return listOfCategories;}

    public ArrayList<SortCategory> mockCategories(){
        ArrayList<SortCategory> mock = new ArrayList<>();

        for(int j = 0; j < 5; j++){
            SortCategory tempC = new SortCategory("Category"+j, j);
            mock.add(tempC);
        }

        return  mock;
    }


}

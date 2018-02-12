package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.moufee.a14cup.categorySorts.CategorySortingList;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class SortListViewModel extends ViewModel {

    // Temporary setup for sort orders. Don't really know how to set up livedata stuff
    // Mocking repo for sort orders
    private ArrayList<CategorySortingList> listOfSorts;
    private ArrayList<String> categories = new ArrayList<>(Arrays.asList("Meat", "Vegetables", "Fruit", "Sweets", "Drinks"));
    public CategorySortingList CurrentSort;


    @Inject
    public SortListViewModel(){
        CurrentSort = null;
        listOfSorts = initMockSorts();
    }

    public ArrayList<CategorySortingList> getSorts() { return listOfSorts;}

    public ArrayList<CategorySortingList> initMockSorts(){
        ArrayList<CategorySortingList> toReturn = new ArrayList<CategorySortingList>();

        for(int i = 0; i<5; i++){
            CategorySortingList temp = new CategorySortingList("Sort "+i, categories);
            toReturn.add(temp);
        }

        return toReturn;
    }

}

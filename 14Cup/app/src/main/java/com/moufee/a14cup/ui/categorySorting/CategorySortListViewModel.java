package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModel;

import com.moufee.a14cup.categorySorts.CategorySortingList;
import com.moufee.a14cup.categorySorts.CategorySortingListCategory;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortListViewModel extends ViewModel {

    // Temporary setup for sort orders. Don't really know how to set up livedata stuff
    // Mocking repo for sort orders
    private ArrayList<CategorySortingList> listOfSorts;
    public CategorySortingList CurrentSort;


    @Inject
    public CategorySortListViewModel(){
        CurrentSort = null;
        listOfSorts = initMockSorts();
    }

    public ArrayList<CategorySortingList> getSorts() { return listOfSorts;}

    public ArrayList<CategorySortingListCategory> getCategories(CategorySortingList sort) {return sort.categories;}

    public ArrayList<CategorySortingList> initMockSorts(){
        ArrayList<CategorySortingList> toReturn = new ArrayList<CategorySortingList>();
        ArrayList<CategorySortingListCategory> categories = new ArrayList<>();

        for(int j = 0; j < 5; j++){
            CategorySortingListCategory tempC = new CategorySortingListCategory("Category"+j, j);
            categories.add(tempC);
        }

        for(int i = 0; i<5; i++){

            CategorySortingList temp = new CategorySortingList("Sort "+i, categories);
            toReturn.add(temp);
        }

        return toReturn;
    }



}

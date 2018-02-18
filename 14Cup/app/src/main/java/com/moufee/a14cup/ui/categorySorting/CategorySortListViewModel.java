package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModel;

import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.categorySorts.SortCategory;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortListViewModel extends ViewModel {

    // Temporary setup for sort orders. Don't really know how to set up livedata stuff
    // Mocking repo for sort orders
    private ArrayList<CategorySortList> listOfSorts;
    public CategorySortList CurrentSort;


    @Inject
    public CategorySortListViewModel(){
        CurrentSort = null;
        listOfSorts = initMockSorts();
    }

    public ArrayList<CategorySortList> getSorts() { return listOfSorts;}

    public ArrayList<SortCategory> getCategories(CategorySortList sort) {return sort.categories;}

    public ArrayList<CategorySortList> initMockSorts(){
        ArrayList<CategorySortList> toReturn = new ArrayList<CategorySortList>();
        ArrayList<SortCategory> categories = new ArrayList<>();

        for(int j = 0; j < 5; j++){
            SortCategory tempC = new SortCategory("Category"+j, j);
            categories.add(tempC);
        }

        for(int i = 0; i<5; i++){

            CategorySortList temp = new CategorySortList("Sort "+i, categories);
            toReturn.add(temp);
        }

        return toReturn;
    }



}

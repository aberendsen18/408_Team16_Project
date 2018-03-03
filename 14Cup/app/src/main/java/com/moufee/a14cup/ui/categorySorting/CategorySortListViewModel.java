package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.ViewModel;

import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.categorySorts.SortCategory;
import com.moufee.a14cup.repository.CategoryRepository;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortListViewModel extends ViewModel {


    private CategoryRepository cListRepository;
    // Temporary setup for sort orders. Don't really know how to set up livedata stuff
    // Mocking repo for sort orders
    private ArrayList<CategorySortList> listOfSorts;
    private ArrayList<SortCategory> listOfCategories;
    public CategorySortList CurrentSort;


    @Inject
    public CategorySortListViewModel(CategoryRepository listRepository){
        cListRepository = listRepository;
        listOfSorts = cListRepository.getCategorySortList();
        CurrentSort = listOfSorts.get(0);
        listOfCategories = cListRepository.getCategoryList(CurrentSort);
    }

    public ArrayList<CategorySortList> getSorts() { return cListRepository.getCategorySortList();}

    public ArrayList<SortCategory> getCategories() {return cListRepository.getCategoryList(CurrentSort);}

}

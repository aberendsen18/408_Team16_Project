package com.moufee.a14cup.repository;

import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.categorySorts.SortCategory;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Travis Kovacic on 3/2/2018.
 */

@Singleton
public class CategoryRepository {

    private final ArrayList<CategorySortList> categoryListCollection;

    @Inject
    public CategoryRepository() {
        this.categoryListCollection = initMockSorts();
    }

    public ArrayList<CategorySortList> getCategorySortList(){
        return categoryListCollection;
    }

    public ArrayList<SortCategory> getCategoryList(CategorySortList list) { return list.categories; }

    public void deleteCategory(CategorySortList list, SortCategory sort){
        // Convoluted transfer from string to int since when actually stored in firebase, the id's will be strings.
        // But my mocked implementation will need integers for ArrayList
        CategorySortList listToRemoveCategoryFrom =  categoryListCollection.get(Integer.parseInt(list.id));
        listToRemoveCategoryFrom.categories.remove(Integer.parseInt(sort.id));
    }

    public ArrayList<CategorySortList> initMockSorts(){
        ArrayList<CategorySortList> toReturn = new ArrayList<>();

        for(int i = 0; i<5; i++){
            ArrayList<SortCategory> categories = new ArrayList<>();
            for(int j = 0; j < 5; j++){
                int r = (int)(Math.random()*10);
                SortCategory tempC = new SortCategory("Category"+r, j);
                tempC.id = Integer.toString(j);
                categories.add(tempC);
            }
            CategorySortList temp = new CategorySortList("Sort "+i, categories);
            temp.id = Integer.toString(i);
            toReturn.add(temp);
        }

        return toReturn;
    }

}

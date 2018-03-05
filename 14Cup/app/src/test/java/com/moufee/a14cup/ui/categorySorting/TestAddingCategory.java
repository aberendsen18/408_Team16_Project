package com.moufee.a14cup.ui.categorySorting;

import com.moufee.a14cup.categorySorts.CategorySortList;
import com.moufee.a14cup.categorySorts.SortCategory;
import com.moufee.a14cup.repository.CategoryRepository;

import net.bytebuddy.TypeCache;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Travis Kovacic on 3/4/2018.
 */

public class TestAddingCategory {

    CategoryRepository testRepo = new CategoryRepository();

    @Test
    public void addingCategory_isCorrect() throws Exception {
        CategorySortList testList = testRepo.getCategorySortList().get(0);

        SortCategory categoryToAdd = new SortCategory("Added");

        testRepo.addCategory(testList,categoryToAdd);

        String expectedResult = "Added";
        String actualResult = testList.categories.get(testList.categories.size()-1).name;

        assertEquals(expectedResult, actualResult);
    }

}

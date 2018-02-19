package com.moufee.a14cup.categorySorts;

import java.util.ArrayList;

/**
 * Created by kovac on 2/18/2018.
 */

public class CategorySorter {

    public CategorySorter(){

    }

    public ArrayList<Integer> sortList(ArrayList<Integer> itemList, ArrayList<SortCategory> categoryList){
        if(itemList.size()==0 || (itemList.size()==0 && categoryList.size()==0)){
            return new ArrayList<>();
        }

        if(categoryList.size()==0){
            return itemList;
        }

        ArrayList<Integer> ranksFromCategories = new ArrayList<>();
        ArrayList<Integer> finalList = new ArrayList<>();

        for(int i = 0; i < categoryList.size(); i++){
            ranksFromCategories.add(categoryList.get(i).rank);
        }

        for(int i = 0; i < ranksFromCategories.size(); i++){
            for(int j = 0; j < itemList.size(); j++){
                if(ranksFromCategories.get(i).equals(itemList.get(j))){
                    finalList.add(itemList.get(j));
                    itemList.remove(j);
                }
            }
        }

        if(itemList.size()>0){
            for(int i = 0; i < itemList.size(); i++){
                finalList.add(itemList.get(i));
            }
        }

        return finalList;
    }

}

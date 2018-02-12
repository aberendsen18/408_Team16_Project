package com.moufee.a14cup.ui.categorySorting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moufee.a14cup.R;
import com.moufee.a14cup.lists.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis Kovacic on 2/12/2018.
 */

public class CategorySortingListFragment extends Fragment{

    public CategorySortingListFragment(){
    }

    public static  CategorySortingListFragment newInstance() { return new CategorySortingListFragment(); }

}

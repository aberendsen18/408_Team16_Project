package com.moufee.a14cup;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.categorySorts.CategorySortOrder;
import com.moufee.a14cup.databinding.ActivityMainBinding;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.recipes.Recipe;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.ui.list.ListDetailFragment;
import com.moufee.a14cup.ui.list.ListViewModel;
import com.moufee.a14cup.ui.list.MyListsFragment;
import com.moufee.a14cup.ui.list.MyListsRecyclerViewAdapter;
import com.moufee.a14cup.ui.recipes.RecipeFragment;
import com.moufee.a14cup.ui.recipes.RecipeInfoFragment;
import com.moufee.a14cup.ui.recipes.RecipeViewModel;
import com.moufee.a14cup.ui.settings.SettingsActivity;
import com.moufee.a14cup.validation.DataValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        MyListsFragment.OnListFragmentInteractionListener,
        RecipeFragment.OnRecipeFragmentInteractionListener,
        RecipeInfoFragment.OnRecipeInfoFragmentInteractionListener {

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    ShoppingListRepository mListRepository;

    private ListViewModel mViewModel;
    private RecipeViewModel rViewModel;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private MyListsRecyclerViewAdapter recyclerViewAdapter;
    private ActivityMainBinding mBinding;
    private ArrayAdapter<CategorySortOrder> mCategoryAdapter;

    @Override
    public void onRecipeFragmentInteraction(Recipe item) {
        RecipeInfoFragment fragment = RecipeInfoFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                //removed addToBackStack to create defect
                .commit();
        //mDrawerLayout.closeDrawer(GravityCompat.START);
        mToolbar.setTitle("Add A Recipe"); // write a get recipe label method.

        rViewModel.setSelectedRecipe(item);
    }

    @Override
    public void onRecipeInfoFragmentSubmit(ShoppingList list) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        // Resets fragment if on a different fragment, IE SortOrders/Settings/etc
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        if (!(f instanceof ListDetailFragment)) {
            ListDetailFragment fragment = ListDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
        }

        mToolbar.setTitle(list.name);
        mViewModel.setSelectedListID(list.id);
    }

    @Override
    public void onListFragmentInteraction(ShoppingList list) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        // Resets fragment if on a different fragment, IE SortOrders/Settings/etc
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        if (!(f instanceof ListDetailFragment)) {
            ListDetailFragment fragment = ListDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
        }

        mToolbar.setTitle(list.name);
        mViewModel.setSelectedListID(list.id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        if (!isSignedIn() || !checkPlayServices()) {
            startActivity(WelcomeActivity.getIntent(this));
            finish();
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.my_lists);
        setSupportActionBar(mToolbar);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        recyclerViewAdapter = new MyListsRecyclerViewAdapter(new ArrayList<ShoppingList>(), this);

        mCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);


        rViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeViewModel.class);
        mBinding.newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.add_new_list, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setView(view);
                final EditText ListName = view.findViewById(R.id.list_name);

                alertBuilder.setCancelable(true)
                        .setPositiveButton(R.string.action_new_list_positive, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShoppingList NewList = new ShoppingList();
                                NewList.name = ListName.getText().toString();
                                // should probably check for null but
                                // if nobody is logged in at this point, something is seriously wrong
                                NewList.owner = mViewModel.getCurrentUser().getValue().getUid();
                                String str = DataValidation.validateShoppingList(NewList);

                                if (str.equals("valid")) {
                                    mListRepository.addList(NewList);
                                } else {
                                    //print the error to the screen
                                    Toast.makeText(MainActivity.this, str,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(
                                "cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }
                        );
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set the adapter
        mRecyclerView = findViewById(R.id.my_lists_recycler_view);
        if (mRecyclerView != null) {
            Context context = mRecyclerView.getContext();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(recyclerViewAdapter);
        }

        // Set the recipes link in nav bar to open the recipies fragment
        LinearLayout recipeButton = findViewById(R.id.recipeButton);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeFragment fragment = RecipeFragment.newInstance(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mToolbar.setTitle("Search For Recipes");
            }
        });


        setListeners();

        ListDetailFragment fragment = ListDetailFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }

    private void setListeners() {

        mViewModel.getLists().observe(this, new Observer<List<ShoppingList>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingList> shoppingLists) {

                MenuItem sortMenuItem = mToolbar.getMenu().findItem(R.id.action_choose_sort_order);

                if (shoppingLists == null || shoppingLists.size() == 0) {
                    mViewModel.setSelectedListID(null);
                    recyclerViewAdapter.setLists(new ArrayList<ShoppingList>());
//                    mToolbar.setTitle("My Lists");
//                    sortMenuItem.setVisible(false);
                    return;
                }

                recyclerViewAdapter.setLists(shoppingLists);
                sortMenuItem.setVisible(true);
                ShoppingList firstList = shoppingLists.get(0);
                if (mViewModel.getSelectedListID().getValue() == null) {
                    mViewModel.setSelectedListID(firstList.id);
                    onListFragmentInteraction(firstList);
                }

            }
        });
        mViewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    startActivity(WelcomeActivity.getIntent(getApplicationContext()));
                    finish();
                } else
                    mBinding.setUser(firebaseUser);
            }
        });
        mViewModel.getSortOrders().observe(this, new Observer<List<CategorySortOrder>>() {
            @Override
            public void onChanged(@Nullable List<CategorySortOrder> categorySortOrders) {
                mCategoryAdapter.clear();
                if (categorySortOrders != null)
                    mCategoryAdapter.addAll(categorySortOrders);
            }
        });

        mViewModel.getSelectedList().observe(this, new Observer<ShoppingList>() {
            @Override
            public void onChanged(@Nullable ShoppingList shoppingList) {
                if (shoppingList != null)
                    mToolbar.setTitle(shoppingList.name);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                mListRepository.deleteList(mViewModel.getLists().getValue().get(viewHolder.getAdapterPosition()).id);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    boolean isSignedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_logout:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.action_settings:
                startActivity(SettingsActivity.getIntent(getApplicationContext()));
                return true;
            case R.id.action_choose_sort_order:
                new AlertDialog.Builder(this).setAdapter(mCategoryAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategorySortOrder selectedOrder = mCategoryAdapter.getItem(which);
                        ShoppingList currentList = mViewModel.getSelectedList().getValue();
                        if (currentList == null) return;
                        if (currentList.sortOrders == null)
                            currentList.sortOrders = new HashMap<>();
                        currentList.sortOrders.put(mViewModel.getCurrentUser().getValue().getUid(), selectedOrder.id);
                        mListRepository.updateList(currentList);
                    }
                })
                        .setTitle(R.string.title_choose_sort_order)
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }


}

package com.moufee.a14cup;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moufee.a14cup.databinding.ActivityMainBinding;
import com.moufee.a14cup.lists.ShoppingList;
import com.moufee.a14cup.repository.ShoppingListRepository;
import com.moufee.a14cup.ui.list.ListViewModel;
import com.moufee.a14cup.ui.list.MyListsFragment;
import com.moufee.a14cup.ui.list.MyListsRecyclerViewAdapter;
import com.moufee.a14cup.ui.list.ShoppingListDetailFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, MyListsFragment.OnListFragmentInteractionListener {

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    ShoppingListRepository mListRepository;

    private ListViewModel mViewModel;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private MyListsRecyclerViewAdapter recyclerViewAdapter;
    private ActivityMainBinding mBinding;

    @Override
    public void onListFragmentInteraction(ShoppingList list) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
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
        setListeners();

        ShoppingListDetailFragment fragment = ShoppingListDetailFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }

    private void setListeners() {
        mViewModel.getLists().observe(this, new Observer<List<ShoppingList>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingList> shoppingLists) {
                if (shoppingLists != null)
                    recyclerViewAdapter.setLists(shoppingLists);
                else
                    recyclerViewAdapter.setLists(new ArrayList<ShoppingList>());
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
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }
}

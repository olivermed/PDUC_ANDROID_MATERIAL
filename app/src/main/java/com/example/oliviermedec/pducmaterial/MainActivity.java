package com.example.oliviermedec.pducmaterial;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.oliviermedec.pducmaterial.Fragment.Categories.CategoriesFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Panier.PanierFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Scanner.ScannerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFirstView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            categoriesFragment.setMainActivityInstance(this);
            setFragment(categoriesFragment, categoriesFragment.TAG);
        } else if (id == R.id.nav_scanner) {
            ScannerFragment scannerFragment = new ScannerFragment();
            scannerFragment.setMainActivityInstance(this);
            setFragment(scannerFragment, scannerFragment.TAG);
        } else if (id == R.id.nav_pannier) {
            PanierFragment panierFragment = new PanierFragment();
            panierFragment.setMainActivityInstance(this);
            setFragment(panierFragment, panierFragment.TAG);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment, String Tag) {
        int entry = getSupportFragmentManager().getBackStackEntryCount();
        String fragmentName = null;

        if (entry > 0) {
            fragmentName = getSupportFragmentManager().getBackStackEntryAt(entry - 1).getName();
            if (fragmentName != Tag) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, Tag)
                        .addToBackStack(Tag)
                        .commit();
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, Tag)
                    .addToBackStack(Tag)
                    .commit();
        }
        System.out.println("Fragment name :: " + fragmentName);
        Log.w("Number of entry::", "" + entry);
    }

    private void initFragmentParentInstance(Fragment fragment, String Tag){
        if (Tag == ScannerFragment.TAG) {
            System.out.println("Parent instance 1");
            ((ScannerFragment)fragment).setMainActivityInstance(this);
        } else if (Tag == CategoriesFragment.TAG) {
            System.out.println("Parent instance 2");
            ((CategoriesFragment)fragment).setMainActivityInstance(this);
        }
    }

    public void setAppBarMenu(int id) {
        navigationView.setCheckedItem(id);
    }

    public void setFirstView() {
        if (findViewById(R.id.fragment_container) != null &&
                getSupportFragmentManager().getBackStackEntryCount() == 0) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            categoriesFragment.setMainActivityInstance(this);
            setFragment(categoriesFragment, categoriesFragment.TAG);
        }
        int entry = getSupportFragmentManager().getBackStackEntryCount();
        if (entry > 0) {
            Fragment currentFrag = getSupportFragmentManager().getFragments().get(entry - 1);
            initFragmentParentInstance(currentFrag, currentFrag.getTag());
        }
    }
}

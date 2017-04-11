package com.example.oliviermedec.pducmaterial;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.oliviermedec.pducmaterial.Fragment.Categories.CategoriesFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Panier.Panier;
import com.example.oliviermedec.pducmaterial.Fragment.Panier.PanierFragment;
import com.example.oliviermedec.pducmaterial.Fragment.Scanner.ScannerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView = null;
    public FloatingActionButton fab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(Scanner);

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
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            setFragment(new CategoriesFragment(), CategoriesFragment.TAG);
        } else if (id == R.id.nav_scanner) {
            setFragment(new ScannerFragment(), ScannerFragment.TAG);
        } else if (id == R.id.nav_pannier) {
            setFragment(new PanierFragment(), PanierFragment.TAG);
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

    public void setAppBarMenu(int id) {
        navigationView.setCheckedItem(id);
    }

    public void setFirstView() {
        if (findViewById(R.id.fragment_container) != null &&
                getSupportFragmentManager().getBackStackEntryCount() == 0) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            setFragment(categoriesFragment, categoriesFragment.TAG);
        }
    }

    public void setPanierFabListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Panier(getApplicationContext()).getPanier() != null) {
                    DialogPanier();
                } else {
                    Snackbar.make(view, getResources().getString(R.string.no_products), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setScannerFabListener() {
        fab.setOnClickListener(Scanner);
    }

    private View.OnClickListener Scanner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Snackbar.make(view, "Ceci est le boutton pour scanner.", Snackbar.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                setFragment(new ScannerFragment(), ScannerFragment.TAG);
            } else {
                /*new MaterialDialog.Builder(getApplicationContext())
                        .title(R.string.error)
                        .content(R.string.camera_fail)
                        .positiveText(R.string.agree)
                        .show();*/
                System.out.println(getResources().getString(R.string.camera_fail));
            }
        }
    };

    public void DialogPanier() {
        new MaterialDialog.Builder(MainActivity.this)
                .titleColorRes(R.color.colorPrimary)
                .contentColor(Color.WHITE) // notice no 'res' postfix for literal color
                .backgroundColorRes(R.color.blueGrey)
                .positiveColorRes(R.color.white)
                .neutralColorRes(R.color.white)
                .negativeColorRes(R.color.white)
                .widgetColorRes(R.color.colorPrimary)
                .buttonRippleColorRes(R.color.colorPrimary)
                .inputRangeRes(4, 80, R.color.colorPrimary)
                .title(R.string.panier)
                .content("Total : " + new Panier(getApplicationContext()).getTotalFacture() + "\n\n" + getResources().getString(R.string.insert_name_ask))
                .positiveText(R.string.allow)
                .negativeText(R.string.cancel)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.input_name_hint, R.string.input_name_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        System.out.println(input);
                        final MaterialDialog EndAchat = new  MaterialDialog.Builder(MainActivity.this)
                                .titleColorRes(R.color.colorPrimary)
                                .contentColor(Color.WHITE) // notice no 'res' postfix for literal color
                                .backgroundColorRes(R.color.blueGrey)
                                .positiveColorRes(R.color.white)
                                .neutralColorRes(R.color.white)
                                .negativeColorRes(R.color.white)
                                .widgetColorRes(R.color.colorPrimary)
                                .buttonRippleColorRes(R.color.colorPrimary)
                                .title(R.string.panier)
                                .cancelable(false)
                                .content("Mr/Mlle " + input + " " + getResources().getString(R.string.get_your_stuff))
                                .show();

                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (EndAchat.isShowing()) {
                                    EndAchat.dismiss();
                                    new Panier(getApplicationContext()).deletePanier();
                                    finish();
                                }
                            }
                        };
                        final Handler handler = new Handler();
                        dialog.dismiss();
                        handler.postDelayed(runnable, 10000);
                    }
                })
                .show();
    }
}

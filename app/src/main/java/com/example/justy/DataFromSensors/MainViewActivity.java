package com.example.justy.DataFromSensors;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;


public class MainViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private OkHttpClient client;
    private int amountOfColumns;
    private ArrayList<String> columnsNames;
    private Menu newMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SplashActivity.avaliableStationsRequest.parseJsonToVariables();
        SplashActivity.avaliableStationsRequest.printAll();

        SplashActivity.avaliableDataRequest.parseJsonToVariables();
        SplashActivity.avaliableDataRequest.printAll();
        System.out.println("tuuuuuuu");
        columnsNames = SplashActivity.avaliableDataRequest.getAvaliableColumnsNames();
        amountOfColumns = SplashActivity.avaliableDataRequest.getAvaliableColumnsNames().size();

        GlobalVariables.currentColumn = columnsNames.get(0);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerId);
        spinner.setPrompt("Wybierz stację");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, SplashActivity.avaliableStationsRequest.getStationsNames());
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = spinner.getSelectedItem().toString();
                TextView myText = (TextView) view;
                Toast.makeText(getApplicationContext(), "Wybrałeś: " + myText.getText(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, 4);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        newMenu = navigationView.getMenu();
        newMenu.clear();
        for (int i = 0; i < amountOfColumns; i++) {
            newMenu.add(0, i, Menu.NONE, columnsNames.get(i));
        }

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        for (int i = 0; i < amountOfColumns; i++) {
            if (newMenu.getItem(i).getItemId() == id) {
                GlobalVariables.currentColumn = columnsNames.get(i);
                System.out.println(GlobalVariables.currentColumn);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerId);
                MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, 4);
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
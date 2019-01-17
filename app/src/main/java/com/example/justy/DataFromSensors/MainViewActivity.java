package com.example.justy.DataFromSensors;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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


public class MainViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String currentColumn;
    public static String currentStation;

    private ArrayList<String> columnsNames;
    private ArrayList<String> stationsNames;

    private Menu newMenu;
    private MyFragmentPagerAdapter adapter;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private TextView chartSignature;

    private CurrentDataDialog currentDataDialog;
    private ErrorDialog errorDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        loadData();
        createSpinnerWithSations();
        manageFragments();
        createMenuWithMeasurements();
        createChartSignature();
    }

    public void loadData(){

        SplashActivity.avaliableDataRequest.parseJsonToVariables();
        columnsNames = SplashActivity.avaliableDataRequest.getAvaliableColumnsNames();
        stationsNames = SplashActivity.avaliableStationsRequest.getStationsNames();

        try {
            currentColumn = columnsNames.get(0);
            currentStation = stationsNames.get(0);
        }catch (Exception e){
            errorDialog = new ErrorDialog();
            errorDialog.showErrorDialog(this);
        }
    }

    public void createSpinnerWithSations(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerId);
        spinner.setPrompt("Wybierz stację");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, stationsNames);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = spinner.getSelectedItem().toString();
                TextView myText = (TextView) view;
                Toast.makeText(getApplicationContext(), "Wybrałeś: " + myText.getText(), Toast.LENGTH_SHORT).show();
                currentStation = (String) myText.getText();
                tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void createMenuWithMeasurements(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        newMenu = navigationView.getMenu();
        newMenu.clear();
        for (int i = 0; i < columnsNames.size(); i++) {
            newMenu.add(0, i, Menu.NONE, columnsNames.get(i)); //i+1
            newMenu.getItem(i).setIcon(R.drawable.baseline_insert_chart_outlined_24);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void manageFragments(){
        viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, 4);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void createChartSignature(){
        chartSignature = findViewById(R.id.chartSignatureTextViewId);
        chartSignature.setText(currentColumn);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            for (int i = 0; i < columnsNames.size(); i++) {
                if (newMenu.getItem(i).getItemId() == id) {
                    currentColumn = columnsNames.get(i);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.select();
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                    chartSignature.setText(currentColumn);
                }
            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void showTableWithCurrentData(View view) {
        currentDataDialog = new CurrentDataDialog(this, columnsNames);
    }
}
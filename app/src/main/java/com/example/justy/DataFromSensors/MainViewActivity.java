package com.example.justy.DataFromSensors;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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


        GlobalVariables.avaliableStationsRequest.parseJsonToVariables();
        GlobalVariables.avaliableStationsRequest.printAll();

        GlobalVariables.avaliableDataRequest.parseJsonToVariables();
        GlobalVariables.avaliableDataRequest.printAll();
        System.out.println("tuuuuuuu");
        columnsNames = GlobalVariables.avaliableDataRequest.getAvaliableColumnsNames();
        amountOfColumns = GlobalVariables.avaliableDataRequest.getAvaliableColumnsNames().size();

        GlobalVariables.currentColumn = columnsNames.get(0);

        //drawChart("PM10");
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerId);
//        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
//        viewPager.setAdapter(adapter);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
//        tabLayout.setupWithViewPager(viewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayoutId);

        tabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener(){
            public void  onTabSelected(TabLayout.Tab tab){
                String tabId = (String)tab.getText();
                System.out.println(tabId);
//                MainActivity.this.getTabContent(tabId);
                switch (tabId) {
                    case "Day":
                        drawChart(GlobalVariables.currentColumn, GlobalVariables.postRequestPerDay);
                    case "Week":
                        drawChart(GlobalVariables.currentColumn, GlobalVariables.postRequestPerWeek);
                    case "Month":
                        drawChart(GlobalVariables.currentColumn, GlobalVariables.postRequestPerMonth);
                    case "Year":
                        drawChart(GlobalVariables.currentColumn, GlobalVariables.postRequestPerYear);
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        newMenu = navigationView.getMenu();
        newMenu.clear();
        for(int i=0; i<amountOfColumns; i++){
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

        for(int i=0; i<amountOfColumns; i++){
            if(newMenu.getItem(i).getItemId() == id){
                GlobalVariables.currentColumn = columnsNames.get(i);
                System.out.println(GlobalVariables.currentColumn);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
                drawChart(columnsNames.get(i), GlobalVariables.postRequestPerDay);
//                DayFragmenter dayFragmenter = new DayFragmenter();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().attach(dayFragmenter).commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void drawChart(String columnName, PostRequest postRequest){

        LineChart chart = (LineChart) findViewById(R.id.chart);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        ArrayList<Entry> entries = new ArrayList<>();
        try {
            ParseJSON parseJSON = new ParseJSON();
            parseJSON.getDataFromJSON(postRequest.getResponseArray(), columnName);
            for(int i=0; i<parseJSON.getTimeArray().size(); i++){
                entries.add(new Entry(parseJSON.getTimeArray().get(i), parseJSON.getFloatArray().get(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        LineDataSet set = new LineDataSet(entries, "Customized values");
        setChartProperties(set);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData((dataSets));

//        data.setHighlightEnabled(true);

        XAxis xAxis = chart.getXAxis();
        generateXAxis(xAxis);
        xAxis.setValueFormatter(new TimeAxisValueFormatter());

        chart.setDrawMarkers(true);
        chart.setMarker(markerView(getApplicationContext(), chart.getWidth()));
        chart.setData(data);
        chart.invalidate();
        chart.animateX(1000);
    }

    void setChartProperties(LineDataSet set){
        set.setFillAlpha(110);
        set.setLineWidth(3f);
        set.setValueTextSize(10f);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(true);
        //set.setValueTextColor(Color.GREEN);
    }

    void generateXAxis(XAxis xAxis){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45f);
        xAxis.setLabelCount(12, true);
    }

    public CustomMarkerView markerView(Context context, int width)
    {
        CustomMarkerView mv = new CustomMarkerView(context, R.layout.custom_marker);
//        if(-mv.getWidth() > width){
//            mv.setOffset(- mv.getWidth() / 2 - 30, -mv.getHeight()-20);
//        }
//        else {
            mv.setOffset(- mv.getWidth() / 2, -mv.getHeight()-20);
//        }
        return mv;
    }
}

class TimeAxisValueFormatter implements IAxisValueFormatter {
        private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            Date s = new Date(new Float(value).longValue());
            return mFormat.format(s);
        }
}

class TimeValueFormatter {
    private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");

    public String getFormattedValue(float value) {
        Date s = new Date(new Float(value).longValue());
        return mFormat.format(s);
    }
}

class CustomMarkerView extends MarkerView
{
    private TextView markerTextView;

    public CustomMarkerView(Context context, int layoutResource)
    {
        super(context, layoutResource);
        markerTextView = findViewById(R.id.markerText);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        markerTextView.setText("Time: " + new TimeValueFormatter().getFormattedValue(e.getX()) + "\n" +"Value: " + e.getY()); // set the entry-value as the display text
    }

}

//    public JSONArray responseJSON;
//
//    public JSONArray getDataFromServer(String timeRange, String station) {
//
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        OkHttpClient client = new OkHttpClient();
//
//        final String url = "https://station-controll-123.localtunnel.me/request_data/";
//
//        String jsonToSend = createJsonToSend(timeRange, station);
//        System.out.println(jsonToSend);
//
//        RequestBody body = RequestBody.create(JSON, jsonToSend);
//
//        final Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String myResponse;
//                        try {
//                            myResponse = response.body().string();
//                            String responseAfterRemoveBackslash = myResponse.replace("\\\"", "\"");
//                            responseAfterRemoveBackslash = responseAfterRemoveBackslash.substring(1, responseAfterRemoveBackslash.length() - 1);
//                            responseJSON = new JSONArray(responseAfterRemoveBackslash);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                });
//            }
//        });
//        return responseJSON;
//    }
//
//    String createJsonToSend(String timeRange, String station){
//        return "{\"Time\": \"" + timeRange + "\", \"Station\":\"" + station + "\"}";
//    }
//
//    ArrayList<Timestamp> time = new ArrayList<>();
//
//        time.add(java.sql.Timestamp.valueOf("2018-12-26 00:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 01:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 02:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 03:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 04:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 05:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 06:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 07:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 08:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 09:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 10:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 11:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 12:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 13:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 14:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 15:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 16:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 17:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 18:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 19:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 20:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 21:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 22:00:00.0"));
//                time.add(java.sql.Timestamp.valueOf("2018-12-26 23:00:00.0"));
//
//                ArrayList<Float> values = new ArrayList<>();
//        values.add(10.2f);
//        values.add(10.3f);
//        values.add(10.8f);
//        values.add(11.0f);
//        values.add(11.4f);
//        values.add(11.5f);
//        values.add(12.2f);
//        values.add(12.1f);
//        values.add(12.4f);
//        values.add(13.8f);
//        values.add(13.6f);
//        values.add(13.2f);
//        values.add(14.1f);
//        values.add(14.3f);
//        values.add(14.5f);
//        values.add(14.6f);
//        values.add(15.3f);
//        values.add(15.2f);
//        values.add(15.5f);
//        values.add(16.6f);
//        values.add(16.7f);
//        values.add(15.2f);
//        values.add(14.5f);
//        values.add(13.3f);

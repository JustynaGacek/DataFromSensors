package com.example.justy.DataFromSensors;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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


        GlobalVariables.avaliableDataRequest.parseJsonToVariables();
        GlobalVariables.avaliableDataRequest.printAll();
        drawChart();

//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        OkHttpClient client = new OkHttpClient();
//
//        String url = "https://green-earwig-99.localtunnel.me/request_data/";
//        String json = "{\"Time\": \"month\"}";
//
//        RequestBody body = RequestBody.create(JSON, json);
//
//        Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                if(response.isSuccessful()){
//                    final String myResponse = response.body().string();
//
//                    System.out.println("tuuuuuuuuuu");
//
//                            //System.out.println(myResponse.replace("\\\"", "\""));
//
//                     try {
//
//                         String afterRemove = myResponse.replace("\\\"", "\"");
//                         afterRemove = afterRemove.substring(1, afterRemove.length()-1);
//                         JSONArray ja = new JSONArray(afterRemove);
//                         System.out.println(ja);
//
//                     } catch (JSONException e) {
//                         e.printStackTrace();
//                         Log.e("JSON Parser", "Error parsing data " + e.toString());
//                     }
//
//                    }
//            }
//        });

//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                // Do network action in this function
//
//                PostRequest postRequest = new PostRequest();
//                postRequest.post("day", "Gmina Skawina, Zelczyna");
////                System.out.println(postRequest.getResponseArray());
//
//                try {
//                    ParseJSON parseJSON = new ParseJSON();
//                    parseJSON.parseJson(postRequest.getResponseArray());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////
//            }
//        }).start();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void drawChart(){

        LineChart chart = (LineChart) findViewById(R.id.chart);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        ArrayList<Entry> entries = new ArrayList<>();
        try {
            ParseJSON parseJSON = new ParseJSON();
            parseJSON.getDataFromJSON(GlobalVariables.postRequest.getResponseArray(), "Temperatura");
            for(int i=0; i<parseJSON.getTimeArray().size(); i++){
                entries.add(new Entry(parseJSON.getTimeArray().get(i), parseJSON.getFloatArray().get(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        LineDataSet set = new LineDataSet(entries, "Customized values");
        set.setFillAlpha(110);
        set.setLineWidth(3f);
        set.setValueTextSize(10f);
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(true);
        set.setHighlightEnabled(true);
        //set.setValueTextColor(Color.GREEN);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData((dataSets));

        data.setHighlightEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45f);
        xAxis.setLabelCount(12, true);

//        chart.getAxisRight().setEnabled(false);


        xAxis.setValueFormatter(new TimeAxisValueFormatter());

        chart.setDrawMarkers(true);
        chart.setMarker(markerView(getApplicationContext(), chart.getWidth()));
        chart.setData(data);
        chart.invalidate();
        chart.animateX(1000);

///////////////////////////////////////////////////////////////////////////////////////////

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

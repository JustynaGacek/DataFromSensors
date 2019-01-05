package com.example.justy.DataFromSensors;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;


public class YearFragmenter extends Fragment {


    public YearFragmenter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_year_fragmenter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        LineChart chart = (LineChart) getView().findViewById(R.id.chartYear);
        DrawChart drawChart = new DrawChart(SplashActivity.jsonArrayYear.get(SplashActivity.avaliableStationsRequest.getStationsNames().indexOf(GlobalVariables.currentStation)), GlobalVariables.currentColumn);
//        DrawChart drawChart = new DrawChart(SplashActivity.jsonArrayYear.get(0), GlobalVariables.currentColumn);
        drawChart.drawChart(chart, getContext(), new TimeAxisValueFormatterForYear());
    }

//    public void drawChart(String columnName, LineChart chart){
//
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(true);
//
//        ArrayList<Entry> entries = new ArrayList<>();
//        try {
//            ParseJSON parseJSON = new ParseJSON();
//            parseJSON.clearArrays();
//            parseJSON.getDataFromJSON(SplashActivity.jsonArrayYear, columnName);
//            for(int i=0; i<parseJSON.getTimeArray().size(); i++){
//                entries.add(new Entry(parseJSON.getTimeArray().get(i), parseJSON.getFloatArray().get(i)));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        LineDataSet set = new LineDataSet(entries, "Customized values");
//        setChartProperties(set);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set);
//
//        LineData data = new LineData((dataSets));
//
////        data.setHighlightEnabled(true);
//
//        XAxis xAxis = chart.getXAxis();
//        generateXAxis(xAxis);
//        xAxis.setValueFormatter(new TimeAxisValueFormatterForDay());
//
//        chart.setDrawMarkers(true);
//        //chart.setMarker(markerView(getApplicationContext(), chart.getWidth()));
//        chart.setData(data);
//        chart.invalidate();
//        chart.animateX(1000);
//    }
//
//    void setChartProperties(LineDataSet set){
//        set.setFillAlpha(110);
//        set.setLineWidth(3f);
//        set.setValueTextSize(10f);
//        set.setDrawCircles(false);
//        set.setDrawValues(false);
//        set.setHighlightEnabled(true);
//        //set.setValueTextColor(Color.GREEN);
//    }
//
//    void generateXAxis(XAxis xAxis){
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(10f);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setLabelRotationAngle(-45f);
//        xAxis.setLabelCount(12, true);
//    }

}

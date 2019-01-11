package com.example.justy.DataFromSensors;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DrawChart {

    public static Long currentTimestamp;

    private JSONArray dataJsonArray;
    private String columnName;
    private ParseJSON parseJSON;
    private CustomMarkerView markerView;
    private String currentUnit;

    public DrawChart(JSONArray jsonArray, String columnName){
        dataJsonArray = jsonArray;
        parseJSON = new ParseJSON();
        this.columnName = columnName;
    }

    public void drawChart(LineChart chart, Context context, IAxisValueFormatter valueFormatter){

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        ArrayList<Entry> entries = new ArrayList<>();
        try {
            parseJSON.clearArrays();
            parseJSON.getDataFromJSON(dataJsonArray, columnName);
            currentUnit = parseJSON.getUnit();
            for(int i=0; i<parseJSON.getTimeArray().size(); i++){
                if(i==0) { currentTimestamp = parseJSON.getTimeArray().get(i); }
                entries.add(new Entry(parseJSON.getTimeArray().get(i) - currentTimestamp, parseJSON.getFloatArray().get(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        LineDataSet set = new LineDataSet(entries, "Jednostka: " + currentUnit);

        setChartProperties(set);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData((dataSets));

        XAxis xAxis = chart.getXAxis();
        generateXAxis(xAxis, valueFormatter);

        chart.setDrawMarkers(true);
        chart.setMarker(markerView(context, chart.getWidth()));
        chart.setData(data);

        chart.animateX(1000);

        chart.invalidate();
    }

    void setChartProperties(LineDataSet set){
        set.setFillAlpha(110);
        set.setLineWidth(1f);
        set.setValueTextSize(10f);
        set.setDrawCircles(true);
        set.setDrawValues(false);
        set.setHighlightEnabled(true);
//        set.addColor(Color.parseColor("#303F9F")); // Your Blue
//        set.setCircleColor(Color.parseColor("#303F9F"));
//        set.setCircleRadius(3f);
//        set.setDrawCircleHole(true);
    }

    void generateXAxis(XAxis xAxis, IAxisValueFormatter valueFormatter){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45f);
        xAxis.setValueFormatter(valueFormatter);
    }

    public CustomMarkerView markerView(Context context, int width)
    {
        markerView = new CustomMarkerView(context, R.layout.custom_marker);
        markerView.setOffset(- markerView.getWidth() / 2, -markerView.getHeight()-20);
        return markerView;
    }

}

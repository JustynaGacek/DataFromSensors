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

    private JSONArray dataJsonArray;
    private String columnName;
    private ParseJSON parseJSON;

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
            for(int i=0; i<parseJSON.getTimeArray().size(); i++){
                if(i==0) { GlobalVariables.currentTimestamp = parseJSON.getTimeArray().get(i); }
                entries.add(new Entry(parseJSON.getTimeArray().get(i) - GlobalVariables.currentTimestamp, parseJSON.getFloatArray().get(i)));
                System.out.print(entries.get(i).getX());
                System.out.print(" ");
                System.out.print(entries.get(i).getY());
                System.out.println();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        LineDataSet set = new LineDataSet(entries, "Customized values");
        setChartProperties(set);
//        set.setCubicIntensity(0.5f);
//
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData((dataSets));

//        data.setHighlightEnabled(true);

        XAxis xAxis = chart.getXAxis();
        generateXAxis(xAxis);
        xAxis.setValueFormatter(valueFormatter);

        chart.setDrawMarkers(true);
        chart.setMarker(markerView(context, chart.getWidth()));
        chart.setData(data);
        chart.invalidate();
        chart.animateX(1000);
    }

    void setChartProperties(LineDataSet set){
//        set.setFillAlpha(110);
        set.setLineWidth(2f);
        set.setValueTextSize(10f);
        set.setDrawCircles(true);
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
//        xAxis.setLabelCount(12, true);
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

//    public Long getReferenceTimestamp(){
//        return referenceTimestamp;
//    }
}

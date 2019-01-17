package com.example.justy.DataFromSensors;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;


public class YearFragment extends Fragment {

    private TimeAxisValueFormatterForYear timeAxisValueFormatter;
    private DrawChart drawChart;

    public YearFragment() {
        timeAxisValueFormatter = new TimeAxisValueFormatterForYear();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_year_fragmenter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        LineChart chart = (LineChart) getView().findViewById(R.id.chartYear);
        try {
            drawChart = new DrawChart(SplashActivity.jsonArrayYear.get(SplashActivity.avaliableStationsRequest.getStationsNames().indexOf(MainViewActivity.currentStation)), MainViewActivity.currentColumn);
            drawChart.drawChart(chart, getContext(),timeAxisValueFormatter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

package com.example.justy.DataFromSensors;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAxisValueFormatterForYear implements IAxisValueFormatter {
    private SimpleDateFormat format;

    TimeAxisValueFormatterForYear(){
        format = new SimpleDateFormat("W 'tydz.' MMM");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date s = new Date(new Float(value + DrawChart.currentTimestamp).longValue());
        return format.format(s);
    }
}

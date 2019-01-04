package com.example.justy.DataFromSensors;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAxisValueFormatterForMonth implements IAxisValueFormatter {
    private SimpleDateFormat mFormat = new SimpleDateFormat("d. MMM");

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date s = new Date(new Float(value + GlobalVariables.currentTimestamp).longValue());
        return mFormat.format(s);
    }
}

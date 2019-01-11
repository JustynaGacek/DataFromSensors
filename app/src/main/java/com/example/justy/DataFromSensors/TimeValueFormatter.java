package com.example.justy.DataFromSensors;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeValueFormatter {
    private SimpleDateFormat mFormat = new SimpleDateFormat("dd.MM.yyyy '\n'HH:mm:ss");

    public String getFormattedValueForDay(float value) {
        Date s = new Date(new Float(value + DrawChart.currentTimestamp).longValue());
        return mFormat.format(s);
    }

}

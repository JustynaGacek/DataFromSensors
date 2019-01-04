package com.example.justy.DataFromSensors;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeValueFormatter {
    private SimpleDateFormat mFormat = new SimpleDateFormat("dd.MM.yyyy '\n'HH:mm:ss");

    public String getFormattedValueForDay(float value) {
        Date s = new Date(new Float(value + GlobalVariables.currentTimestamp).longValue());
        return mFormat.format(s);
    }

//    public String getFormattedValueForWeek(float value) {
//        SimpleDateFormat format = new SimpleDateFormat()
//        Date s = new Date(new Float(value).longValue());
//        return mFormat.format(s);
//    }
}

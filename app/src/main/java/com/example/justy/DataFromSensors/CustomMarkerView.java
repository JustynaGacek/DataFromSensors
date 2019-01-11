package com.example.justy.DataFromSensors;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class CustomMarkerView extends MarkerView
{
    private TextView markerTextView;
    private TimeValueFormatter timeValueFormatter;

    public CustomMarkerView(Context context, int layoutResource)
    {
        super(context, layoutResource);
        timeValueFormatter = new TimeValueFormatter();
        markerTextView = findViewById(R.id.markerText);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        markerTextView.setText("Czas: " + timeValueFormatter.getFormattedValueForDay(e.getX()) + "\n" +"Wartość: \n" + e.getY());
    }

}

package com.example.justy.DataFromSensors;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class CustomMarkerView extends MarkerView
{
    private TextView markerTextView;
    private String currentTab;

    public CustomMarkerView(Context context, int layoutResource)
    {
        super(context, layoutResource);
        markerTextView = findViewById(R.id.markerText);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        markerTextView.setText("Czas: " + new TimeValueFormatter().getFormattedValueForDay(e.getX()) + "\n" +"Wartość: \n" + e.getY()); // set the entry-value as the display text
    }

}

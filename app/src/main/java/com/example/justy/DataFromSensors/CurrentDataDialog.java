package com.example.justy.DataFromSensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class CurrentDataDialog {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("ResourceType")
    CurrentDataDialog(Context context, ArrayList<String> columsNames){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.current_data_dialog, null);
        builder.setView(view);

        TableLayout tableLayout = (TableLayout)view.findViewById(R.id.tableData);

        ParseJSON parseJSON = new ParseJSON();
        for(int i=0; i<columsNames.size(); i++) {
            try {
                parseJSON.clear();
                parseJSON.getTheNewestMeasurement(SplashActivity.jsonArrayYear.get(SplashActivity.avaliableStationsRequest.getStationsNames().indexOf(MainViewActivity.currentStation)), columsNames.get(i));

                TableRow tableRow = new TableRow(context);
                tableRow.setBackgroundResource(R.layout.table_border);

                TextView a = new TextView(context);
                TextView b = new TextView(context);
                TextView c = new TextView(context);
                TextView d = new TextView(context);

                a.setPadding(10, 30, 10, 20);
                b.setPadding(10, 30, 10, 20);
                c.setPadding(10, 30, 10, 20);
                d.setPadding(10, 30, 10, 20);

                a.setText(columsNames.get(i));
                a.setTypeface(null, Typeface.BOLD);
                a.setGravity(Gravity.CENTER_HORIZONTAL);
                a.setTextColor(Color.WHITE);

                String time = parseJSON.getNewestMeasurementTime();
                String value = parseJSON.getNewestMeasurementValue();
                String unit = parseJSON.getNewestMeasurementUnit();
                if (time!=null && value!=null && unit!=null){
                    b.setText(time.replace(" ", "\n"));
                    c.setText(value);
                    d.setText(unit);
                }
                else{
                    b.setText("-");
                    c.setText("-");
                    d.setText("-");
                }

                b.setGravity(Gravity.CENTER_HORIZONTAL);
                b.setTextColor(Color.WHITE);
                c.setGravity(Gravity.CENTER_HORIZONTAL);
                c.setTextColor(Color.WHITE);
                d.setGravity(Gravity.CENTER_HORIZONTAL);
                d.setTextColor(Color.WHITE);

                tableRow.addView(a);
                tableRow.addView(b);
                tableRow.addView(c);
                tableRow.addView(d);

                tableLayout.addView(tableRow);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


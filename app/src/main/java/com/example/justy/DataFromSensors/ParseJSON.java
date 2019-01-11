package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ParseJSON {

    private ArrayList<Long> timeArray;
    private ArrayList<Float> valuesArray;
    private String unit;

    private String newestMeasurementValue;
    private String newestMeasurementTime;
    private String newestMeasurementUnit;

    ParseJSON(){
        timeArray = new ArrayList<>();
        valuesArray = new ArrayList<>();
        newestMeasurementValue = "aaa";
        newestMeasurementTime = "vvv";
        newestMeasurementUnit = "ppp";
    }

//    String getUnitFromJSON(JSONArray jsonArray, String columnName) throws JSONException {
//        if(jsonArray!=null){
//            JSONObject object;
//            JSONObject objectFields;
//            int i = 0;
//            do {
//                object = jsonArray.getJSONObject(i);
//                objectFields = (JSONObject) object.get("fields");
//                unit = objectFields.getString(columnName + "_jednostka");
//                i++;
//            }while (unit.equals("null"));
//        }
//        return unit;
//    }

    void getTheNewestMeasurement(JSONArray jsonArray, String columnName) throws JSONException {
        if(jsonArray!=null) {
            int i = jsonArray.length()-1;
            String value = null;
            String time = null;
            String unit = null;
            do {
                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject objectFields = (JSONObject) object.get("fields");
                value = objectFields.getString(columnName);
                time = objectFields.getString("Czas_pomiaru");
                unit = objectFields.getString(columnName + "_jednostka");
                i--;
            }while ((value.equals("null") || time.equals("null") || unit.equals("null")) && i!=0);

            if(!time.equals("null") && !value.equals("null") && !unit.equals("null")) {
                newestMeasurementTime = time.replace('T', ' ').replace("Z", "");
                newestMeasurementValue = value;
                newestMeasurementUnit = unit;
            }

//            System.out.print(newestMeasurementTime);
//            System.out.print(" ");
//            System.out.print(newestMeasurementValue);
//            System.out.print(" ");
//            System.out.print(newestMeasurementUnit);
//            System.out.println();
        }
    }

    void clear(){
        newestMeasurementValue = null;
        newestMeasurementTime = null;
        newestMeasurementUnit = null;
    }

    void getDataFromJSON(JSONArray jsonArray, String columnName) throws JSONException {

        if(jsonArray!=null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject objectFields = (JSONObject) object.get("fields");
                String columnValue = objectFields.getString(columnName);
                String timeValue = objectFields.getString("Czas_pomiaru");
                String unitValue = objectFields.getString(columnName + "_jednostka");
                if (!columnValue.equals("null") && !timeValue.equals("null") && !unitValue.equals("null")) {
                    unit = unitValue;
                    timeArray.add(Timestamp.valueOf(timeValue.replace('T', ' ').replace("Z", "")).getTime());
                    valuesArray.add(Float.parseFloat(columnValue));
                }
            }
        }
    }

    String getUnit(){
        return unit;
    }

    ArrayList<Long> getTimeArray(){
        return timeArray;
    }

    ArrayList<Float> getFloatArray(){
        return valuesArray;
    }

    void clearArrays() {
        timeArray.clear();
        valuesArray.clear();
    }

    public String getNewestMeasurementValue() {
        return newestMeasurementValue;
    }

    public String  getNewestMeasurementTime() {
        return newestMeasurementTime;
    }

    public String getNewestMeasurementUnit() {
        return newestMeasurementUnit;
    }

}


//                System.out.print(i);
//                System.out.print(" ");
//                System.out.print(value);
//                System.out.print(" ");
//                System.out.print(time);
//                System.out.print(" ");
//                System.out.print(unit);
//                System.out.println();
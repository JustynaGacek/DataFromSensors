package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJSON {

    private ArrayList<Long> timeArray;
    private ArrayList<Float> valuesArray;

    ParseJSON(){
        timeArray = new ArrayList<>();
        valuesArray = new ArrayList<>();
    }

    void getDataFromJSON(JSONArray jsonArray, String columnName) throws JSONException {

        if(jsonArray!=null) {
            System.out.print("Parsuje");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject objectFields = (JSONObject) object.get("fields");
                String columnValue = objectFields.getString(columnName);
                String timeValue = objectFields.getString("Czas_pomiaru").replace('T', ' ');
                if (!columnValue.equals("null") && !timeValue.equals("null")) {
                    timeArray.add(java.sql.Timestamp.valueOf(timeValue.replace('T', ' ').replace("Z", "")).getTime());
                    //System.out.println(timeArray.get(i));
                    valuesArray.add(Float.parseFloat(columnValue));
                    //System.out.println(valuesArray.get(i));
                }
            }
        }
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

}

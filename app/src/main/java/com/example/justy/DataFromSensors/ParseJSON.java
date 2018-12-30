package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {

    void parseJson(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            JSONObject object1 = (JSONObject) object.get("fields");
            String d = object1.getString("Temperature");
            if(!d.equals("null")){
                GlobalVariables.temperature.add(Float.parseFloat(d));
                System.out.println(GlobalVariables.temperature.get(i));
            }

//            System.out.println(d);

        }
    }


}

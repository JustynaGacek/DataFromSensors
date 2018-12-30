package com.example.justy.DataFromSensors;

import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.ArrayList;

public class GlobalVariables {
    public static JSONArray responseArray;

    public static ArrayList<Float> temperature = new ArrayList<>();
    public static ArrayList<Timestamp> timeTemperature = new ArrayList<>();
}

package com.example.justy.DataFromSensors;

public class GlobalVariables {
    public static GetAvaliableStationsRequest avaliableStationsRequest = new GetAvaliableStationsRequest();
    public static GetAvailableDataRequest avaliableDataRequest = new GetAvailableDataRequest();

    public static PostRequest postRequestPerDay = new PostRequest();
//    public static ParseJSON parseJSON = new ParseJSON();
    public static PostRequest postRequestPerWeek = new PostRequest();
    public static PostRequest postRequestPerMonth = new PostRequest();
    public static PostRequest postRequestPerYear = new PostRequest();

    public static String currentColumn;
}

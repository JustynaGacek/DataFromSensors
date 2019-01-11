package com.example.justy.DataFromSensors;

public abstract class PrepareResponseBase {

    String transformResponseString(String response){
        String responseAfterRemoveBackslash = response.replace("\\\"", "\"");
        responseAfterRemoveBackslash = responseAfterRemoveBackslash.substring(1, responseAfterRemoveBackslash.length() - 1);
        return responseAfterRemoveBackslash;
    }

}

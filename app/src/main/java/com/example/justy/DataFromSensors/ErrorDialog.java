package com.example.justy.DataFromSensors;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

public class ErrorDialog {

    public static void showErrorDialog(Activity activity) {

        String title = "Error";
        String message = "An error occurred";

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage("Problem z łądowaniem danych")
//                .setTitle("Błąd")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }
}

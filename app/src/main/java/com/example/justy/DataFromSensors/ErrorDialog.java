package com.example.justy.DataFromSensors;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class ErrorDialog {

    public void showErrorDialog(Activity activity) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Czy chcesz zamknąć aplikację?");
        alertDialogBuilder
                .setMessage("Wystąpił problem z połączeniem z serwerem. Sprawdź czy masz włączony Internet w telefonie. Jeśli tak, sporóbuj poźniej, gdy awaria serwera zostanie usunięta. Przepraszamy!")
                .setCancelable(false)
                .setPositiveButton("Zakończ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}

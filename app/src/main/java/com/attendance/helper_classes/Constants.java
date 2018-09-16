package com.attendance.helper_classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.attendance.R;

import java.util.ArrayList;

public class Constants {

    public final static String EMAIL_PATTERN = "";
    public final static String PHONE_PATTERN = "";
    public final static String NAME = "";

    public static void showAlertDialog(Context context, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.app_name));
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    alertDialog.dismiss();
                });
        alertDialog.show();
    }
}

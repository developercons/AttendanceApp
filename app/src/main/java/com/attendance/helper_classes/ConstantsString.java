package com.attendance.helper_classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.attendance.R;

import java.util.ArrayList;

public class ConstantsString {

    public final static String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";;
    public final static String PHONE_PATTERN = "";
    public final static String NAME = "";

	public static final ArrayList<String> qualificationList = new ArrayList<>();
	public static final ArrayList<String> semesterList = new ArrayList<>();
	public static final ArrayList<String> coursesList = new ArrayList<>();

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

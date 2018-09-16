package com.attendance.helper_classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import com.attendance.R;

import static android.content.Context.MODE_PRIVATE;

public class PermissionUtils {
    private static final int REQUEST_PERMISSION_SETTING = 1;
    private static boolean stopRecursiveCallSecondTimePermission = true;
    private static boolean isGrant = false;
    private static boolean sentToSettings = false;

    /**
     * this method check permission int three stage
     * 1) if app is install first time and then ask for required permission
     *    to run the app and in that case return false.
     * 2) if first time someone deny the any one of the permission or all the permission
     *    and in that case return false.
     * 3) if app permission deny and clicked on checkbox
     *   (the msg mention on that "don't ask again) and in that case return false.
     * 4) if all the permission is granted then return true.
     * @param activity point to current activity
     * @param permissionList list of permission
     * @param PERMISSION_CONSTANT specific number constant to permission
     * @return if all permission is granted then return true else false
     */
    public static Boolean permissionGranted(Activity activity,
                                            String[] permissionList, int PERMISSION_CONSTANT) {
        SharedPreferences permissionStatus = activity.getSharedPreferences("permissionStatus", MODE_PRIVATE);
        boolean isPermissionNotGranted = false;

        if ( permissionList.length > 0 ) {
            for ( String permission : permissionList ) {
                // if any one of the listed permission is deny then isPermissionTrue is true
                // and we break the loop else continue to check all the permission.
                boolean isTrue = ActivityCompat.checkSelfPermission(activity, permission)
                        != PackageManager.PERMISSION_GRANTED;
                if ( isTrue ) {
                    isPermissionNotGranted = true;
                }
            }

            if ( isPermissionNotGranted ) {
                boolean isPermissionGrant = isShouldShowRequestPermissionRationale(activity, permissionList);
                if ( isPermissionGrant ) {
                    //Show Information about why you need the permission
                    if ( stopRecursiveCallSecondTimePermission )
                        secondTimePermission (activity, permissionList, PERMISSION_CONSTANT);
                }
                else if ( permissionStatus.getAll().size() > 0 && !sentToSettings ) {
                    for ( String permissionName : permissionList ) {
                        if ( permissionStatus.getBoolean(permissionName, false) ) {
                            //Previously Permission Request was cancelled with Don't Ask Again',
                            // Redirect to Settings after showing Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(R.string.app_name);
                            builder.setMessage("This app are need these permissions.");
                            builder.setPositiveButton("Grant", (dialog, which) -> {
                                dialog.cancel();
                                openSettings(activity);
                            });
                            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                            builder.show();
                            break;
                        }
                    }
                }
                else {
                    ActivityCompat.requestPermissions(activity, permissionList, PERMISSION_CONSTANT);
                }
                SharedPreferences.Editor editor = permissionStatus.edit();
                for ( String permission : permissionList ) {
                    editor.putBoolean(permission, true);
                }
                editor.apply();
                setGrant(false);
                return isGranted();
            }
            else {
                //You already have the permission, just go ahead.
                proceedAfterPermission(activity, false);
                setGrant(true);
                return isGranted();
            }
        }
        return false;
    }

    private static void setGrant( boolean isGrant ) {
        PermissionUtils.isGrant = isGrant;
    }

    public static boolean isGranted() {
        return isGrant;
    }

    /**
     * this method is used if someone first time deny the permission
     * all or any one of that permission then android will automatically
     * display default permission dialog box provided by android
     * @param activity point to current activity
     * @param permissionList list of permission
     * @param PERMISSION_CONSTANT specific number constant to permission
     */
    public static void secondTimePermission(Activity activity, String[]
            permissionList, int PERMISSION_CONSTANT) {
        stopRecursiveCallSecondTimePermission = false;
        ActivityCompat.requestPermissions(activity, permissionList, PERMISSION_CONSTANT);
    }

    /**
     * this method is used to open setting where we will give permission to app manually
     * that necessary for the app. this method is called when someone deny the permission
     * and clicked on the checked (in which mention don't ask again)
     * @param activity point to current activity
     */
    public static void openSettings(Activity activity) {
        sentToSettings = true;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        String msg = "Go to Setting to grant permission";
        toast(activity, msg);
    }

    /**
     * this method is use when someone first time deny the permission
     * and this is called to check permission is granted or not
     * @param activity point to current activity
     * @param permissionList list of permission
     * @return if all permission is not granted then return true else false
     */
    public static boolean isShouldShowRequestPermissionRationale(Activity activity, String[] permissionList) {
        if ( permissionList.length > 0 ) {
            for ( String permission : permissionList ) {
                boolean isPermissionTrue = ActivityCompat.
                        shouldShowRequestPermissionRationale(activity, permission);
                if ( isPermissionTrue ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method check current android version of tab
     * if current android version is Marshmallow or above this
     * then its return true else false
     */
    public static boolean isAndroidVersionMorHigher () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * this method is called after when all permission is granted to show the msg
     * @param activity point current activity
     * @param isShow
     */
    public static void proceedAfterPermission(Activity activity, boolean isShow) {
        //We've got the permission, now we can proceed further
        if ( isShow ) {
            String msg = "We got the required Permissions";
            toast(activity, msg);
        }
    }

    /**
     * show the current msg that we want to display
     * @param activity point to current Activity
     * @param msg point to current msg
     */
    public static void toast (Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getPermission(Context ctx) {
        if ( isAndroidVersionMorHigher() ) {
            if ( ctx == null ) return null;
            try {
                StringBuilder builder = new StringBuilder();
                PackageManager pm = ctx.getPackageManager();
                PackageInfo packageInfo = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_PERMISSIONS);

                String[] pinfo = packageInfo.requestedPermissions;

                if (pinfo == null) return "No Permission Info ";

                for (String name : pinfo) {
                    PermissionInfo info = pm.getPermissionInfo(name, PackageManager.GET_META_DATA);

                    if (info == null) continue;

                    if ( info.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS ) {
                        builder.append(name.substring(name.lastIndexOf('.'))).append(" : ").append(ctx.checkSelfPermission(name) == PackageManager.PERMISSION_GRANTED).append("\n");
                        isGrant = ctx.checkSelfPermission(name) != PackageManager.PERMISSION_GRANTED;
                        setGrant(isGrant);
                    }
                }
                return builder.toString();
            } catch ( Exception e ) {
//                e.getMessage();
            }
        }
        return null;
    }
}



package com.herocorp.ui.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rsawh on 02-Dec-16.
 */

public class PreferenceUtil {
    public static final String USER_ID = "username";
    public static final String DEALER_CODE = "dealercode";
    public static final String IS_USER_LOGIN = "is_user_login";
    public static final String VERSION = "version";
    public static final String VERSION_PATH = "path";
    public static final String STATE_ID = "state_id";
    public static final String STATE_NAME = "state_name";
    public static final String SYNC_DATE = "sync_date";

    private SharedPreferences sharedPref;


    public static void set_Userdata(Context context, String user_id, String dealer_code, Boolean login, String version, String version_path, String state_id, String state_name) {
        SharedPreferences.Editor edit = context.getSharedPreferences("hero", context.MODE_PRIVATE).edit();
        edit.putString(USER_ID, user_id);
        edit.putString(DEALER_CODE, dealer_code);
        edit.putBoolean(IS_USER_LOGIN, login);
        edit.putString(VERSION, version);
        edit.putString(VERSION_PATH, version_path);
        edit.putString(STATE_ID, state_id);
        edit.putString(STATE_NAME, state_name);
        edit.commit();
    }

    public static void set_Syncdate(Context context, String sync_date) {
        SharedPreferences.Editor edit = context.getSharedPreferences("hero", context.MODE_PRIVATE).edit();
        edit.putString(SYNC_DATE, sync_date);
        edit.commit();
    }


    public static Boolean get_IsUserLogin(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        Boolean restoredText = sharedPref.getBoolean(IS_USER_LOGIN, false);
        return restoredText;
    }

    public static String get_DealerCode(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        String restoredText = sharedPref.getString(DEALER_CODE, null);
        return restoredText;
    }

    public static String get_UserId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        String restoredText = sharedPref.getString(USER_ID, null);
        return restoredText;
    }

    public static String get_Version(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        String restoredText = sharedPref.getString(VERSION, null);
        return restoredText;
    }

    public static String get_VersionPath(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        String restoredText = sharedPref.getString(VERSION_PATH, null);
        return restoredText;
    }

    public static String get_StateId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        String restoredText = sharedPref.getString(STATE_ID, null);
        return restoredText;
    }

    public static String get_StateName(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        String restoredText = sharedPref.getString(STATE_NAME, null);
        return restoredText;
    }

    public static String get_Syncdate(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
       /* String restoredText = "";
        if (sharedPref.contains(SYNC_DATE))*/
        String restoredText = sharedPref.getString(SYNC_DATE, "");
        return restoredText;
    }

    public static void clearPref(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("hero", context.MODE_PRIVATE).edit();
        edit.remove(DEALER_CODE).commit();
        edit.remove(IS_USER_LOGIN).commit();
        edit.remove(VERSION_PATH).commit();
        edit.remove(VERSION).commit();
        edit.remove(STATE_ID).commit();
        edit.remove(STATE_NAME).commit();
    }

    public static void clear_SyncDate(Context context) {
        SharedPreferences mypref = context.getSharedPreferences("hero", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mypref.edit();
        if (mypref.contains(SYNC_DATE))
            edit.remove(SYNC_DATE).commit();
    }
}
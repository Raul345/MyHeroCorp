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
    public static final String DISTRICT_NAME = "district_name";
    public static final String TEHSIL_NAME = "tehsil_name";
    public static final String CITY_NAME = "city_name";
    public static final String SYNC_DATE = "sync_date";
    public static final String MAKE_SYNC_DATE = "make_sync_date";
    public static final String VERSION_CHECK = "version_date";
    public static final String SYNC_YN = "sync_yn";
    public static final String FCM_TOKEN = "fcm_token";

    private SharedPreferences sharedPref;

    public static SharedPreferences getPref(Context context) {

        return context.getSharedPreferences("hero", context.MODE_PRIVATE);
    }

    public static void set_Userdata(Context context, String user_id, String dealer_code, Boolean login, String version, String version_path, String state_id, String state_name) {
        SharedPreferences.Editor edit = getPref(context).edit();
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
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.putString(SYNC_DATE, sync_date);
        edit.commit();
    }

    public static void set_Syncyn(Context context, Boolean sync_yn) {
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.putBoolean(SYNC_YN, sync_yn);
        edit.commit();
    }

    public static void set_Versiondate(Context context, String version_date) {
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.putString(VERSION_CHECK, version_date);
        edit.commit();
    }


    public static void set_MakeSyncdate(Context context, String sync_date) {
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.putString(MAKE_SYNC_DATE, sync_date);
        edit.commit();
    }

    public static void set_Token(Context context, String token) {
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.putString(FCM_TOKEN, token);
        edit.commit();
    }

    public static String get_Token(Context context) {
        String restoredText = "";
        if (getPref(context).contains(FCM_TOKEN))
            restoredText = getPref(context).getString(FCM_TOKEN, "");

        return restoredText;
    }


    public static Boolean get_Syncyn(Context context) {
        Boolean restoredText = getPref(context).getBoolean(SYNC_YN, false);
        return restoredText;
    }

    public static Boolean get_IsUserLogin(Context context) {
        Boolean restoredText = getPref(context).getBoolean(IS_USER_LOGIN, false);
        return restoredText;
    }

    public static String get_DealerCode(Context context) {
        String restoredText = getPref(context).getString(DEALER_CODE, null);
        return restoredText;
    }

    public static String get_UserId(Context context) {
        String restoredText = "";
        if (getPref(context).contains(USER_ID))
            restoredText = getPref(context).getString(USER_ID, "");
        return restoredText;
    }

    public static String get_Version(Context context) {
        String restoredText = getPref(context).getString(VERSION, null);
        return restoredText;
    }

    public static String get_VersionPath(Context context) {
        String restoredText = getPref(context).getString(VERSION_PATH, null);
        return restoredText;
    }

    public static String get_StateId(Context context) {
        String restoredText = getPref(context).getString(STATE_ID, null);
        return restoredText;
    }

    public static String get_StateName(Context context) {
        String restoredText = getPref(context).getString(STATE_NAME, null);
        return restoredText;
    }

    public static String get_Syncdate(Context context) {

        String restoredText = getPref(context).getString(SYNC_DATE, "");
        return restoredText;
    }

    public static String get_MakeSyncdate(Context context) {
        String restoredText = "";
        if (getPref(context).contains(MAKE_SYNC_DATE))
            restoredText = getPref(context).getString(MAKE_SYNC_DATE, "");
        return restoredText;
    }

    public static String get_Versiondate(Context context) {
        String restoredText = getPref(context).getString(VERSION_CHECK, "");
        return restoredText;
    }

    public static void clearPref(Context context) {
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.remove(DEALER_CODE).commit();
        edit.remove(IS_USER_LOGIN).commit();
        edit.remove(VERSION_PATH).commit();
        edit.remove(VERSION).commit();
        edit.remove(STATE_ID).commit();
        edit.remove(STATE_NAME).commit();
        edit.remove(DISTRICT_NAME).commit();
        edit.remove(TEHSIL_NAME).commit();
        edit.remove(CITY_NAME).commit();
    }

    public static void clear_SyncDate(Context context) {
        SharedPreferences.Editor edit = getPref(context).edit();
        if (getPref(context).contains(SYNC_DATE))
            edit.remove(SYNC_DATE).commit();
        if (getPref(context).contains(MAKE_SYNC_DATE))
            edit.remove(MAKE_SYNC_DATE).commit();
    }


    public static void set_Address(Context context, String state, String district, String tehsil, String city) {
        SharedPreferences.Editor edit = getPref(context).edit();
        edit.putString(STATE_NAME, state);
        edit.putString(DISTRICT_NAME, district);
        edit.putString(TEHSIL_NAME, tehsil);
        edit.putString(CITY_NAME, city);
        edit.commit();
    }

    public static String get_DistrictName(Context context) {
        String restoredText = getPref(context).getString(DISTRICT_NAME, "");
        return restoredText;
    }

    public static String get_TehsilName(Context context) {
        String restoredText = getPref(context).getString(TEHSIL_NAME, "");
        return restoredText;
    }

    public static String get_CityName(Context context) {
        String restoredText = getPref(context).getString(CITY_NAME, "");
        return restoredText;
    }

    public static void clear_Address(Context context) {
        SharedPreferences.Editor edit = getPref(context).edit();
        if (getPref(context).contains(DISTRICT_NAME))
            edit.remove(DISTRICT_NAME).commit();
        if (getPref(context).contains(TEHSIL_NAME))
            edit.remove(TEHSIL_NAME).commit();
        if (getPref(context).contains(CITY_NAME))
            edit.remove(CITY_NAME).commit();
    }


}

package com.herocorp.core.constants;

/**
 * App constants.
 * Created by JitainSharma on 11/06/16.
 */
public class AppConstants {

    //App Environment
    //Dev : isDebug = true && isLive = false, QA : isDebug = false && isLive = false, Live : isLive = true
    public static final Boolean isDebug = false;
    public static final Boolean isLive = true;
    public static final Boolean isDSEDebug = false;
    public static final Boolean isDSELive = true;
    public static final String VALIDITY_DATE = "validityDate";
    public static String IS_SYNC_COMPLETED = "isAlreadySynced";
    public static String IS_USER_LOGGED_IN = "isAlreadyLoggedIn";
    public static String IS_360_RECORD_INSERTED = "is360RecordInserted";
    public static String IS_COMPARE_RECORD_INSERTED = "isCompareRecordInserted";
}

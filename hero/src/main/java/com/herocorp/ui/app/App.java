package com.herocorp.ui.app;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.herocorp.core.interfaces.SyncServiceCallBack;
import com.herocorp.infra.services.DataSyncService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * App Application Class.
 * Created by JitainSharma on 11/06/16.
 */
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    private static App instance;
    private RequestQueue mRequestQueue;

    public static boolean isSyncComplete = false;

    private static ProgressDialog progressBar;
    private static float currentProgress = 0;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new DatabaseBackup(getApplicationContext());

        instance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    //Sync Service
    public void startSync(SyncServiceCallBack syncServiceCallBack) {

        Intent myIntent = new Intent(getApplicationContext(), DataSyncService.class);
        startService(myIntent);

        //Register the CallBack
        DataSyncService.registerCallback(syncServiceCallBack);

    }

    public static void getProgressBar(Context context, String message) {

        if(progressBar == null) {

            progressBar = new ProgressDialog(context);
            progressBar.setCancelable(false);
            progressBar.setMessage(message);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(Math.round(currentProgress));
            progressBar.setMax(100);
            progressBar.show();
        }
    }

    public static void setProgress(float progress) {

        if (progressBar != null && progressBar.isShowing()) {

            currentProgress += progress;

            if (Math.round(currentProgress) < 100) {
                progressBar.setProgress(Math.round(currentProgress));

            } else {

                progressBar.dismiss();
                progressBar = null;
                currentProgress = 0;
            }
        }
    }

    public static boolean shouldAppRun(String targetDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(targetDate);

        long targetTime = date.getTime();

        if(System.currentTimeMillis() >= targetTime) {
            return false;
        }

        return true;
    }
}

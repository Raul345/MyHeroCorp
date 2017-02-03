package com.herocorp.ui.activities.DSEapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Close_followup;
import com.herocorp.ui.activities.DSEapp.models.Next_Followup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


// Created by rsawh on 28-Nov-16.

public class DbSyncservice extends Service {
    private static final String TAG = "Sync_closenext";

    private boolean isRunning = false;

    @Override
    public void onCreate() {
        Log.e(TAG, "Sync Service started");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(TAG, "Sync Service in progress");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
               /* for (int i = 0; i < 5; i++) {
                    try {
                        Toast.makeText(getApplicationContext(), "service ", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Service running");
                        //Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                if (isRunning) {
                    Log.e(TAG, "Service running");
                }
            }*/

                try {
                    if (NetConnections.isConnected(getApplicationContext())) {
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        List<Close_followup> record = db.getCloseFollowup();
                        for (Close_followup followup : record) {
                            JSONObject jsonparams = new JSONObject();
                            jsonparams.put("reason", followup.getReason());
                            jsonparams.put("sub_reason", followup.getSubreason());
                            jsonparams.put("existMake", followup.getExistmake());
                            jsonparams.put("existModel", followup.getExistmodel());
                            jsonparams.put("remarks", followup.getRemarks());
                            jsonparams.put("user_id", followup.getUser_id());
                            jsonparams.put("dms_enquiry_id", followup.getDms_enquiryid());
                            Log.e("close_followup", jsonparams.toString());
                            String newurlparams = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");

                            new NetworkConnect1(URLConstants.SYNC_FOLLOW_UP, newurlparams, followup.getDms_enquiryid(),1).execute();
                        }


                        List<Next_Followup> records = db.getNextFollowup();
                        for (Next_Followup followup : records) {
                            JSONObject jsonparams = new JSONObject();
                            jsonparams.put("date", followup.getDate());
                            jsonparams.put("remarks", followup.getRemarks());
                            jsonparams.put("fol_date", followup.getFollowdate());
                            jsonparams.put("user_id", followup.getUser_id());
                            jsonparams.put("dms_enquiry_id", followup.getDms_enquiryid());
                            String json = jsonparams.toString().replace("\\/", "/");
                            Log.e("followup", json);
                            String newurlparams = "data=" + URLEncoder.encode(json, "UTF-8");
                            new NetworkConnect1(URLConstants.SYNC_FOLLOW_UP, newurlparams, followup.getDms_enquiryid(),2).execute();
                        }
                        Log.e(TAG, "Service sync running");
                    }
                    if (isRunning) {
                        Log.e(TAG, "Service is running");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Service error !!");
                }


                //Stop service once it finishes its task
                stopSelf();
            }

        }

        ).

                start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }


    public class NetworkConnect1 extends AsyncTask<Void, Void, String> {
        String targetURL;
        String urlParameters;
        String enq_id;
        NetworkConnect networkConnect;
        int flag;

        public NetworkConnect1(String targeturl, String urlParameters, String enq_id, int flag) {
            this.targetURL = targeturl;
            this.urlParameters = urlParameters;
            this.enq_id = enq_id;
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url;
            HttpURLConnection connection = null;
            try {
                String encryptdata = null;
                networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptdata = result.replace("\\/", "/");
                Log.e("encrypt_sync_data:", encryptdata);
                String data = "data=" + URLEncoder.encode(encryptdata, "UTF-8");


                //Create connection
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                //connection.setConnectTimeout(5000);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();

            } catch (Exception e) {

                e.printStackTrace();
                return null;

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsono = new JSONObject(s);
                if (jsono.getString("success").equals("1") && jsono.getString("records").equals("true")) {
                    Log.e("sync_response", s);
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    if (flag == 1)
                        db.delete_closefollowup(enq_id);
                    else
                        db.delete_nextfollowup(enq_id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        }
    }

}




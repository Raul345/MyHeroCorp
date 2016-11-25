package com.herocorp.ui.activities.DSEapp.ConnectService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.herocorp.ui.activities.DSEapp.Fragment.AlertDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rsawh on 22-Sep-16.
 */
public class NetworkConnect1 extends AsyncTask<Void, Void, String> {
    String targetURL;
    String urlParameters;
    ProgressDialog progress;
    String message;
    Context context;
    int flag = 0;

    public NetworkConnect1(String targeturl, String urlParameters, ProgressDialog progress, String message, Context context, int flag) {
        this.targetURL = targeturl;
        this.urlParameters = urlParameters;
        this.progress = progress;
        this.message = message;
        this.context = context;
        this.flag = flag;
    }

    public NetworkConnect1(String targeturl, String urlParameters) {
        this.targetURL = targeturl;
        this.urlParameters = urlParameters;
        flag = 0;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(context, "Sending Request", " Please wait....", false, false);
    }


    protected String doInBackground(Void... params) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
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
        progress.dismiss();
        try {
            Log.e(targetURL + "response", s);
            JSONObject jsono = new JSONObject(s);
            if (jsono.getString("success").equals("1") && jsono.getString("records").equals("true")) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("msg", message);
                bundle.putInt("flag", flag);
                FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                AlertDialogFragment dialogFragment = new AlertDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(false);
                dialogFragment.show(fm, "Sample Fragment");
            } else
                Toast.makeText(context, "Error while submitting !!", Toast.LENGTH_SHORT).show();
            Log.i("json", s);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }
}

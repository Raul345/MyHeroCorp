package com.herocorp.ui.activities.DSEapp.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rsawh on 08-Dec-16.
 */

public class Syncmakemodel extends AsyncTask<Void, Void, String> {
    DatabaseHelper db;
    NetworkConnect networkConnect;
    final ProgressDialog progressDialog;

    String result;
    private SharedPreferences sharedPreferences;
    Context context;

    public Syncmakemodel(Context context) {
        this.context = context;
        progressDialog = ProgressDialog.show(context, null, null);
        progressDialog.setContentView(R.layout.progresslayout);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected String doInBackground(Void... params) {
        try {
            final JSONObject jsonparams = new JSONObject();
            jsonparams.put("user_id", PreferenceUtil.get_UserId(context));

            Log.e("make_sync_start:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
            String newurlparams = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");
            networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
            String data = networkConnect.execute();
            String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
            networkConnect = new NetworkConnect(URLConstants.BIKE_MAKE_MODEL, urldata);
            result = networkConnect.execute();
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String s) {
        try {
            super.onPostExecute(s);
            db = new DatabaseHelper(context);
            db.clearmakemodel_table();

            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("make");
            Log.e("response_make_model:", result);

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                db.addbikemake(new Bikemake(object.getString("id"), object.getString("make_name")));
            }

            JSONArray jarray1 = jsono.getJSONArray("model");
            for (int i = 0; i < jarray1.length(); i++) {
                JSONObject object = jarray1.getJSONObject(i);
                db.addbikemodel(new Bike_model(object.getString("make_id"), object.getString("model_name")));
            }

            PreferenceUtil.set_MakeSyncdate(context, new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());

            Log.e("make_sync_close", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
            progressDialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(context, "Check your Connection !!", Toast.LENGTH_SHORT);
        }
    }

}
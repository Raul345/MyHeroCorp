package com.herocorp.ui.activities.DSEapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
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

public class SyncFollowup extends AsyncTask<Void, Void, String> {
    DatabaseHelper db;
    NetworkConnect networkConnect;
    String result;
    Context context;
    String first_name;
    String last_name;
    String cell_ph_no;
    String age;
    String gender;
    String email_addr;
    String state;
    String district;
    String tehsil;
    String city;
    String x_con_seq_no;
    String x_model_interested;
    String expected_date_purchase;
    String x_exchange_required;
    String x_finance_required;
    String exist_vehicle;
    String followup_comments;
    String enquiry_id;
    String follow_date;
    String enquiry_entry_date;
    String dealer_bu_id;
    String priority;
    String two_wheeler_type;
    String rural_urban;
    String occupation;
    String usage;

    private SharedPreferences sharedPreferences;


    public SyncFollowup(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(Void... params) {
        try {
            final JSONObject jsonparams = new JSONObject();
            jsonparams.put("user_id", PreferenceUtil.get_UserId(context));
            jsonparams.put("user_id", "DSE10866");
            Log.e("followup_sync_start:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
            String newurlparams = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");
            networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
            String data = networkConnect.execute();
            String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
            networkConnect = new NetworkConnect(URLConstants.PENDING_FOLLOWUP, urldata);
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
            Log.e("response_followup:", result);
            db = new DatabaseHelper(context);
            db.clearfollowup_table();
            JSONObject jsono = null;
            JSONArray jarray = null;
            try {
                jsono = new JSONObject(result);
                if (jsono.has("follow_up")) {
                    jarray = jsono.getJSONArray("follow_up");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        first_name = object.getString("FST_NAME");
                        last_name = object.getString("LAST_NAME");
                        cell_ph_no = object.getString("CELL_PH_NUM");
                        age = object.getString("AGE");
                        gender = object.getString("GENDER");
                        email_addr = object.getString("EMAIL_ADDR");
                        state = object.getString("STATE");
                        district = object.getString("DISTRICT");
                        tehsil = object.getString("TEHSIL");
                        city = object.getString("CITY");
                        x_con_seq_no = object.getString("X_CON_SEQ_NUM");
                        x_model_interested = object.getString("X_MODEL_INTERESTED");
                        expected_date_purchase = object.getString("EXPCTD_DT_PURCHASE");
                        x_exchange_required = object.getString("X_EXCHANGE_REQUIRED");
                        x_finance_required = object.getString("X_FINANCE_REQUIRED");
                        exist_vehicle = object.getString("EXISTING_VEHICLE");
                        followup_comments = object.getString("FOLLOWUP_COMMENTS");
                        enquiry_id = object.getString("ENQUIRY_ID");
                        follow_date = object.getString("FOLLOW_DATE");
                        enquiry_entry_date = object.getString("ENQUIRY_ENTRY_DATE");
                        dealer_bu_id = object.getString("DEALER_BU_ID");
                        if (object.has("PRIORITY")) {
                            priority = object.getString("PRIORITY");
                        }
                        if (object.has("TWO_WHEELER_TYPE")) {
                            two_wheeler_type = object.getString("TWO_WHEELER_TYPE");
                        }
                        if (object.has("RURAL_URBAN")) {
                            rural_urban = object.getString("RURAL_URBAN");
                        }
                        if (object.has("OCCUPATION")) {
                            occupation = object.getString("OCCUPATION");
                        }
                        if (object.has("USAGE")) {
                            usage = object.getString("USAGE");
                        }
                        db.addfollowup(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, "0"));
                    /*    db.addNewfollowup(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, priority, two_wheeler_type, rural_urban, occupation, usage,"0"));
*/

                    }
                }
                PreferenceUtil.set_Syncdate(context, new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                Log.e("followup_sync_end:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(context, "Check your Connection !!", Toast.LENGTH_SHORT);
        }
    }
}
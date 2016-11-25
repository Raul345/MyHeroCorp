package com.herocorp.ui.activities.DSEapp.Fragment.Home;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Contact.ContactFragment;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingFollowup.PendingFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingOrders.PendingOrdersFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.TodayFollowup.TodayFollowupFragment;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rsawh on 10-Sep-16.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    EditText phoneno_et, registration_et;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    NetworkConnect networkConnect;
    String encryptuser;
    Bundle bundle = new Bundle();
    int check = 0, flag = 0;

    DatabaseHelper db;
    String urlParameters;

    TextView pendingorderText, todayfollowupText, pendingfollowupText, searchenquiryText;

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

    private SharedPreferences sharedPreferences;
    String user_id, dealer_code, sync_date = "";
    String current_date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_home_fragment, container, false);
        getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        try {
            initView(rootView);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void initView(View rootView) throws ParseException {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{190, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{0, 20, 0, 0}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        LinearLayout topLayout2 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout2, new int[]{0, 20, 0, 0}, new int[]{0, 0, 0, 0}, topLayout2.getParent());

        LinearLayout pendingorderLayout = (LinearLayout) rootView.findViewById(R.id.pendingorders_layout);
        LinearLayout todayfollowupLayout = (LinearLayout) rootView.findViewById(R.id.followup_layout);
        LinearLayout pendingfollowupLayout = (LinearLayout) rootView.findViewById(R.id.pendingfollowup_layout);
        LinearLayout searchenquiryLayout = (LinearLayout) rootView.findViewById(R.id.searchenquiry_layout);

        customViewParams.setMarginAndPadding(pendingorderLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, pendingorderLayout.getParent());
        customViewParams.setMarginAndPadding(todayfollowupLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, todayfollowupLayout.getParent());
        customViewParams.setMarginAndPadding(pendingfollowupLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, pendingfollowupLayout.getParent());
        customViewParams.setMarginAndPadding(searchenquiryLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, searchenquiryLayout.getParent());

        customViewParams.setHeightAndWidth(pendingorderLayout, 320, 320);
        customViewParams.setHeightAndWidth(todayfollowupLayout, 320, 320);
        customViewParams.setHeightAndWidth(pendingfollowupLayout, 320, 320);
        customViewParams.setHeightAndWidth(searchenquiryLayout, 320, 320);

        pendingorderText = (TextView) rootView.findViewById(R.id.pendingorders_text);
        todayfollowupText = (TextView) rootView.findViewById(R.id.followup_text);
        pendingfollowupText = (TextView) rootView.findViewById(R.id.pendingfollowup_text);
        searchenquiryText = (TextView) rootView.findViewById(R.id.searchenquiry_text);

        customViewParams.setTextViewCustomParams(pendingorderText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(todayfollowupText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(pendingfollowupText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(searchenquiryText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);

        ImageView pendingorder = (ImageView) rootView.findViewById(R.id.pendingorders);
        ImageView todayfollowup = (ImageView) rootView.findViewById(R.id.followup);
        ImageView pendingfollowup = (ImageView) rootView.findViewById(R.id.pendingfollowup);
        ImageView searchenquiry = (ImageView) rootView.findViewById(R.id.searchenquiry);
        ImageView submit = (ImageView) rootView.findViewById(R.id.imageView_submit_home);
     /*   progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
*/
        customViewParams.setImageViewCustomParams(pendingorder, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(todayfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(pendingfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(searchenquiry, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);

        LinearLayout textview_container = (LinearLayout) rootView.findViewById(R.id.textview_container);
        customViewParams.setMarginAndPadding(textview_container, new int[]{120, 30, 110, 0}, new int[]{0, 0, 0, 0}, textview_container.getParent());

        phoneno_et = (EditText) rootView.findViewById(R.id.phoneno_edittext);
        registration_et = (EditText) rootView.findViewById(R.id.registration_edittext);

        fetch_data();
        current_date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
        if (!(sync_date.equalsIgnoreCase(current_date.toString())))
            sync_data();

        menu.setOnClickListener(this);
        pendingorder.setOnClickListener(this);
        todayfollowup.setOnClickListener(this);
        pendingfollowup.setOnClickListener(this);
        searchenquiry.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.pendingorders) {

            pendingorderText.setTextColor(Color.WHITE);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);
            // progress();
            if (NetConnections.isConnected(getContext())) {
                // Toast.makeText(getActivity(), "Please wait for a while !!", Toast.LENGTH_SHORT).show();
                new Encrypt_data().execute();
                transaction(new PendingOrdersFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.followup) {

            // Toast.makeText(getActivity(), "today followup", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.WHITE);
            searchenquiryText.setTextColor(Color.GRAY);

            check = 0;
            flag = 0;
            transaction(new TodayFollowupFragment());


        } else if (i == R.id.pendingfollowup) {
            //  Toast.makeText(getActivity(), "penidng followup", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.WHITE);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);

            transaction(new PendingFollowupFragment());

        } else if (i == R.id.searchenquiry) {
            // Toast.makeText(getActivity(), "search enquiry", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.WHITE);
            flag = 1;
            check = 1;
            transaction(new TodayFollowupFragment());
            /*if (NetConnections.isConnected(getContext())) {


                transaction(new TodayFollowupFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();*/
        } else if (i == R.id.imageView_submit_home) {
            if (!(phoneno_et.getText().toString().equals("") && registration_et.getText().toString().equals(""))) {
                if (NetConnections.isConnected(getContext())) {
                    new Encrypt_data().execute();
                    transaction(new ContactFragment());
                } else
                    Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "Phone/RegNo missing!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void transaction(final Fragment f) {
        bundle.putString("user_id", encryptuser);
        bundle.putString("user", user_id);
        bundle.putInt("check", check);
        bundle.putInt("flag", flag);
        bundle.putString("phone_no", phoneno_et.getText().toString());
        bundle.putString("reg_no", registration_et.getText().toString());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        f.setArguments(bundle);
        ft.addToBackStack(null);
        ft.replace(R.id.content_dsehome, f);
        ft.commit();

    }

    public void sync_data() {
        if (NetConnections.isConnected(getContext())) {
            final Handler handler = new Handler();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    int i = 0;
                    while (encryptuser == null && i < 5) {
                        String json = "{\"user_id\":\"ROBINK11610\"}";
                        //   String json = "{\"user_id\":\"" + user_id + "\"}";
                        try {
                            urlParameters = "data=" + URLEncoder.encode(json, "UTF-8");
                            networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                            String result = networkConnect.execute();
                            if (result != null) {
                                encryptuser = result.replace("\\/", "/");
                                urlParameters = "data=" + URLEncoder.encode(encryptuser, "UTF-8");

                                //sync_start
                                /*if (!(sync_date.equalsIgnoreCase(current_date.toString()))) {*/
                                Log.e("sync_start", current_date.toString());
                                networkConnect = new NetworkConnect(URLConstants.PENDING_FOLLOWUP, urlParameters);
                                jsonparse_followup(networkConnect.execute());
                                //  }
                            }
                            i++;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        handler.post(this);
                    }
                }
            };

            thread.start();
        } else
            Toast.makeText(

                    getContext(),

                    "Check your connection !!", Toast.LENGTH_SHORT).

                    show();

    }

    public void jsonparse_followup(String result) {
        db = new DatabaseHelper(getContext());
        db.cleartables();
        JSONObject jsono = null;
        JSONArray jarray = null;
        try {
            jsono = new JSONObject(result);
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
                db.addfollowup(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                        expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, "0"));
            }
            networkConnect = new NetworkConnect(URLConstants.BIKE_MAKE_MODEL, urlParameters);
            jsonparse_makemodel(networkConnect.execute());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void jsonparse_makemodel(String result) {
        try {
            db = new DatabaseHelper(getContext());
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("make");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                db.addbikemake(new Bikemake(object.getString("id"), object.getString("make_name")));
            }

            JSONArray jarray1 = jsono.getJSONArray("model");
            for (int i = 0; i < jarray1.length(); i++) {
                JSONObject object = jarray1.getJSONObject(i);
                db.addbikemodel(new Bike_model(object.getString("make_id"), object.getString("model_name")));
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("sync_date", current_date.toString());
            edit.commit();

            Log.e("sync_close", current_date.toString());
            //   progressBar.setVisibility(View.INVISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));
        }
    }

    public void fetch_data() {
        sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        if (sharedPreferences.contains("username"))
            user_id = sharedPreferences.getString("username", null);
        if (sharedPreferences.contains("dealercode"))
            dealer_code = sharedPreferences.getString("dealercode", null);
        if (sharedPreferences.contains("sync_date"))
            sync_date = sharedPreferences.getString("sync_date", null);
    }

    public class Encrypt_data extends AsyncTask<Void, Void, String> {
        NetworkConnect networkConnect;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... params) {
            if (NetConnections.isConnected(getContext())) try {
                JSONObject json = new JSONObject();
                try {
                    json.put("user_id", "ROBINK11610");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String urlParameters = "data=" + URLEncoder.encode(json.toString(), "UTF-8");
                networkConnect = new NetworkConnect("http://abym.in/clientProof/hero_motors/encrypt", urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptuser = result.replace("\\/", "/");

                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            else {
                Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //  progressBar.setVisibility(View.INVISIBLE);

        }
    }

   /* public class Sync_data extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... params) {
            if (NetConnections.isConnected(getContext())) try {
                // String json = "{\"user_id\":\"ROBINK11610\"}";
                JSONObject json = new JSONObject();
                json.put("user_id", "ROBINK11610");
                Log.e("jhhkj", json.toString());
                //   String json = "{\"user_id\":\"" + user_id + "\"}";
                urlParameters = "data=" + URLEncoder.encode(json.toString(), "UTF-8");
                networkConnect = new NetworkConnect("http://abym.in/clientProof/hero_motors/encrypt", urlParameters);
                String result = networkConnect.execute();
                if (result != null) {
                    encryptuser = result.replace("\\/", "/");
                    urlParameters = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
                    Log.e("sync_start", current_date.toString());
                    networkConnect = new NetworkConnect(URLConstants.PENDING_FOLLOWUP, urlParameters);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("Sync_error", e.toString());
            }
            return networkConnect.execute().toString();
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jsonparse_followup(networkConnect.execute());
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }*/
}
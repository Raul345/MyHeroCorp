package com.herocorp.ui.activities.DSEapp.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
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
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.Contact.ContactFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingFollowup.PendingFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingOrders.PendingOrdersFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.TodayFollowup.TodayFollowupFragment;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rsawh on 03-Dec-16.
 */

public class MainHome extends FragmentActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dse_home_fragment);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        try {
            initView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void initView() throws ParseException {
        customViewParams = new CustomViewParams(this);
        CustomTypeFace customTypeFace = new CustomTypeFace(this);

        ImageView heroLogo = (ImageView) findViewById(R.id.app_logo);
        ImageView menu = (ImageView) findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{190, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{0, 20, 0, 0}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        LinearLayout topLayout2 = (LinearLayout) findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout2, new int[]{0, 20, 0, 0}, new int[]{0, 0, 0, 0}, topLayout2.getParent());

        LinearLayout pendingorderLayout = (LinearLayout) findViewById(R.id.pendingorders_layout);
        LinearLayout todayfollowupLayout = (LinearLayout) findViewById(R.id.followup_layout);
        LinearLayout pendingfollowupLayout = (LinearLayout) findViewById(R.id.pendingfollowup_layout);
        LinearLayout searchenquiryLayout = (LinearLayout) findViewById(R.id.searchenquiry_layout);

        customViewParams.setMarginAndPadding(pendingorderLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, pendingorderLayout.getParent());
        customViewParams.setMarginAndPadding(todayfollowupLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, todayfollowupLayout.getParent());
        customViewParams.setMarginAndPadding(pendingfollowupLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, pendingfollowupLayout.getParent());
        customViewParams.setMarginAndPadding(searchenquiryLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, searchenquiryLayout.getParent());

        customViewParams.setHeightAndWidth(pendingorderLayout, 320, 320);
        customViewParams.setHeightAndWidth(todayfollowupLayout, 320, 320);
        customViewParams.setHeightAndWidth(pendingfollowupLayout, 320, 320);
        customViewParams.setHeightAndWidth(searchenquiryLayout, 320, 320);

        pendingorderText = (TextView) findViewById(R.id.pendingorders_text);
        todayfollowupText = (TextView) findViewById(R.id.followup_text);
        pendingfollowupText = (TextView) findViewById(R.id.pendingfollowup_text);
        searchenquiryText = (TextView) findViewById(R.id.searchenquiry_text);

        customViewParams.setTextViewCustomParams(pendingorderText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(todayfollowupText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(pendingfollowupText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(searchenquiryText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, customTypeFace.gillSans, 0);

        ImageView pendingorder = (ImageView) findViewById(R.id.pendingorders);
        ImageView todayfollowup = (ImageView) findViewById(R.id.followup);
        ImageView pendingfollowup = (ImageView) findViewById(R.id.pendingfollowup);
        ImageView searchenquiry = (ImageView) findViewById(R.id.searchenquiry);
        ImageView submit = (ImageView) findViewById(R.id.imageView_submit_home);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        customViewParams.setImageViewCustomParams(pendingorder, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(todayfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(pendingfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(searchenquiry, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);

        LinearLayout textview_container = (LinearLayout) findViewById(R.id.textview_container);
        customViewParams.setMarginAndPadding(textview_container, new int[]{120, 30, 110, 0}, new int[]{0, 0, 0, 0}, textview_container.getParent());

        phoneno_et = (EditText) findViewById(R.id.phoneno_edittext);
        registration_et = (EditText) findViewById(R.id.registration_edittext);

        fetch_pref();
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
            BaseDrawerActivity.toggleDrawer();
        } else if (i == R.id.pendingorders) {
            pendingorderText.setTextColor(Color.WHITE);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);
            // progress();
            if (NetConnections.isConnected(this)) {
                transaction(new PendingOrdersFragment());
            }else
                Toast.makeText(this, "Check your Connection !!", Toast.LENGTH_SHORT).show();

        }else if (i == R.id.followup) {
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
        } else if (i == R.id.imageView_submit_home) {
            if (!(phoneno_et.getText().toString().equals("") && registration_et.getText().toString().equals(""))) {
                if (NetConnections.isConnected(this)) {
                    //new Encrypt_data().execute();
                    transaction(new ContactFragment());
                } else
                    Toast.makeText(this, "Check your Connection !!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Phone/RegNo missing!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void transaction(final Fragment f) {
        // bundle.putString("user_id", encryptuser);
       /* bundle.putString("user", user_id);
        bundle.putString("dealercode", dealer_code);*/
        bundle.putInt("check", check);
        bundle.putInt("flag", flag);
        bundle.putString("phone_no", phoneno_et.getText().toString());
        bundle.putString("reg_no", registration_et.getText().toString());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        f.setArguments(bundle);
        ft.addToBackStack(null);
        ft.add(R.id.content_dsehome, f);
        ft.commit();
    }


    public void sync_data() {
        final JSONObject jsonparams = new JSONObject();

        if (NetConnections.isConnected(getApplicationContext())) {
            final Handler handler = new Handler();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    int i = 0;
                    while (encryptuser == null && i < 5) {
                        try {
                            jsonparams.put("user_id", user_id);
                            Log.e("followup_request:", jsonparams.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            urlParameters = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");
                            networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                            String result = networkConnect.execute();
                            if (result != null) {
                                encryptuser = result.replace("\\/", "/");
                                urlParameters = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
                                Log.e("sync_start", current_date.toString());

                                networkConnect = new NetworkConnect(URLConstants.PENDING_FOLLOWUP, urlParameters);
                                jsonparse_followup(networkConnect.execute());
                                networkConnect = new NetworkConnect(URLConstants.BIKE_MAKE_MODEL, urlParameters);
                                jsonparse_makemodel(networkConnect.execute());
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

                    getApplicationContext(),

                    "Check your connection !!", Toast.LENGTH_SHORT).

                    show();

    }

    public void jsonparse_followup(String result) {
        Log.e("response_followup:", result);
        db = new DatabaseHelper(getApplicationContext());
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void jsonparse_makemodel(String result) {
        try {
            db = new DatabaseHelper(getApplicationContext());
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

            PreferenceUtil.set_Syncdate(getApplicationContext(), current_date.toString());

            Log.e("sync_close", current_date.toString());
            //   progressBar.setVisibility(View.INVISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
        }
    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(this);
        dealer_code = PreferenceUtil.get_DealerCode(this);
        sync_date = PreferenceUtil.get_Syncdate(this);

    }
}
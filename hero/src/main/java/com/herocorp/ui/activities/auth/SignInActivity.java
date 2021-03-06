package com.herocorp.ui.activities.auth;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SignInActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION_READ_PHONE_STATE = 1;
    private EditText dealerCode;
    DatabaseHelper db;
    /*   private String[] imeis = {"356604060544425",
               "861375036084113",
               "861375036084105",
               "356905070792270",
               "356906070792278",
               "355004057991484",
               "865317023674973",
               "354833052900434",
               "351971070447146",
               "351971070473217"
       };*/
    private String respDesc = "", respCode = "", state_id = "", dealer_code = "", version = "", path = "", state_name = "", city_name = "", result = "", failure_msg = "";
    private String appVersion;
    // private String deviceImei = "911441757449230";//Test
    // private String deviceImei = "911562050022240";//ALOK11831
    //private String deviceImei = "359940061620922";//RAJ10220
    private String deviceImei;
    private String userCode, uuid = "0";
    private String encryptuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        if (PreferenceUtil.get_IsUserLogin(getApplicationContext()))
            openHomeScreen();
        else
            initView();
    }

    private void initView() {

        CustomViewParams customViewParams = new CustomViewParams(this);

        LinearLayout containerLayout = (LinearLayout) findViewById(R.id.container_layout);
        customViewParams.setMarginAndPadding(containerLayout, new int[]{50, 50, 100, 50}, new int[]{7, 7, 7, 7}, containerLayout.getParent());
        customViewParams.setHeightAndWidth(containerLayout, 700, 500);

        ImageView bikesImageView = (ImageView) findViewById(R.id.bike_image_view);
        ImageView logoImageView = (ImageView) findViewById(R.id.logo_image_view);
        ImageView dealerImageView = (ImageView) findViewById(R.id.dealer_image_view);

        customViewParams.setImageViewCustomParams(bikesImageView, new int[]{100, 0, 0, 0}, new int[]{0, 0, 0, 0}, 650, 826);
        customViewParams.setImageViewCustomParams(logoImageView, new int[]{0, 40, 0, 20}, new int[]{0, 0, 0, 0}, 180, 160);
        customViewParams.setImageViewCustomParams(dealerImageView, new int[]{0, 7, 0, 7}, new int[]{0, 0, 0, 0}, 120, 100);

        final TextView dealerCodeText = (TextView) findViewById(R.id.dealer_text);
        customViewParams.setTextViewCustomParams(dealerCodeText, new int[]{7, 0, 7, 20}, new int[]{0, 0, 0, 0}, 35, new CustomTypeFace(this).gillSansBold, 0);

        dealerCode = (EditText) findViewById(R.id.dealer_code_field);
        customViewParams.setEditTextCustomParams(dealerCode, new int[]{7, 7, 7, 20}, new int[]{15, 15, 15, 15}, 35, new CustomTypeFace(this).gillSansBold, 0);
        customViewParams.setHeightAndWidth(dealerCode, 0, 270);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        customViewParams.setButtonCustomParams(signInButton, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 70, 210, 35, new CustomTypeFace(this).gillSans, 0);

        dealerCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    dealerCode.setText(dealerCode.getText().toString().toUpperCase());
            }
        });
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            if (NetConnections.isConnected(getApplicationContext()))
                showPhoneStatePermission();
            else
                Toast.makeText(getApplicationContext(), "No Network Connection !!", Toast.LENGTH_LONG).show();
        }
    }

    //@Override
   /* public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case REQUEST_PERMISSION_READ_PHONE_STATE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    if (Arrays.asList(imeis).contains(dealerCode.getText().toString()) &&
                            telephonyManager.getDeviceId().contentEquals(dealerCode.getText().toString())) {

                        sharedPreferences.edit().putBoolean(AppConstants.IS_USER_LOGGED_IN, true).commit();
                        openHomeScreen();

                    } else {
                        Toast.makeText(this, "Please enter valid dealer code", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }*/

    public void showPhoneStatePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_READ_PHONE_STATE);
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_READ_PHONE_STATE);
                }
            }
        } else {

            check_user();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERMISSION_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    check_user();
                else {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_READ_PHONE_STATE);

                }
                break;
            }

        }
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, BaseDrawerActivity.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("tag")) {
                intent.putExtra("tag", extras.getString("tag"));
            }
        }
        startActivity(intent);
        finish();
    }

    public class Login extends AsyncTask<Void, Void, String> {
        String targetURL;
        String newurlParameters;
        NetworkConnect networkConnect;
        String result;
        private ProgressDialog progressDialog;

        public Login(String urlParameters, String targeturl) {
            this.targetURL = targeturl;
            this.newurlParameters = urlParameters;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SignInActivity.this, null, null);
            progressDialog.setContentView(R.layout.progresslayout);
        }

        protected String doInBackground(Void... params) {
            if (NetConnections.isConnected(getApplicationContext())) {
                try {
                    String urlParameters = "data=" + URLEncoder.encode(newurlParameters, "UTF-8");
                    networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                    String result = networkConnect.execute();
                    if (result != null)
                        encryptuser = result.replace("\\/", "/");
                    Log.e("response_encrypt", encryptuser);
                    String newurlparams = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
                    networkConnect = new NetworkConnect(targetURL, newurlparams);
                    result = networkConnect.execute();
                    return result;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            } else {
                Toast.makeText(getApplicationContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            db = new DatabaseHelper(getApplicationContext());
            db.clearstate_table();
            try {
                JSONObject jsono = new JSONObject(s);
                Log.e("response_login", s);
                respCode = jsono.getString("respCode");
                result = jsono.getString("result");
                if (respCode.equals("1") && result.equals("true")) {
                    respDesc = jsono.getString("respDescription");
                    dealer_code = jsono.getString("dealer_code");
                    state_id = jsono.getString("state_id");
                    if (jsono.has("state_name"))
                        state_name = jsono.getString("state_name");
                    version = jsono.getString("version");
                    path = jsono.getString("path");
                    if (jsono.has("city_name"))
                        city_name = jsono.getString("city_name");

                    JSONArray jarray = jsono.getJSONArray("state");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        db.add_state(new State(object.getString("id"), object.getString("state_name")));
                    }
                    save_data();
                    openHomeScreen();
                } else {
                    failure_msg = jsono.getString("failure_msg");
                    Toast.makeText(getApplicationContext(), failure_msg, Toast.LENGTH_SHORT).show();
                   /* save_data();
                    openHomeScreen();*/
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Check your Connection !! ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void save_data() {
        if (!userCode.equalsIgnoreCase(PreferenceUtil.get_UserId(getApplicationContext()))) {
            PreferenceUtil.clear_SyncDate(getApplicationContext());
        }
        PreferenceUtil.clear_Address(getApplicationContext());
        PreferenceUtil.set_Userdata(getApplicationContext(), userCode, dealer_code, true, version, path, state_id, state_name, city_name);
    }

    public void check_user() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
           /* if (Arrays.asList(imeis).contains(dealerCode.getText().toString()) &&
                    telephonyManager.getDeviceId().contentEquals(dealerCode.getText().toString())) {
                sharedPreferences.edit().putBoolean(AppConstants.IS_USER_LOGGED_IN, true).commit();
                openHomeScreen();

            }*/
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName;
            deviceImei = telephonyManager.getDeviceId();
            userCode = dealerCode.getText().toString().toUpperCase();
            if (userCode.equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter dealer Code!!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject jsonparams = new JSONObject();
                    jsonparams.put("username", userCode);
                    jsonparams.put("version", appVersion);
                    jsonparams.put("imei", deviceImei);
                    jsonparams.put("uuid", uuid);
                    Log.e("sigin:", jsonparams.toString());
                    new Login(jsonparams.toString(), URLConstants.LOGIN).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}

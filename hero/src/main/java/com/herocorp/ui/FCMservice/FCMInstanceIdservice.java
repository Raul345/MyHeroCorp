package com.herocorp.ui.FCMservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.utility.PreferenceUtil;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

// Created by rsawh on 09-Oct-16.


public class FCMInstanceIdservice extends FirebaseInstanceIdService {
    private String deviceImei, userId, appVersion, androidVersion, deviceInfo, deviceToken;
    private static final String TAG = "HeroFCMIIDService";

    NetworkConnect networkConnect;
    Context context;

    public FCMInstanceIdservice() {
    }

    public FCMInstanceIdservice(String deviceImei, String userId, String appVersion, String androidVersion, String deviceInfo, Context context) {
        this.deviceImei = deviceImei;
        this.userId = userId;
        this.appVersion = appVersion;
        this.androidVersion = androidVersion;
        this.deviceInfo = deviceInfo;
        this.context = context;
    }

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        try {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + deviceToken);
            if (!deviceToken.equals(PreferenceUtil.get_Token(context)))
                sendRegistrationToServer(deviceImei, userId, appVersion, androidVersion, deviceInfo, deviceToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void jsonparse_token(String result, String deviceToken) {
        try {
            Log.e("token_api_response", result);
            PreferenceUtil.set_Token(context, deviceToken);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
        }

    }

    private void sendRegistrationToServer(String deviceImei, String userId, String appVersion, String androidVersion, String deviceInfo, String deviceToken) {
        //Implement this method if you want to store the token on your server
        try {
            String newurlparams = "imeiNo=" + URLEncoder.encode(deviceImei, "UTF-8");
            newurlparams += "&userId=" + URLEncoder.encode(userId, "UTF-8");
            newurlparams += "&appVersion=" + URLEncoder.encode(appVersion, "UTF-8");
            newurlparams += "&androidVersion=" + URLEncoder.encode(androidVersion, "UTF-8");
            newurlparams += "&deviceInfo=" + URLEncoder.encode(deviceInfo, "UTF-8");
            newurlparams += "&deviceToken=" + URLEncoder.encode(deviceToken, "UTF-8");

            networkConnect = new NetworkConnect(URLConstants.TOKEN_DATA, newurlparams);
            jsonparse_token(networkConnect.execute(), deviceToken);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

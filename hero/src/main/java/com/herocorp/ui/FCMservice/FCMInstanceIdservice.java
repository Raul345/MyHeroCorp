/*
package com.herocorp.ui.FCMservice;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.NetworkConnect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

*/
/**
 * Created by rsawh on 09-Oct-16.
 *//*

*/
/*public class FCMInstanceIdservice extends FirebaseInstanceIdService {
    private String deviceImei, userId, appVersion, androidVersion, deviceInfo, deviceToken;
    private static final String TAG = "HeroFCMIIDService";

    NetworkConnect networkConnect;

    public FCMInstanceIdservice() {
    }

    public FCMInstanceIdservice(String deviceImei, String userId, String appVersion, String androidVersion, String deviceInfo) {
        this.deviceImei = deviceImei;
        this.userId = userId;
        this.appVersion = appVersion;
        this.androidVersion = androidVersion;
        this.deviceInfo = deviceInfo;
    }

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        try {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + deviceToken);

          *//*
*/
/*  String data = "{\"imeiNo\":\"" + deviceImei + "\",\"userId\":\"" + userId + "\",\"appVersion\":\"" + appVersion + "\",\"androidVersion\":\"" + androidVersion +
                    "\",\"deviceInfo\":\"" + "ffd77" + "\",\"deviceToken\":\"" + deviceToken + "\"}";
          *//*
*/
/*

            //Log.e(TAG, "data: " + data);
            sendRegistrationToServer(deviceImei, userId, appVersion, androidVersion, "4445", deviceToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void jsonparse_token(String result) {
        try {
            // JSONObject jsono = new JSONObject(result);
            Log.e(TAG, "RESPONSE" + result);
        }*//*
*/
/* catch (JSONException e) {
            e.printStackTrace();
        }*//*
*/
/* catch (Exception e) {
            System.out.println(Toast.makeText(getApplicationContext(), "Check your Connection !!", Toast.LENGTH_SHORT));
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
            jsonparse_token(networkConnect.execute());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }*//*

}
*/

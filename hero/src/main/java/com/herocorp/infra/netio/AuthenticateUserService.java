package com.herocorp.infra.netio;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.core.interfaces.iNetworkResponseCallback;
import com.herocorp.core.models.AuthenticateUserModel;
import com.herocorp.infra.parsers.AppJsonParser;
import com.herocorp.ui.app.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hgodara on 12/08/16.
 */
public class AuthenticateUserService extends BaseNetIO {

    public static void authenticateUser(final String tag,
                                        String deviceId, String appVersion, String deviceVersion, final iNetworkResponseCallback<AuthenticateUserModel> callback) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imeiNo", String.valueOf("351971070473217"));
        jsonObject.put("appVersion", String.valueOf(appVersion));
        jsonObject.put("androidVersion", String.valueOf(deviceVersion));
        jsonObject.put("deviceInfo", String.valueOf(deviceVersion));

        JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(
                        Request.Method.POST,
                        URLConstants.BASE_URL + URLConstants.AUTHENTICATE_USER,
                        jsonObject,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response != null) {

                                        if (response.getBoolean("Success")) {
                                            ArrayList<AuthenticateUserModel> modelArrayList = new ArrayList<>(0);

                                            //Parse the JSON
                                            JSONArray responseDataString = response.getJSONArray("Result");
                                            for (int i = 0; i < responseDataString.length(); i++) {
                                                modelArrayList.add(AppJsonParser.getInstance()
                                                        .getObjectFromString(responseDataString.get(i).toString(),
                                                                AuthenticateUserModel.class));
                                            }
                                            callback.onSuccess(modelArrayList);

                                            return;
                                        }

                                        callback.onFailure(response.getString("Message"), true);

                                    }
                                } catch (Exception e) {
                                    Log.e(tag, "Failed ", e);
                                    callback.onFailure("Failed", true);
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onFailure(errorMessage(error), true);
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getAccessHeaders();
                    }
                };

        setTimeOut(jsonObjReq);
        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq, tag);

    }
}

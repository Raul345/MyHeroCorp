package com.herocorp.infra.netio;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Base Net IO class.
 * Created by JitainSharma on 09/06/16.
 */
public class BaseNetIO {

    protected static final int timeout = 50000;

    protected static Map<String, String> getAccessHeaders() throws AuthFailureError {

        Map<String, String> params = new HashMap<>();
        //No headers
        return params;
    }

    /**
     * Set the default timeouts for the connection.
     *
     * @param jsonObjReq
     */
    protected static void setTimeOut(JsonRequest jsonObjReq) {

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjReq.setShouldCache(false);
    }

    /**
     * Set the cache for the request.
     *
     * @param jsonObjReq
     */
    protected static void setCache(JsonRequest jsonObjReq) {
        jsonObjReq.setShouldCache(true);
    }

    /**
     * Type cast the message according to the exception type.
     *
     * @param error
     * @return
     */
    protected static String errorMessage(VolleyError error) {

        //Check exception type
        if (error instanceof TimeoutError) {
            return "Connection timed out, please try again.";
        } else if (error instanceof NoConnectionError) {
            return "Can't connect to internet, please retry.";
        } else {
            return "Some error has occurred, please try again.";
        }
    }

}
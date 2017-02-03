package com.herocorp.ui.activities.DSEapp.Fragment.PendingOrders;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.adapter.PendingOrdersadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Pendingorder;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by rsawh on 20-Sep-16.
 */
public class PendingOrdersFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private CustomViewParams customViewParams;

    private Handler handler = new Handler();
    ListView userList;
    PendingOrdersadapter userAdapter;
    ArrayList<Pendingorder> userArray = new ArrayList<Pendingorder>();
    TextView pendingorders_msg;
    String order_no;
    String dealer_code;
    String order_date;
    String cust_name;
    String mobile;
    String model_cd;
    String dse_name;
    String reason;
    String campaign;
    String expected_date;
    String financer_name;
    SwipeRefreshLayout swipe_refresh_orders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_pendingorders_fragment, container, false);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        initView(rootView);

        return rootView;
    }

    private void updateList() {
        userList.setAdapter(userAdapter);
        if (userAdapter.getCount() > 0)
            pendingorders_msg.setVisibility(View.INVISIBLE);
        else
            pendingorders_msg.setVisibility(View.VISIBLE);
        swipe_refresh_orders.setRefreshing(false);
    }

    public void onDestroy() {
        // Remove adapter reference from list
        userList.setAdapter(null);
        super.onDestroy();
    }

    private void initView(View rootView) {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{70, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        RelativeLayout topLayout1 = (RelativeLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        userAdapter = new PendingOrdersadapter(getContext(), R.layout.dse_pendingorder_row, userArray);
        userList = (ListView) rootView.findViewById(R.id.list_pendingorders);
        pendingorders_msg = (TextView) rootView.findViewById(R.id.pendingorders_message);
        swipe_refresh_orders = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_orders);
        customViewParams.setMarginAndPadding(swipe_refresh_orders, new int[]{100, 30, 100, 40}, new int[]{0, 0, 0, 0},swipe_refresh_orders.getParent());

        userList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userList.getChildAt(0) != null) {
                    swipe_refresh_orders.setEnabled(userList.getFirstVisiblePosition() == 0 && userList.getChildAt(0).getTop() == 0);
                }
            }
        });

        swipe_refresh_orders.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          swipe_refresh_orders.setRefreshing(true);
                                          send_request();
                                      }
                                  }
        );


        menu.setOnClickListener(this);
        swipe_refresh_orders.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        }

    }

    public void jsonparse(String result) {
        try {
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("order_data");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                order_no = object.getString("order_no");
                dealer_code = object.getString("dealer_code");
                order_date = object.getString("order_date");
                cust_name = object.getString("cust_name");
                mobile = object.getString("mobile");
                model_cd = object.getString("model_cd");
                dse_name = object.getString("dse_name");
                reason = object.getString("reason");
                campaign = object.getString("campaign");
                expected_date = object.getString("expected_date");
                financer_name = object.getString("financer_name");
                userAdapter.add(new Pendingorder(order_no, dealer_code, order_date, cust_name, mobile, model_cd, dse_name,
                        reason, campaign, expected_date, financer_name));
                userAdapter.notifyDataSetChanged();
            }
            updateList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));

        }
    }

    @Override
    public void onRefresh() {
        send_request();
    }

    public class Pending_orders extends AsyncTask<Void, Void, String> {
        String newurlParameters;
        NetworkConnect networkConnect;
        String result;

        public Pending_orders(String urlParameters) {
            this.newurlParameters = urlParameters;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipe_refresh_orders.setRefreshing(true);
        }

        protected String doInBackground(Void... params) {
            if (NetConnections.isConnected(getContext())) {
                try {
                    String newurlparams = "data=" + URLEncoder.encode(newurlParameters, "UTF-8");
                    networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
                    String data = networkConnect.execute();
                    String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
                    networkConnect = new NetworkConnect(URLConstants.PENDING_ORDER, urldata);
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
                Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                Log.e("order_data:", result);
                JSONObject jsono = new JSONObject(result);
                if (jsono.has("order_data")) {
                    JSONArray jarray = jsono.getJSONArray("order_data");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        order_no = object.getString("order_no");
                        dealer_code = object.getString("dealer_code");
                        order_date = object.getString("order_date");
                        cust_name = object.getString("cust_name");
                        mobile = object.getString("mobile");
                        model_cd = object.getString("model_cd");
                        dse_name = object.getString("dse_name");
                        reason = object.getString("reason");
                        campaign = object.getString("campaign");
                        expected_date = object.getString("expected_date");
                        financer_name = object.getString("financer_name");
                        userAdapter.add(new Pendingorder(order_no, dealer_code, order_date, cust_name, mobile, model_cd, dse_name,
                                reason, campaign, expected_date, financer_name));
                        userAdapter.notifyDataSetChanged();
                    }
                }
                updateList();
            } catch (JSONException e) {
                swipe_refresh_orders.setRefreshing(false);
                e.printStackTrace();
            } catch (Exception e) {
                swipe_refresh_orders.setRefreshing(false);
                pendingorders_msg.setVisibility(View.VISIBLE);
                e.printStackTrace();
               // Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
            }
        }
    }

    public void send_request() {
        try {
            JSONObject jsonparms = new JSONObject();
            jsonparms.put("user_id", PreferenceUtil.get_UserId(getContext()));
            jsonparms.put("dealer_code", PreferenceUtil.get_DealerCode(getContext()));

            Log.e("pendingorder:", jsonparms.toString());
            new Pending_orders(jsonparms.toString()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
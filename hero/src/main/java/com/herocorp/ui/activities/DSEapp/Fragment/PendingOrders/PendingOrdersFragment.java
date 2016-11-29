package com.herocorp.ui.activities.DSEapp.Fragment.PendingOrders;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.adapter.PendingOrdersadapter;
import com.herocorp.ui.activities.DSEapp.models.Pendingorder;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by rsawh on 20-Sep-16.
 */
public class PendingOrdersFragment extends Fragment implements View.OnClickListener {
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

    NetworkConnect networkConnect;
    String user_id="ROBINK11610", code="11610";

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
            pendingorders_msg.setVisibility(View.GONE);
        else
            pendingorders_msg.setVisibility(View.VISIBLE);
        //   progressbar.setVisibility(View.GONE);
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
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 30, 100, 40}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        userAdapter = new PendingOrdersadapter(getContext(), R.layout.dse_pendingorder_row, userArray);
        userList = (ListView) rootView.findViewById(R.id.list_pendingorders);
        pendingorders_msg = (TextView) rootView.findViewById(R.id.pendingorders_message);


        try {

            Bundle bundle = this.getArguments();
            //  String encryptdata = bundle.getString("user_id");
            JSONObject jsonparms = new JSONObject();
            // jsonparms.put("user_id", bundle.getString("user"));
            //  user_id = bundle.getString("user");


            jsonparms.put("user_id", user_id);
            jsonparms.put("dealer_code", code);

            // jsonparms.put("dealer_code",code);

            Log.e("pendingorder:", jsonparms.toString());
            String newurlparams = "data=" + URLEncoder.encode(jsonparms.toString(), "UTF-8");
            networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
            String data = networkConnect.execute();

            String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
            networkConnect = new NetworkConnect(URLConstants.PENDING_ORDER, urldata);

            jsonparse(networkConnect.execute());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar_pendingorders);
        //   progressbar.setVisibility(View.VISIBLE);
        menu.setOnClickListener(this);
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


}

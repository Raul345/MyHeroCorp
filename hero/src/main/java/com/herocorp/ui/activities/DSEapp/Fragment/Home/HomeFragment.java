package com.herocorp.ui.activities.DSEapp.Fragment.Home;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.herocorp.ui.activities.DSEapp.Utilities.Syncmakemodel;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

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

    String user_id, dealer_code, sync_date = "";
    String current_date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_home_fragment, container, false);
        ((BaseDrawerActivity)getActivity()).closeDrawer();
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRetainInstance(true);
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
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);

        customViewParams.setImageViewCustomParams(pendingorder, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(todayfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(pendingfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(searchenquiry, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);

        LinearLayout textview_container = (LinearLayout) rootView.findViewById(R.id.textview_container);
        customViewParams.setMarginAndPadding(textview_container, new int[]{120, 30, 110, 0}, new int[]{0, 0, 0, 0}, textview_container.getParent());

        phoneno_et = (EditText) rootView.findViewById(R.id.phoneno_edittext);
        registration_et = (EditText) rootView.findViewById(R.id.registration_edittext);

        fetch_pref();
        current_date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
        if (!(sync_date.equalsIgnoreCase(current_date.toString()) && NetConnections.isConnected(getContext())))
            sync_data();
            //new Syncmakemodel(getContext()).execute();

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
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            pendingorderText.setTextColor(Color.WHITE);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);
            // progress();
            if (NetConnections.isConnected(getContext())) {
                transaction(new PendingOrdersFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.followup) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            // Toast.makeText(getActivity(), "today followup", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.WHITE);
            searchenquiryText.setTextColor(Color.GRAY);

            check = 0;
            flag = 0;

            transaction(new TodayFollowupFragment());

        } else if (i == R.id.pendingfollowup) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            //  Toast.makeText(getActivity(), "pendidng followup", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.WHITE);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);

            transaction(new PendingFollowupFragment());

        } else if (i == R.id.searchenquiry) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            // Toast.makeText(getActivity(), "search enquiry", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.WHITE);
            flag = 1;
            check = 1;
            transaction(new TodayFollowupFragment());
        } else if (i == R.id.imageView_submit_home) {
            try {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                int check = 1;
                if (phoneno_et.getText().toString().equals("") && registration_et.getText().toString().equals("")) {
                    check = 0;
                    Toast.makeText(getActivity(), "Phone/RegNo missing!!", Toast.LENGTH_SHORT).show();
                } else if (!phoneno_et.getText().toString().equals("") && phoneno_et.getText().toString().length() < 10) {
                    check = 0;
                    Toast.makeText(getActivity(), "Incorrect Phone No!!", Toast.LENGTH_SHORT).show();
                }
                if (check == 1) {
                    if (NetConnections.isConnected(getContext())) {
                        transaction(new ContactFragment());
                    } else
                        Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
                }
              } catch (Exception e) {
            }
        }
    }

    public void transaction(final Fragment f) {
        bundle.putInt("check", check);
        bundle.putInt("flag", flag);
        bundle.putString("phone_no", phoneno_et.getText().toString());
        bundle.putString("reg_no", registration_et.getText().toString());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        f.setArguments(bundle);
        ft.add(R.id.content_dsehome, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void sync_data() {
        final JSONObject jsonparams = new JSONObject();

        if (NetConnections.isConnected(getContext())) {
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
                                Log.e("make_sync_start", current_date.toString());
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

                    getContext(),

                    "Check your connection !!", Toast.LENGTH_SHORT).

                    show();

    }


    public void jsonparse_makemodel(String result) {
        try {
            db = new DatabaseHelper(getContext());
            db.clearmakemodel_table();

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

            PreferenceUtil.set_MakeSyncdate(getContext(), current_date.toString());

            Log.e("make_sync_close", current_date.toString());
            //   progressBar.setVisibility(View.INVISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
        }
    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(getContext());
        dealer_code = PreferenceUtil.get_DealerCode(getContext());
        sync_date = PreferenceUtil.get_MakeSyncdate(getContext());
    }

}
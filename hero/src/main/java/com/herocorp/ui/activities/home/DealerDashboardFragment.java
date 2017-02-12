package com.herocorp.ui.activities.home;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.AppConstants;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.Home.HomeFragment;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Pitch;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.activities.news.Fragment.NewsFragment;
import com.herocorp.ui.activities.products.ProductDetailFragment;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DealerDashboardFragment extends Fragment implements View.OnClickListener {

    private String TAG = "HeroCorp";
    private SharedPreferences sharedPreferences;

    DatabaseHelper db;
    String urlParameters;
    String encryptuser;
    NetworkConnect networkConnect;
    String current_date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View rootView = inflater.inflate(R.layout.dealer_dashboard_fragment, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        initView(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void initView(View rootView) {

        CustomViewParams customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView logoImageView = (ImageView) rootView.findViewById(R.id.logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(logoImageView, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 260, 210);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout containerLayout = (LinearLayout) rootView.findViewById(R.id.buttons_container);
        customViewParams.setMarginAndPadding(containerLayout, new int[]{10, 10, 10, 10}, new int[]{0, 0, 0, 0}, containerLayout.getParent());

        LinearLayout productsLayout = (LinearLayout) rootView.findViewById(R.id.products_layout);
        LinearLayout valueLayout = (LinearLayout) rootView.findViewById(R.id.value_layout);
        LinearLayout newsLayout = (LinearLayout) rootView.findViewById(R.id.news_layout);
        LinearLayout contactUsLayout = (LinearLayout) rootView.findViewById(R.id.contact_us_layout);

        customViewParams.setMarginAndPadding(productsLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, productsLayout.getParent());
        customViewParams.setMarginAndPadding(valueLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, valueLayout.getParent());
        customViewParams.setMarginAndPadding(newsLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, newsLayout.getParent());
        customViewParams.setMarginAndPadding(contactUsLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, contactUsLayout.getParent());

        customViewParams.setHeightAndWidth(productsLayout, 320, 320);
        customViewParams.setHeightAndWidth(valueLayout, 320, 320);
        customViewParams.setHeightAndWidth(newsLayout, 320, 320);
        customViewParams.setHeightAndWidth(contactUsLayout, 320, 320);

        TextView productsText = (TextView) rootView.findViewById(R.id.products_text);
        TextView valueText = (TextView) rootView.findViewById(R.id.value_text);
        TextView newsText = (TextView) rootView.findViewById(R.id.news_text);
        TextView contactUsText = (TextView) rootView.findViewById(R.id.contact_us_text);

        customViewParams.setTextViewCustomParams(productsText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(valueText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(newsText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(contactUsText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);

        ImageView products = (ImageView) rootView.findViewById(R.id.products);
        ImageView valueAddedServices = (ImageView) rootView.findViewById(R.id.value_added_service);
        ImageView news = (ImageView) rootView.findViewById(R.id.news);
        ImageView contactUs = (ImageView) rootView.findViewById(R.id.contact_us);

        customViewParams.setImageViewCustomParams(products, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 100, 100);
        customViewParams.setImageViewCustomParams(valueAddedServices, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 100, 100);
        customViewParams.setImageViewCustomParams(news, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 100, 100);
        customViewParams.setImageViewCustomParams(contactUs, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 100, 100);

        TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 10}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);

        current_date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
        if (!(PreferenceUtil.get_MakeSyncdate(getContext()).equalsIgnoreCase(current_date.toString()) && NetConnections.isConnected(getContext())))
            sync_data();

        menu.setOnClickListener(this);
        productsLayout.setOnClickListener(this);
        contactUsLayout.setOnClickListener(this);
        valueLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();


        } else if (i == R.id.products_layout) {
            if (sharedPreferences.getBoolean(AppConstants.IS_SYNC_COMPLETED, false)) {
                ((BaseDrawerActivity) getActivity()).openFragment(new ProductDetailFragment(), true);
            } else {
                Toast.makeText(getActivity(), "Application is not synced properly, please sync it first.", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.contact_us_layout) {
            try {
                getActivity().setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                ((BaseDrawerActivity) getActivity()).openFragment(new HomeFragment(), true);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "DSE App not installed!", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.value_layout) {
            try {

                //  ((BaseDrawerActivity) getActivity()).openFragment(new VasWarrantyfragment(), true);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "VAS  not installed!", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.news_layout) {
            try {
                ((BaseDrawerActivity) getActivity()).openFragment(new NewsFragment(), true);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "VAS  not installed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void sync_data() {
        final JSONObject jsonparams = new JSONObject();

        if (NetConnections.isConnected(getContext())) {
            final Handler handler = new Handler();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        //  int i = 0;
                        while (encryptuser == null) {
                            try {
                                jsonparams.put("user_id", PreferenceUtil.get_UserId(getContext()));
                                Log.e("make_model_request:", jsonparams.toString());
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
                                }
                                // i++;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            handler.post(this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            Log.e("make_model_response:", result);

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

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
        }
    }

}
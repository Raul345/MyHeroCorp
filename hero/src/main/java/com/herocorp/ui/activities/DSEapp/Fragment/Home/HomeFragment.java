package com.herocorp.ui.activities.DSEapp.Fragment.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
import com.herocorp.ui.activities.DSEapp.Pitch.ImageLoader;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Pitch;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by rsawh on 10-Sep-16.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    EditText phoneno_et, registration_et;
    ProgressDialog progressDialog, progressBar;
    ;
    NetworkConnect networkConnect;
    String encryptuser;
    Bundle bundle = new Bundle();
    int check = 0, flag = 0;
    DatabaseHelper db;
    String urlParameters;
    TextView pendingorderText, todayfollowupText, pendingfollowupText, searchenquiryText;
    String user_id, dealer_code, sync_date = "";
    String current_date;
    ImageView image1, image2, image3;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        rootView = inflater.inflate(R.layout.dse_home_fragment, container, false);

        ((BaseDrawerActivity) getActivity()).closeDrawer();
        try {
            initView(rootView);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        current_date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
        if (!(sync_date.equalsIgnoreCase(current_date.toString())) && NetConnections.isConnected(getContext())) {
            progressDialog = ProgressDialog.show(getActivity(), null, null);
            progressDialog.setContentView(R.layout.progresslayout);
           /* progressBar = new ProgressDialog(getActivity());
            progressBar.setCancelable(false);
            progressBar.setMessage("Data is Syncing");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(Math.round(0));
            progressBar.setMax(100);
            progressBar.show();*/

            try {
                clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sync_data();
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

        customViewParams.setImageViewCustomParams(pendingorder, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(todayfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(pendingfollowup, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);
        customViewParams.setImageViewCustomParams(searchenquiry, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 180, 180);

        LinearLayout textview_container = (LinearLayout) rootView.findViewById(R.id.textview_container);
        customViewParams.setMarginAndPadding(textview_container, new int[]{120, 30, 110, 0}, new int[]{0, 0, 0, 0}, textview_container.getParent());

        phoneno_et = (EditText) rootView.findViewById(R.id.phoneno_edittext);
        phoneno_et.setRawInputType(Configuration.KEYBOARD_12KEY);
        registration_et = (EditText) rootView.findViewById(R.id.registration_edittext);
        registration_et.setRawInputType(Configuration.KEYBOARD_12KEY);
        image1 = (ImageView) rootView.findViewById(R.id.imageloader1);
        image2 = (ImageView) rootView.findViewById(R.id.imageloader2);
        image3 = (ImageView) rootView.findViewById(R.id.imageloader3);

        fetch_pref();

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
            if (NetConnections.isConnected(getContext())) {
                transaction(new PendingOrdersFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.followup) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.WHITE);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);

            transaction(new PendingFollowupFragment());

        } else if (i == R.id.searchenquiry) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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
                                Log.e("pitch_sync_start", current_date.toString());
                                networkConnect = new NetworkConnect(URLConstants.FETCH_PITCH, urlParameters);
                                jsonparse_pitch(networkConnect.execute());
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


    public void jsonparse_pitch(String result) {
        try {
            db = new DatabaseHelper(getActivity());
            db.clearpitch();
            Log.e("response_pitch", result);
            JSONObject jsonObject = new JSONObject(result);
            //  final ImageLoader imageLoader = new ImageLoader(getContext());
            final JSONArray jarray = jsonObject.getJSONArray("pitch");
            //  progressBar.setProgress(30);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);
                String id = jobj.getString("id");
                String gender = jobj.getString("gender");
                String age = jobj.getString("age");
                String occupation = jobj.getString("occupation");
                String ownership = jobj.getString("existing_ownership");
                String usage = jobj.getString("intended_usage");
                String area = jobj.getString("urban_rural");
                String city = "";
                if (jobj.has("city"))
                    city = jobj.getString("city");
                final String imgpath = jobj.getString("img_path");

                db.add_pitch(new Pitch(id, gender, age, occupation, ownership, usage, area
                        , city, imgpath));

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            ImageLoader imageLoader = new ImageLoader(getContext());
                            JSONObject j = new JSONObject(imgpath);
                            String pleasure = j.getString("Pleasure");
                            String maestro = j.getString("Maestro Edge");
                            String duet = j.getString("Duet");
                            Log.e("p_url", pleasure);
                            Log.e("m_url", maestro);
                            Log.e("d_url", duet);
                            imageLoader.DisplayImage(pleasure, image1);
                            imageLoader.DisplayImage(maestro, image2);
                            imageLoader.DisplayImage(duet, image3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // progressBar.setProgress(70);
            }
            // progressBar.setProgress(100);
            PreferenceUtil.set_PitchSyncdate(getContext(), current_date.toString());
            Log.e("pitch_sync_close", current_date.toString());
            progressDialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
        }
    }

    public void fetch_logics() {
        db = new DatabaseHelper(getContext());
        List<Pitch> allrecords = db.getAllPitch();
        for (Pitch record : allrecords) {
            Log.e("fetch_logic", record.getId() + record.getGender() + record.getAge() + record.getOccupation() + record.getOwnership() + record.getArea() + record.getUsage() + record.getImg_path());
        }

    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(getContext());
        dealer_code = PreferenceUtil.get_DealerCode(getContext());
        sync_date = PreferenceUtil.get_PitchSyncdate(getContext());
    }

    public void clear() {
        File f = new File(Environment.getExternalStorageDirectory() + "/" + "HeroSales");
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                c.delete();
        }
    }
}
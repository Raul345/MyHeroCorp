package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by rsawh on 03-Oct-16.
 */
public class TestRideFeedbackFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    RadioGroup radiogroup1, radiogroup2, radiogroup3, radiogroup4, radiogroup5, radiogroup6;
    RadioButton radiobutton1, radiobutton2, radiobutton3, radiobutton4, radiobutton5, radiobutton6;
    EditText prefer_et;
    String enquiry_id = "", dealer_code = "", key = "";
    String ans1, ans2, ans3, ans4, ans5, ans6, ans7;
    NetworkConnect networkConnect;

    SharedPreferences mypref;
    private SharedPreferences sharedPreferences;
    String user_id;
    LinearLayout layout_vehiclerating, layout_ridingperform, layout_vehiclefeatures, layout_buyplan, layout_vehiclerefer, layout_comments, layout_overallrating;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_testride_fragment, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(final View rootView) {
        mypref = getActivity().getSharedPreferences("herocorp", 0);
        if (mypref.contains("key")) {
            key = mypref.getString("key", "");
        }
        if (mypref.contains("enquiry_id")) {
            enquiry_id = mypref.getString("enquiry_id", "");
        }
       /* if (mypref.contains("dealerid")) {
            dealer_code = mypref.getString("dealerid", "");
        }*/

        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        layout_vehiclerating = (LinearLayout) rootView.findViewById(R.id.layout_vehiclerating);
        layout_ridingperform = (LinearLayout) rootView.findViewById(R.id.layout_ridingperform);
        layout_vehiclefeatures = (LinearLayout) rootView.findViewById(R.id.layout_vehiclefeatures);
        layout_buyplan = (LinearLayout) rootView.findViewById(R.id.layout_buyplan);
        layout_vehiclerefer = (LinearLayout) rootView.findViewById(R.id.layout_vehiclerefer);
        layout_comments = (LinearLayout) rootView.findViewById(R.id.layout_comments);
        layout_overallrating = (LinearLayout) rootView.findViewById(R.id.layout_overallrating);

        ImageView imageView_submit = (ImageView) rootView.findViewById(R.id.imageview_submit_feedback);

        radiogroup1 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        radiogroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup2);
        radiogroup3 = (RadioGroup) rootView.findViewById(R.id.radioGroup3);
        radiogroup4 = (RadioGroup) rootView.findViewById(R.id.radioGroup4);
        radiogroup5 = (RadioGroup) rootView.findViewById(R.id.radioGroup5);
        radiogroup6 = (RadioGroup) rootView.findViewById(R.id.radioGroup6);

        prefer_et = (EditText) rootView.findViewById(R.id.prefer_edittext);


        menu.setOnClickListener(this);
        setlayout();
        fetch_pref();
        imageView_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectedindex1, selectedindex2, selectedindex3, selectedindex4, selectedindex5, selectedindex6;
                    if (radiogroup1.getCheckedRadioButtonId() == -1) {
                        selectedindex1 = -1;
                        ans1 = "";
                    } else {
                        selectedindex1 = radiogroup1.getCheckedRadioButtonId();
                        radiobutton1 = (RadioButton) rootView.findViewById(selectedindex1);
                        ans1 = radiobutton1.getText().toString();
                    }

                    if (radiogroup2.getCheckedRadioButtonId() == -1) {
                        selectedindex2 = -1;
                        ans2 = "";
                    } else {
                        selectedindex2 = radiogroup2.getCheckedRadioButtonId();
                        radiobutton2 = (RadioButton) rootView.findViewById(selectedindex2);
                        ans2 = radiobutton2.getText().toString();
                    }
                    if (radiogroup3.getCheckedRadioButtonId() == -1) {
                        selectedindex3 = -1;
                        ans3 = "";
                    } else {
                        selectedindex3 = radiogroup3.getCheckedRadioButtonId();
                        radiobutton3 = (RadioButton) rootView.findViewById(selectedindex3);
                        ans3 = radiobutton3.getText().toString();
                    }
                    if (radiogroup4.getCheckedRadioButtonId() == -1) {
                        selectedindex4 = -1;
                        ans4 = "";
                    } else {
                        selectedindex4 = radiogroup4.getCheckedRadioButtonId();
                        radiobutton4 = (RadioButton) rootView.findViewById(selectedindex4);
                        ans4 = radiobutton4.getText().toString();
                    }

                    if (radiogroup5.getCheckedRadioButtonId() == -1) {
                        {
                            selectedindex5 = -1;
                            ans5 = "";
                        }
                    } else {
                        selectedindex5 = radiogroup5.getCheckedRadioButtonId();
                        radiobutton5 = (RadioButton) rootView.findViewById(selectedindex5);
                        ans5 = radiobutton5.getText().toString();
                    }
                    if (radiogroup6.getCheckedRadioButtonId() == -1) {
                        selectedindex6 = -1;
                        ans7 = "";
                    } else {
                        selectedindex6 = radiogroup6.getCheckedRadioButtonId();
                        radiobutton6 = (RadioButton) rootView.findViewById(selectedindex6);
                        ans7 = radiobutton6.getText().toString();
                    }

                    if (layout_comments.getVisibility() == View.VISIBLE)
                        ans6 = prefer_et.getText().toString();


                    JSONObject jsonparams = new JSONObject();
                    jsonparams.put("user_id", user_id);
                    jsonparams.put("enq_id", enquiry_id);
                    jsonparams.put("dealer_id", dealer_code);
                    jsonparams.put("key", key);

                    JSONObject jsonparams1 = new JSONObject();
                    jsonparams1.put("1", ans1);
                    jsonparams1.put("2", ans2);
                    jsonparams1.put("3", ans3);
                    jsonparams1.put("4", ans4);
                    jsonparams1.put("5", ans5);
                    if (layout_comments.getVisibility() == View.VISIBLE) {
                        jsonparams1.put("6", ans6);
                        jsonparams1.put("7", ans7);
                    } else
                        jsonparams1.put("6", ans7);

                    jsonparams.put("ans", jsonparams1.toString());
                    Log.e("testride_data", jsonparams.toString());
                    encryptuser(URLConstants.SYNC_TEST_RIDE, jsonparams.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.imageview_submit_feedback) {

        }
    }

    public void encryptuser(String url, String data) {
        ProgressDialog progress = new ProgressDialog(getContext());
        if (NetConnections.isConnected(getContext())) {
            try {
                /*String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");

                networkConnect = new NetworkConnect("http://abym.in/clientProof/hero_motors/encrypt", urlParameters);

                String result = networkConnect.execute();
                if (result != null)
                    encryptdata = result.replace("\\/", "/");
*/
                //clearing key
               /* if (mypref.contains("key")) {
                    mypref.edit().remove("key").commit();
                }*/
              /*  if (mypref.contains("enquiry_id")) {
                    mypref.edit().remove("enquiry_id").commit();
                }
                if (mypref.contains("dealerid")) {
                    mypref.edit().remove("dealerid").commit();
                }
              */
                String newurlparams = "data=" + URLEncoder.encode(data, "UTF-8");
                new NetworkConnect1(url, newurlparams, progress, "Test Ride Feedback has been successfully submitted.", getContext(), 3).execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(getContext());
        dealer_code = PreferenceUtil.get_DealerCode(getContext());

    }

    public void setlayout() {
        Bundle bundle = this.getArguments();
        if (bundle.containsKey("quesid")) {
            ArrayList<String> quesid = bundle.getStringArrayList("quesid");

            if (quesid.contains("How would you rate the vehicle in terms of styling?"))
                layout_vehiclerating.setVisibility(View.VISIBLE);
            else
                layout_vehiclerating.setVisibility(View.GONE);
            if (quesid.contains("What do you think about the riding performance of the Vehicle?"))
                layout_ridingperform.setVisibility(View.VISIBLE);
            else
                layout_ridingperform.setVisibility(View.GONE);
            if (quesid.contains("Are you happy with the features provided in the Vehicle?"))
                layout_vehiclefeatures.setVisibility(View.VISIBLE);
            else
                layout_vehiclefeatures.setVisibility(View.GONE);
            if (quesid.contains("When are you planning to buy the Vehicle?"))
                layout_buyplan.setVisibility(View.VISIBLE);
            else
                layout_buyplan.setVisibility(View.GONE);
            if (quesid.contains("Will you refer this Vehicle to your relatives and friends?"))
                layout_vehiclerefer.setVisibility(View.VISIBLE);
            else
                layout_vehiclerefer.setVisibility(View.GONE);
            if (quesid.contains("Do you have any other comments that you would like to share with us?"))
                layout_comments.setVisibility(View.VISIBLE);
            else
                layout_comments.setVisibility(View.GONE);
            if (quesid.contains("OVERALL RATING"))
                layout_overallrating.setVisibility(View.VISIBLE);
            else
                layout_overallrating.setVisibility(View.GONE);
        }
    }
}

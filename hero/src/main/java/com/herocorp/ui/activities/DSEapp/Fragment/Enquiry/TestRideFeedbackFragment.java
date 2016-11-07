package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by rsawh on 03-Oct-16.
 */
public class TestRideFeedbackFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    RadioGroup radiogroup1, radiogroup2, radiogroup3, radiogroup4, radiogroup5, radiogroup6;
    RadioButton radiobutton1, radiobutton2, radiobutton3, radiobutton4, radiobutton5, radiobutton6;
    EditText prefer_et;
    String preference;
    String username = "ROBINK11610", enquiry_id = "", dealer_id = "", dealercode = "11610", key = "", encryptdata;
    String ans1, ans2, ans3, ans4, ans5, ans6, ans7;
    NetworkConnect networkConnect;

    SharedPreferences mypref;

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
        if (mypref.contains("dealerid")) {
            dealer_id = mypref.getString("dealerid", "");
        }


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


        ImageView imageView_submit = (ImageView) rootView.findViewById(R.id.imageview_submit_feedback);

        radiogroup1 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        radiogroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup2);
        radiogroup3 = (RadioGroup) rootView.findViewById(R.id.radioGroup3);
        radiogroup4 = (RadioGroup) rootView.findViewById(R.id.radioGroup4);
        radiogroup5 = (RadioGroup) rootView.findViewById(R.id.radioGroup5);
        radiogroup6 = (RadioGroup) rootView.findViewById(R.id.radioGroup6);

        prefer_et = (EditText) rootView.findViewById(R.id.prefer_edittext);

        menu.setOnClickListener(this);
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

                    ans6 = prefer_et.getText().toString();

                  /*  String json = "{\"user_id\":\"" + username +
                            "\",\"enq_id\":\"" + enquiry_id +
                            "\",\"dealer_id\":\"" + dealer_id +
                            "\",\"key\":\"" + key +
                            "\",\"ans\":\"{\"1\":\"" + ans1 +
                            "\",\"2\":\"" + ans2 +
                            "\",\"3\":\"" + ans3 +
                            "\",\"4\":\"" + ans4 +
                            "\",\"5\":\"" + ans5 +
                            "\",\"6\":\"" + ans6 +
                            "\",\"7\":\"" + ans7 +
                            "\"}\"}";
*/

                   /* String answers = "{\"1\":\"" + ans1 +
                            "\",\"2\":\"" + ans2 +
                            "\",\"3\":\"" + ans3 +
                            "\",\"4\":\"" + ans4 +
                            "\",\"5\":\"" + ans5 +
                            "\",\"6\":\"" + ans6 +
                            "\",\"7\":\"" + ans7 +
                            "\"}";*/
                    String json = "{\"user_id\":\"" + username +
                            "\",\"enq_id\":\"" + enquiry_id +
                            "\",\"dealer_id\":\"" + dealer_id +
                            "\",\"key\":\"" + key +
                            "\",\"ans\":\"\"{\"1\":\"" + ans1 +
                            "\",\"2\":\"" + ans2 +
                            "\",\"3\":\"" + ans3 +
                            "\",\"4\":\"" + ans4 +
                            "\",\"5\":\"" + ans5 +
                            "\",\"6\":\"" + ans6 +
                            "\",\"7\":\"" + ans7 +
                            "\"}\"\"}";

                    Toast.makeText(getContext(), json, Toast.LENGTH_LONG).show();
                    encryptuser(URLConstants.SYNC_TEST_RIDE, json);
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
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");

                networkConnect = new NetworkConnect("http://abym.in/clientProof/hero_motors/encrypt", urlParameters);

                String result = networkConnect.execute();
                if (result != null)
                    encryptdata = result.replace("\\/", "/");

                //clearing key
                if (mypref.contains("key")) {
                    mypref.edit().remove("key").commit();
                }
              /*  if (mypref.contains("enquiry_id")) {
                    mypref.edit().remove("enquiry_id").commit();
                }
                if (mypref.contains("dealerid")) {
                    mypref.edit().remove("dealerid").commit();
                }
              */
                String newurlparams = "data=" + URLEncoder.encode(encryptdata, "UTF-8");
                new NetworkConnect1(url, newurlparams, progress, "Test Ride Feedback has been successfully submitted.", getContext(), 3).execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }

}

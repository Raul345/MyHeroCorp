package com.herocorp.ui.activities.DSEapp.Fragment.Followup;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.TestRideFeedbackFragment;
import com.herocorp.ui.activities.DSEapp.models.Pendingorder;
import com.herocorp.ui.activities.auth.SignInActivity;
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
 * Created by rsawh on 26-Sep-16.
 */
public class FollowupDetailFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    TextView name, mobile, email, agesex, cust_id, model, expt_date, exchange_reqd, finance_reqd, exist_vehicle, follow_comments, nextfollow_date;
    Button button_close, button_edit, button_followup, button_testridefeedback;

    String user, encryptuser, enquiryid;
    String first_name;
    String last_name;
    String cell_ph_no;
    String age;
    String gender;
    String email_addr;
    String state;
    String district;
    String tehsil;
    String city;
    String x_con_seq_no;
    String x_model_interested;
    String expected_date_purchase;
    String x_exchange_required;
    String x_finance_required;
    String existvehicle;
    String followup_comments;
    String enquiry_id;
    String follow_date;
    String dealerid;
    String occupation;
    String area;
    String usage;
    String two_wheeler_type;
    String sales_pitch_no = "";


    SharedPreferences mypref;
    SharedPreferences.Editor edit;
    Fragment f;
    ProgressBar progressBar;
    int page_flag = 0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_followupdetail_fragment, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        mypref = getActivity().getSharedPreferences("herocorp", 0);
        mypref.edit().clear().commit();
        edit = mypref.edit();

        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{120, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 10, 100, 40}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);

        name = (TextView) rootView.findViewById(R.id.cust_name);
        mobile = (TextView) rootView.findViewById(R.id.cust_mobile);
        agesex = (TextView) rootView.findViewById(R.id.cust_agesex);
        email = (TextView) rootView.findViewById(R.id.cust_email);
        cust_id = (TextView) rootView.findViewById(R.id.cust_id);
        model = (TextView) rootView.findViewById(R.id.int_model);
        expt_date = (TextView) rootView.findViewById(R.id.expt_purch_date);
        exchange_reqd = (TextView) rootView.findViewById(R.id.exch_reqd);
        finance_reqd = (TextView) rootView.findViewById(R.id.finance_reqd);
        exist_vehicle = (TextView) rootView.findViewById(R.id.exist_vehicletype);
        follow_comments = (TextView) rootView.findViewById(R.id.followup_comments);
        nextfollow_date = (TextView) rootView.findViewById(R.id.nextfollow_date);

        button_close = (Button) rootView.findViewById(R.id.button_close);
        button_followup = (Button) rootView.findViewById(R.id.button_followup);
        button_edit = (Button) rootView.findViewById(R.id.button_edit);
        button_testridefeedback = (Button) rootView.findViewById(R.id.button_testride);

        fetch_data();
        mobile.setPaintFlags(mobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        name.setText(first_name + " " + last_name);
        mobile.setText(cell_ph_no);
        agesex.setText(age + "yrs. / " + gender);

        email.setText(email_addr);
        cust_id.setText(" " + x_con_seq_no);
        model.setText(x_model_interested);
        expt_date.setText(expected_date_purchase);
        exchange_reqd.setText(x_exchange_required);
        finance_reqd.setText(x_finance_required);
        exist_vehicle.setText(existvehicle);

        String comment;
        if (!follow_comments.equals("")) {
            comment = followup_comments.replace("~", "\n");
            follow_comments.setText(comment);
        } else
            follow_comments.setText(followup_comments);

        nextfollow_date.setText(follow_date);
        menu.setOnClickListener(this);
        button_edit.setOnClickListener(this);
        button_close.setOnClickListener(this);
        button_followup.setOnClickListener(this);
        button_testridefeedback.setOnClickListener(this);
        mobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("user_id", encryptuser);
        bundle.putString("user", user);
        bundle.putString("enquiry_id", enquiryid);
        bundle.putString("fname", first_name);
        bundle.putString("lname", last_name);
        bundle.putString("mobile", cell_ph_no);
        bundle.putString("age", age);
        bundle.putString("sex", gender);
        bundle.putString("email", email_addr);
        bundle.putString("state", state);
        bundle.putString("district", district);
        bundle.putString("tehsil", tehsil);
        bundle.putString("city", city);
        bundle.putString("model", x_model_interested);
        bundle.putString("id", x_con_seq_no);
        bundle.putString("pur_date", expected_date_purchase);
        bundle.putString("exchange", x_exchange_required);
        bundle.putString("finance", x_finance_required);
        bundle.putString("vtype", existvehicle);
        bundle.putString("comment", followup_comments);
        bundle.putString("followdate", follow_date);
        bundle.putString("enquiryid", enquiry_id);
        bundle.putString("sales_pitch_no", sales_pitch_no);
        if (page_flag == 1)
            bundle.putInt("enq_flag", page_flag);

        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        } else if (i == R.id.button_close) {
            button_close.setBackgroundColor(Color.DKGRAY);
            button_followup.setBackgroundColor(Color.RED);
            button_edit.setBackgroundColor(Color.RED);
            f = new CloseFollowupFragment();
            f.setArguments(bundle);
            transaction(f);
        } else if (i == R.id.button_followup) {
            button_close.setBackgroundColor(Color.RED);
            button_followup.setBackgroundColor(Color.DKGRAY);
            button_edit.setBackgroundColor(Color.RED);
            f = new FollowupFragment();
            f.setArguments(bundle);
            transaction(f);
        } else if (i == R.id.button_edit) {
            if (NetConnections.isConnected(getContext())) {
                button_close.setBackgroundColor(Color.RED);
                button_followup.setBackgroundColor(Color.RED);
                button_edit.setBackgroundColor(Color.DKGRAY);
                PreferenceUtil.set_Mode(getActivity(), "2");

                f = new EditFollowupFragment();
                f.setArguments(bundle);
                transaction(f);
            } else
                Toast.makeText(getContext(), "You are offline now !!", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.cust_mobile) {
            bundle.putString("header", "Are you Sure?");
            bundle.putString("msg", "Call this number " + cell_ph_no);
            bundle.putInt("flag", 0);
            FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
            ContactAlertFragment dialogFragment = new ContactAlertFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.setCancelable(false);
            dialogFragment.show(fm, "Sample Fragment");
        } else if (i == R.id.button_testride) {
            // edit.putString("enquiryid", enquiry_id);
            if (NetConnections.isConnected(getContext())) {
                try {
                    JSONObject jsonparams = new JSONObject();
                    jsonparams.put("user_id", user);
                    jsonparams.put("enq_id", enquiry_id);
                    Log.e("testride_request:", jsonparams.toString());
                    new testride_feedback(jsonparams.toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
                }
            } else
                Toast.makeText(getContext(), "You are offline now !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_followupdetail, f, "followupetail");
        ft.commit();
    }

    public void fetch_data() {
        Bundle bundle = this.getArguments();
        first_name = bundle.getString("fname");
        last_name = bundle.getString("lname");
        cell_ph_no = bundle.getString("mobile");
        age = bundle.getString("age");
        gender = bundle.getString("sex");
        email_addr = bundle.getString("email");
        state = bundle.getString("state");
        district = bundle.getString("district");
        tehsil = bundle.getString("tehsil");
        city = bundle.getString("city");
        x_con_seq_no = bundle.getString("id");
        x_model_interested = bundle.getString("model");
        expected_date_purchase = bundle.getString("pur_date");
        x_exchange_required = bundle.getString("exchange");
        x_finance_required = bundle.getString("finance");
        existvehicle = bundle.getString("vtype");
        followup_comments = bundle.getString("comment");
        enquiry_id = bundle.getString("enquiryid");
        follow_date = bundle.getString("followdate");
        user = bundle.getString("user");
        encryptuser = bundle.getString("user_id");
        enquiryid = bundle.getString("enquiryid");
        dealerid = bundle.getString("dealerid");
        two_wheeler_type = bundle.getString("twowheelertype");
        area = bundle.getString("area");
        occupation = bundle.getString("occupation");
        usage = bundle.getString("usage");
        sales_pitch_no = bundle.getString("sales_pitch_no");

        if (bundle.containsKey("page_flag"))
            page_flag = bundle.getInt("page_flag");

        if (email_addr.equalsIgnoreCase("null"))
            email_addr = "";
        if (follow_date.equalsIgnoreCase("null"))
            follow_date = "";
        if (followup_comments.equalsIgnoreCase("null"))
            followup_comments = "";
        if (expected_date_purchase.equalsIgnoreCase("null"))
            expected_date_purchase = "";

        edit.clear().commit();
        edit.putString("firstname", first_name);
        edit.putString("lastname", last_name);
        edit.putString("mobile", cell_ph_no);
        edit.putString("age", age);
        edit.putString("email", email_addr);
        edit.putString("gender", gender);
        edit.putString("state", state);
        edit.putString("district", district);
        edit.putString("tehsil", tehsil);
        edit.putString("city", city);
        edit.putString("address1", "");
        edit.putString("address2", "");
        edit.putString("pincode", "");
        edit.putString("con_seq_no", x_con_seq_no);
        edit.putString("model", x_model_interested);
        edit.putString("purch_date", expected_date_purchase);
        edit.putString("exchange", x_exchange_required);
        edit.putString("finance", x_finance_required);
        edit.putString("existvehicle", existvehicle);
        edit.putString("comment", followup_comments);
        edit.putString("enquiry_id", enquiry_id);
        edit.putString("follow_date", follow_date);
        edit.putString("dealerid", dealerid);
        edit.putString("purchase", two_wheeler_type);
        edit.putString("occupation", occupation);
        edit.putString("area", area);
        edit.putString("usage", usage);
        edit.commit();
    }

    public class testride_feedback extends AsyncTask<Void, Void, String> {
        String newurlParameters;
        NetworkConnect networkConnect;
        String result;
        private ProgressDialog progressDialog;

        public testride_feedback(String urlParameters) {
            this.newurlParameters = urlParameters;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), null, null);
            progressDialog.setContentView(R.layout.progresslayout);
        }

        protected String doInBackground(Void... params) {
            if (NetConnections.isConnected(getContext())) {
                try {
                    String newurlparams = "data=" + URLEncoder.encode(newurlParameters, "UTF-8");
                    networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
                    String data = networkConnect.execute();
                    String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
                    networkConnect = new NetworkConnect(URLConstants.FETCH_TEST_RIDE, urldata);
                    // jsonparse(networkConnect.execute());
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
                progressDialog.dismiss();
                Bundle bundle = new Bundle();
                Log.e("testride_response:", result);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("success").equals("0")) {
                    String resDesp = jsonObject.getString("respDescription");
                    bundle.putString("msg", resDesp);
                    bundle.putInt("flag", 0);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AlertDialogFragment dialogFragment = new AlertDialogFragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(fm, "Sample Fragment");
                } else {
                    ArrayList<String> quesid = new ArrayList<String>();
                    JSONArray jArray = jsonObject.getJSONArray("test_ride_ques");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject object = jArray.getJSONObject(i);
                        quesid.add(object.getString("question"));
                        Log.e("id", object.getString("id"));
                        Log.e("ques", object.getString("question"));
                    }
                    bundle.putStringArrayList("quesid", quesid);
                    bundle.putInt("page_flag", 1);
                    f = new TestRideFeedbackFragment();
                    f.setArguments(bundle);
                    transaction(f);
                }

            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
            }
        }
    }
}

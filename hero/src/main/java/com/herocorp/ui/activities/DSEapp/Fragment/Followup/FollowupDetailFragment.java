package com.herocorp.ui.activities.DSEapp.Fragment.Followup;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.TestRideFeedbackFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.CloseFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.FollowupFragment;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

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
    SharedPreferences mypref;
    SharedPreferences.Editor edit;

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
        agesex.setText(age + " / " + gender);
        email.setText(email_addr);
        cust_id.setText(" " + x_con_seq_no);
        model.setText(x_model_interested);
        expt_date.setText(expected_date_purchase);
        exchange_reqd.setText(x_exchange_required);
        finance_reqd.setText(x_finance_required);
        exist_vehicle.setText(existvehicle);
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
        Fragment f;
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
            button_close.setBackgroundColor(Color.RED);
            button_followup.setBackgroundColor(Color.RED);
            button_edit.setBackgroundColor(Color.DKGRAY);
            f = new EditFollowupFragment();
            f.setArguments(bundle);
            transaction(f);

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
            f = new TestRideFeedbackFragment();
            f.setArguments(bundle);
            transaction(f);
        }
    }

    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_followupdetail, f);
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

        edit.putString("firstname", first_name);
        edit.putString("lastname", last_name);
        edit.putString("mobile", cell_ph_no);
        edit.putString("age", age);
        edit.putString("email", email_addr);
        edit.putString("gender", gender);
        edit.putString("state", state);
        edit.putString("district",  district);
        edit.putString("tehsil",tehsil);
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
        edit.commit();

    }
}

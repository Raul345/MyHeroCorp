package com.herocorp.ui.activities.DSEapp.Fragment.Contact;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.CloseFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.FollowupFragment;
import com.herocorp.ui.activities.DSEapp.adapter.VehicleDetailadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.activities.DSEapp.models.VehicleDetail;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class ContactDetailFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;

    String fst_name1, last_name1, cell_ph_num1, age1, gender1, email_addr1, state1, district1, tehsil1, city1, x_con_seq_no1, x_model_interested = "",
            expctd_dt_purchase = "", x_exchange_required, x_finance_required, existing_vehicle, followup_comments, enquiry_id = "",
            x_test_ride_req, enquiry_entry_date = "", addr, addr_line_2, make_cd, model_cd1, dealer_bu_id, follow_date = "";

    String fst_name2, last_name2, cell_ph_num2, age2, gender2, email_addr2, state2, district2, tehsil2, city2, x_con_seq_no2, x_hmgl_card_num, last_srvc_dt, next_srvc_due_dt, ins_policy_num, x_ins_exp_dt, model_cd2, serial_num,
            primary_dealer_name, attrib_42, prod_attrib02_CD, desc_text, first_sale_dt, ins_policy_co, x_hmgl_card_points;

    TextView name, ages, mobile, email, states, tehsils, districts, citys, enquirydate, interestedmodel, expecteddate, followdate;
    Button customer_id, closeenquiry, followupenquiry;
    ImageView followup_check;
    String pendingfollowup_check = "0";
    String enq_id = "";

    String firstname = "", lastname = "", age = "", state = "", district = "",
            tehsil = "", city = "", contact = "", sex = "", email_addr = "", cust_id = "";
    String encryptuser, user;

    ArrayList vinarray = new ArrayList(), variantarray = new ArrayList(), dealerarray = new ArrayList(), colourarray = new ArrayList(), modelarray = new ArrayList(), datearray = new ArrayList(), descarray = new ArrayList();

    ArrayList cardnoarray = new ArrayList(), currentpointsarray = new ArrayList(), lastservicearray = new ArrayList(), nextservicearray, expirydatearray = new ArrayList(),
            policynoarray = new ArrayList(), insurancecoarray = new ArrayList();

    ListView vehiclelist;
    VehicleDetailadapter vehicleDetailadapter;
    ArrayList<VehicleDetail> vehiclearray = new ArrayList<VehicleDetail>();
    Fragment f;

    LinearLayout addenquiry, viewenquiry;

    SharedPreferences mypref;
    SharedPreferences.Editor edit;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_contactdetail_fragment, container, false);

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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{90, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        vehicleDetailadapter = new VehicleDetailadapter(getContext(), R.layout.dse_vehicledetail_row, vehiclearray);
        vehiclelist = (ListView) rootView.findViewById(R.id.list_existingvehicle);

        addenquiry = (LinearLayout) rootView.findViewById(R.id.addenquiry_layout);
        viewenquiry = (LinearLayout) rootView.findViewById(R.id.viewenquiry_layout);


        name = (TextView) rootView.findViewById(R.id.cust_name_textview);
        ages = (TextView) rootView.findViewById(R.id.agesex_textview);
        states = (TextView) rootView.findViewById(R.id.state_textview);
        districts = (TextView) rootView.findViewById(R.id.district_textview);
        tehsils = (TextView) rootView.findViewById(R.id.tehsil_textview);
        citys = (TextView) rootView.findViewById(R.id.city_textview);
        mobile = (TextView) rootView.findViewById(R.id.mobile_textview);
        email = (TextView) rootView.findViewById(R.id.email_textview);

        customer_id = (Button) rootView.findViewById(R.id.cust_id);
        closeenquiry = (Button) rootView.findViewById(R.id.button_close);
        followupenquiry = (Button) rootView.findViewById(R.id.button_followup);


        interestedmodel = (TextView) rootView.findViewById(R.id.textview_x_model_interested);
        enquirydate = (TextView) rootView.findViewById(R.id.textview_enquiry_date);
        expecteddate = (TextView) rootView.findViewById(R.id.textview_expctd_dt_purchase);
        followdate = (TextView) rootView.findViewById(R.id.textview_follow_date);

        followup_check = (ImageView) rootView.findViewById(R.id.img_pendingfollowup_check);

        fetch_data();
        fetch_data1();
        fetch_data2();
        setList();
        set_data();

        vehiclelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                   VehicleDetail data = vehicleDetailadapter.getItem(position);
                                                   Bundle bundle = new Bundle();
                                                   bundle.putString("name", name.getText().toString());
                                                   bundle.putString("cell_ph_num", mobile.getText().toString());
                                                   bundle.putString("age", ages.getText().toString());
                                                   bundle.putString("email_addr", email.getText().toString());
                                                   bundle.putString("state", states.getText().toString());
                                                   bundle.putString("district", districts.getText().toString());
                                                   bundle.putString("tehsil", tehsils.getText().toString());
                                                   bundle.putString("city", citys.getText().toString());
                                                   bundle.putString("customerid", customer_id.getText().toString());
                                                   bundle.putString("cardno", data.getX_hmgl_card_num());
                                                   bundle.putString("currentpoints", data.getX_hmgl_card_num());
                                                   bundle.putString("lastservicdate", data.getLast_srvc_dt());
                                                   bundle.putString("nextservicedate", data.getNext_srvc_due_dt());
                                                   bundle.putString("expirydate", data.getX_ins_exp_dt());
                                                   bundle.putString("policyno", data.getIns_policy_num());
                                                   bundle.putString("insuranceco", data.getIns_policy_co());

                                                   f = new VehicleDetailFragment();
                                                   f.setArguments(bundle);
                                                   transaction(f);
                                               }
                                           }

        );

        addenquiry.setOnClickListener(this);
        closeenquiry.setOnClickListener(this);
        followupenquiry.setOnClickListener(this);

        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("user_id", encryptuser);
        bundle.putString("user", user);
        bundle.putString("enquiry_id", enquiry_id);
        bundle.putInt("enq_flag", 1);
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        } else if (i == R.id.addenquiry_layout) {
            save_preference();
            f = new EditFollowupFragment();
           /* FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
                   ft.replace(R.id.content_contactdetail, f);
            ft.commit();*/
            transaction(f);
        } else if (i == R.id.button_close) {
            f = new CloseFollowupFragment();
            f.setArguments(bundle);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //  ft.addToBackStack(null);
            ft.replace(R.id.content_contactdetail, f);
            ft.commit();
           //transaction(f);
        } else if (i == R.id.button_followup) {
            f = new FollowupFragment();
            f.setArguments(bundle);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //  ft.addToBackStack(null);
            ft.replace(R.id.content_contactdetail, f);
            ft.commit();
            //   transaction(f);
        }

    }

    public void set_data() {

        if (!(fst_name1 == null))
            firstname = fst_name1;
        if (!(fst_name2 == null))
            firstname = fst_name2;
        if (!(last_name1 == null))
            lastname = last_name1;
        if (!(last_name2 == null))
            lastname = last_name2;
        if (!(age1 == null))
            age = age1;
        if (!(age2 == null))
            age = age2;
        if (!(gender1 == null))
            sex = gender1;
        if (!(gender2 == null))
            sex = gender2;
        if (!(state1 == null))
            state = state1;
        if (!(state2 == null))
            state = state2;
        if (!(district1 == null))
            district = district1;
        if (!(district2 == null))
            district = district2;
        if (!(tehsil1 == null))
            tehsil = tehsil1;
        if (!(tehsil2 == null))
            tehsil = tehsil2;
        if (!(city1 == null))
            city = city1;
        if (!(city2 == null))
            city = city2;
        if (!(cell_ph_num1 == null))
            contact = cell_ph_num1;
        if (!(cell_ph_num2 == null))
            contact = cell_ph_num2;
        if (!(email_addr1 == null))
            email_addr = email_addr1;
        if (!(email_addr2 == null))
            email_addr = email_addr2;
        if (!(x_con_seq_no1 == null))
            cust_id = x_con_seq_no1;
        if (!(x_con_seq_no2 == null))
            cust_id = x_con_seq_no2;
        name.setText(firstname + " " + lastname);
        ages.setText(age + " / " + sex);
        states.setText(state);
        districts.setText(district);
        tehsils.setText(tehsil);
        citys.setText(city);
        mobile.setText(contact);
        email.setText(email_addr);
        customer_id.setText("  Customer id: " + cust_id);

        if (x_model_interested.equals("")) {
            addenquiry.setVisibility(View.VISIBLE);
            viewenquiry.setVisibility(View.INVISIBLE);
        } else {
            viewenquiry.setVisibility(View.VISIBLE);
            addenquiry.setVisibility(View.INVISIBLE);
            interestedmodel.setText(x_model_interested);
            enquirydate.setText(enquiry_entry_date);
            expecteddate.setText(expctd_dt_purchase);
            followdate.setText(follow_date);

            if (pendingfollowup_check.equalsIgnoreCase("0"))
                followup_check.setImageResource(R.drawable.error_icon);
            else
                followup_check.setImageResource(R.drawable.tick_image);
        }
    }

    public void fetch_data() {
        Bundle bundle = this.getArguments();
        fst_name1 = bundle.getString("fst_name1");
        last_name1 = bundle.getString("last_name1");
        cell_ph_num1 = bundle.getString("cell_ph_num1");
        age1 = bundle.getString("age1");
        gender1 = bundle.getString("gender1");
        email_addr1 = bundle.getString("email_addr1");
        state1 = bundle.getString("state1");
        district1 = bundle.getString("district1");
        tehsil1 = bundle.getString("tehsil1");
        city1 = bundle.getString("city1");
        x_con_seq_no1 = bundle.getString("x_con_seq_no_1");
        // x_model_interested = bundle.getString("x_model_interested");
        // expctd_dt_purchase = bundle.getString("expctd_dt_purchase");
        // follow_date = bundle.getString("follow_date");
        //enquiry_entry_date = bundle.getString("enquiry_entry_date");
        x_exchange_required = bundle.getString("x_exchange_required");
        x_finance_required = bundle.getString("x_finance_required");
        existing_vehicle = bundle.getString("existing_vehicle");
        followup_comments = bundle.getString("followup_comments");
        enquiry_id = bundle.getString("enquiry_id");
        x_test_ride_req = bundle.getString("x_test_ride_req");
        addr = bundle.getString("addr1");
        addr_line_2 = bundle.getString("addr_line_2_1");
        make_cd = bundle.getString("make_cd");
        model_cd1 = bundle.getString("model_cd1");
        dealer_bu_id = bundle.getString("dealer_bu_id");

        user = bundle.getString("user");
        encryptuser = bundle.getString("user_id");
    }

    public void fetch_data1() {
        Bundle bundle = this.getArguments();
        fst_name2 = bundle.getString("fst_name2");
        last_name2 = bundle.getString("last_name2");
        cell_ph_num2 = bundle.getString("cell_ph_num2");
        age2 = bundle.getString("age2");
        gender2 = bundle.getString("gender2");
        email_addr2 = bundle.getString("email_addr2");
        state2 = bundle.getString("state2");
        district2 = bundle.getString("district2");
        tehsil2 = bundle.getString("tehsil2");
        city2 = bundle.getString("city2");
        x_con_seq_no2 = bundle.getString("x_con_seq_no_2");
        x_hmgl_card_num = bundle.getString("x_hmgl_card_num");
        last_srvc_dt = bundle.getString("last_srvc_dt");
        next_srvc_due_dt = bundle.getString("next_srvc_due_dt");
        ins_policy_num = bundle.getString("ins_policy_num");
        x_ins_exp_dt = bundle.getString("x_ins_exp_dt");
        model_cd2 = bundle.getString(" model_cd2");
        serial_num = bundle.getString("serial_num");
        primary_dealer_name = bundle.getString("primary_dealer_name");
        attrib_42 = bundle.getString("attrib_42");
        addr = bundle.getString("addr2");
        addr_line_2 = bundle.getString("addr_line_2_2");
        prod_attrib02_CD = bundle.getString("prod_attrib02_CD");
        desc_text = bundle.getString("desc_text");
        first_sale_dt = bundle.getString("first_sale_dt");
        ins_policy_co = bundle.getString("ins_policy_co");
        x_hmgl_card_points = bundle.getString("x_hmgl_card_points");
      /*  follow_date = bundle.getString("follow_date");
        enquiry_entry_date = bundle.getString("enquiry_entry_date");
        model_cd1 = bundle.getString("x_model_interested");
        expctd_dt_purchase = bundle.getString("expctd_dt_purchase");*/
        enquiry_id = bundle.getString("enquiry_id");

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Followup> allrecords = db.getContactFollowup(enquiry_id);
        for (Followup record : allrecords) {
            x_model_interested = record.getX_model_interested();
            expctd_dt_purchase = record.getExpcted_date_purchase();
            enquiry_id = record.getEnquiry_id();
            follow_date = record.getFollow_date();
            enquiry_entry_date = record.getEnquiry_entry_date();
            pendingfollowup_check = record.getFollowup_status();
            Log.e("get:", enquiry_id + follow_date);
        }

        user = bundle.getString("user");
        encryptuser = bundle.getString("user_id");
    }

    public void fetch_data2() {
        Bundle bundle = this.getArguments();
        modelarray = bundle.getStringArrayList("modelarray");
        dealerarray = bundle.getStringArrayList("dealerarray");
        vinarray = bundle.getStringArrayList("vinarray");
        variantarray = bundle.getStringArrayList("variantarray");
        colourarray = bundle.getStringArrayList("colourarray");
        descarray = bundle.getStringArrayList("descarray");
        datearray = bundle.getStringArrayList("datearray");

        cardnoarray = bundle.getStringArrayList("cardnoarray");
        currentpointsarray = bundle.getStringArrayList("currentpointsarray");
        lastservicearray = bundle.getStringArrayList("lastservicearray");
        nextservicearray = bundle.getStringArrayList("nextservicearray");
        expirydatearray = bundle.getStringArrayList("expirydatearray");
        policynoarray = bundle.getStringArrayList("policynoarray");
        insurancecoarray = bundle.getStringArrayList("insurancecoarray");

    }

    public void setList() {
        vehicleDetailadapter.clear();
        for (int i = 0; i < vinarray.size(); i++) {

            vehicleDetailadapter.add(new VehicleDetail(modelarray.get(i).toString(), dealerarray.get(i).toString(),
                    variantarray.get(i).toString(), colourarray.get(i).toString(),
                    datearray.get(i).toString(), vinarray.get(i).toString(),
                    descarray.get(i).toString(), cardnoarray.get(i).toString(),
                    currentpointsarray.get(i).toString(), lastservicearray.get(i).toString(),
                    nextservicearray.get(i).toString(), expirydatearray.get(i).toString(),
                    policynoarray.get(i).toString(),
                    insurancecoarray.get(i).toString()));

            vehicleDetailadapter.notifyDataSetChanged();
        }
        updateList();
    }

    private void updateList() {
        vehiclelist.setAdapter(vehicleDetailadapter);
        setListViewHeightBasedOnChildren(vehiclelist);

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_contactdetail, f);
        ft.commit();
    }

    public void save_preference() {
        edit.putString("firstname", firstname);
        edit.putString("lastname", lastname);
        edit.putString("mobile", contact);
        edit.putString("age", age);
        edit.putString("email", email_addr);
        edit.putString("gender", sex);
        edit.putString("state", state);
        edit.putString("district", district);
        edit.putString("tehsil", tehsil);
        edit.putString("city", city);
        edit.putString("address1", "");
        edit.putString("address2", "");
        edit.putString("pincode", "");
        edit.putString("con_seq_no", enquiry_id);
        edit.putString("key", random_key(7));
        edit.commit();
    }

    public String random_key(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}




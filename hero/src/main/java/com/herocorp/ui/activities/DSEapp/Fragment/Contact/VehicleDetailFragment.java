package com.herocorp.ui.activities.DSEapp.Fragment.Contact;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.ContactAlertFragment;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 21-Oct-16.
 */
public class VehicleDetailFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;

    String fst_name, cell_ph_num, age1, email_addr, state, district, tehsil, city, customer_id;

    String x_hmgl_card_num, last_srvc_dt, next_srvc_due_dt, ins_policy_num, x_ins_exp_dt, ins_policy_co, x_hmgl_card_points;

    TextView name, age, mobile, email, states, tehsils, districts, citys, cardno, currentpoint, lastservicedate, nextservicedate, expirydate,
            policyno, insurancecompany;
    Button cust_id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_vehicledetail_fragment, container, false);

        initView(rootView);

        return rootView;
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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{90, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        name = (TextView) rootView.findViewById(R.id.cust_name_textview);
        age = (TextView) rootView.findViewById(R.id.agesex_textview);
        states = (TextView) rootView.findViewById(R.id.state_textview);
        districts = (TextView) rootView.findViewById(R.id.district_textview);
        tehsils = (TextView) rootView.findViewById(R.id.tehsil_textview);
        citys = (TextView) rootView.findViewById(R.id.city_textview);
        mobile = (TextView) rootView.findViewById(R.id.mobile_textview);
        email = (TextView) rootView.findViewById(R.id.email_textview);
        cardno = (TextView) rootView.findViewById(R.id.textview_cardno);
        currentpoint = (TextView) rootView.findViewById(R.id.textview_currentpoints);
        lastservicedate = (TextView) rootView.findViewById(R.id.textview_lastservicedate);
        nextservicedate = (TextView) rootView.findViewById(R.id.textview_nextservicedate);
        expirydate = (TextView) rootView.findViewById(R.id.textview_expirydate);
        policyno = (TextView) rootView.findViewById(R.id.textview_policyno);
        insurancecompany = (TextView) rootView.findViewById(R.id.textview_insuranceco);
        cust_id = (Button) rootView.findViewById(R.id.cust_id);


        fetch_data();
        set_data();
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        }
    }

    public void set_data() {
        name.setText(fst_name);
        age.setText(age1);
        states.setText(state);
        districts.setText(district);
        tehsils.setText(tehsil);
        citys.setText(city);
        mobile.setText(cell_ph_num);

        mobile.setPaintFlags(mobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mobile", cell_ph_num);
                bundle.putString("header", "Are you Sure?");
                bundle.putString("msg", "Call this number " + cell_ph_num);
                bundle.putInt("flag", 0);
                FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                ContactAlertFragment dialogFragment = new ContactAlertFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(false);
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        email.setText(email_addr);
        cust_id.setText(customer_id);
        cardno.setText(x_hmgl_card_num);
        currentpoint.setText(x_hmgl_card_points);
        lastservicedate.setText(last_srvc_dt);
        nextservicedate.setText(next_srvc_due_dt);
        expirydate.setText(x_ins_exp_dt);
        policyno.setText(ins_policy_num);
        insurancecompany.setText(ins_policy_co);
    }

    public void fetch_data() {
        Bundle bundle = this.getArguments();

        fst_name = bundle.getString("name");
        cell_ph_num = bundle.getString("cell_ph_num");
        age1 = bundle.getString("age");
        email_addr = bundle.getString("email_addr");
        state = bundle.getString("state");
        district = bundle.getString("district");
        tehsil = bundle.getString("tehsil");
        city = bundle.getString("city");
        customer_id = bundle.getString("customerid");
        x_hmgl_card_num = bundle.getString("cardno");
        x_hmgl_card_points = bundle.getString("currentpoints");
        last_srvc_dt = bundle.getString("lastservicdate");
        next_srvc_due_dt = bundle.getString("nextservicedate");
        x_ins_exp_dt = bundle.getString("expirydate");
        ins_policy_num = bundle.getString("policyno");
        ins_policy_co = bundle.getString("insuranceco");
    }


}

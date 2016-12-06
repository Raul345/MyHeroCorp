package com.herocorp.ui.activities.DSEapp.Fragment.Alert;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.TestRideFeedbackFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Home.HomeFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingFollowup.PendingFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingOrders.PendingOrdersFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.TodayFollowup.TodayFollowupFragment;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.LocalEnquiry;

/**
 * Created by rsawh on 28-Sep-16.
 */
public class AlertDialogFragment extends DialogFragment {
    Button button_ok;
    TextView textview_alert, textview_header;
    String contact_no, reg_no;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dse_alert_fragment, container, false);
        button_ok = (Button) view.findViewById(R.id.button_ok);
        textview_alert = (TextView) view.findViewById(R.id.textview_alert);
        textview_header = (TextView) view.findViewById(R.id.textview_header);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = this.getArguments();
        String msg = bundle.getString("msg");
        int flag = bundle.getInt("flag");
        textview_alert.setText(msg);

        if (flag == 1) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    getActivity().onBackPressed();
                }
            });
        } else if (flag == 2) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetch_pref();
                    /*DatabaseHelper db = new DatabaseHelper(getContext());
                    db.add_enquiry(new LocalEnquiry(contact_no, reg_no));*/
                    clear_pref();

                    String message = "Do you want to give TestRide Feedback?";
                    Bundle bundle = new Bundle();
                    bundle.putString("header", "");
                    bundle.putString("msg", message);
                    bundle.putInt("flag", 2);
                    FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ContactAlertFragment dialogFragment = new ContactAlertFragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(fm, "Sample Fragment");
                    dismiss();


                }
            });
        } else if (flag == 3) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment f = new HomeFragment();
                    ft.addToBackStack(null);
                    ft.replace(R.id.content_testridefeedback, f);
                    ft.commit();*/
                    dismiss();
                    getActivity().onBackPressed();

                }
            });
        } else if (flag == 4) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (flag == 5) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetch_pref();

                    /*DatabaseHelper db = new DatabaseHelper(getContext());
                    db.add_enquiry(new LocalEnquiry(contact_no, reg_no));*/
                    clear_pref();

                    String message = "Do you want to give TestRide Feedback?";
                    Bundle bundle = new Bundle();
                    bundle.putString("header", "");
                    bundle.putString("msg", message);
                    bundle.putInt("flag", 4);
                    FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ContactAlertFragment dialogFragment = new ContactAlertFragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(fm, "Sample Fragment");
                    dismiss();


                }
            });
        } else {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textview_header.setVisibility(View.GONE);
                    // getActivity().onBackPressed();
                    dismiss();
                }
            });
//            getActivity().onBackPressed();
        }

        return view;
    }

    public void fetch_pref() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        if (sharedPreferences.contains("contact_no"))
            contact_no = sharedPreferences.getString("contact_no", null);
        if (sharedPreferences.contains("reg_no"))
            reg_no = sharedPreferences.getString("reg_no", null);

    }

    public void clear_pref() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (sharedPreferences.contains("contact_no"))
            edit.remove("contact_no").commit();
        if (sharedPreferences.contains("reg_no"))
            edit.remove("reg_no").commit();
    }
}

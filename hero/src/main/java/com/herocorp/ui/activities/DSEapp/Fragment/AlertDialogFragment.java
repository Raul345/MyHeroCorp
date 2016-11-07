package com.herocorp.ui.activities.DSEapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

/**
 * Created by rsawh on 28-Sep-16.
 */
public class AlertDialogFragment extends DialogFragment {
    Button button_ok;
    TextView textview_alert;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dse_alert_fragment, container, false);
        button_ok = (Button) view.findViewById(R.id.button_ok);
        textview_alert = (TextView) view.findViewById(R.id.textview_alert);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = this.getArguments();
        String msg = bundle.getString("msg");
        int flag = bundle.getInt("flag");
        textview_alert.setText(msg);

        if (flag == 1) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                    dismiss();
                }
            });
        } else if (flag == 2) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment f = new TestRideFeedbackFragment();
                   // ft.addToBackStack(null);
                    ft.replace(R.id.content_addenquiry, f);
                    ft.commit();
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
        } else {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // getActivity().onBackPressed();
                    dismiss();

                }
            });
//            getActivity().onBackPressed();
        }

        return view;
    }
}

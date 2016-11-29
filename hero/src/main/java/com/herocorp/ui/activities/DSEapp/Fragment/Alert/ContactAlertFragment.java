package com.herocorp.ui.activities.DSEapp.Fragment.Alert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.herocorp.ui.activities.auth.SignInActivity;

/**
 * Created by rsawh on 07-Oct-16.
 */
public class ContactAlertFragment extends DialogFragment {
    Button button_ok, button_cancel;
    TextView textview_header, textview_message;
    String header = "";
    String msg = "";
    String phone = "";
    String path = "";
    String button_name1 = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_alert_fragment, container, false);

        button_ok = (Button) view.findViewById(R.id.button_ok);
        button_cancel = (Button) view.findViewById(R.id.button_cancel);
        textview_header = (TextView) view.findViewById(R.id.header_textview);
        textview_message = (TextView) view.findViewById(R.id.message_textview);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = this.getArguments();
        header = bundle.getString("header");
        msg = bundle.getString("msg");
        phone = bundle.getString("mobile");
        path = bundle.getString("path");
        button_name1 = bundle.getString("name1");
        final int flag = bundle.getInt("flag");
        textview_header.setText(header);
        textview_message.setText(msg);

        if (flag == 0) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    startActivity(intent);
                    dismiss();
                }
            });
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else if (flag == 1) {
            button_ok.setText(button_name1);
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resultIntent = new Intent(Intent.ACTION_VIEW);
                    resultIntent.setData(Uri.parse(path));
                    startActivity(resultIntent);
                    dismiss();
                }
            });
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    getActivity().onBackPressed();
                }
            });
        } else if (flag == 3) {
            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("hero", 0);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.clear().commit();
                    startActivity(new Intent(getActivity(), SignInActivity.class));
                    getActivity().finish();

                }
            });
            button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();

                }
            });

        }
        return view;
    }
}

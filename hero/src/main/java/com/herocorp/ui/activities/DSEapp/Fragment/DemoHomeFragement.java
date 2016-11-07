package com.herocorp.ui.activities.DSEapp.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.PersonalinfoFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.TestRideFeedbackFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingFollowup.PendingFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.PendingOrders.PendingOrdersFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.TodayFollowup.TodayFollowupFragment;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 03-Oct-16.
 */
public class DemoHomeFragement extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    EditText phoneno_et, registration_et;


    TextView pendingorderText, todayfollowupText, pendingfollowupText, searchenquiryText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.demo_home_fragment, container, false);
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
        registration_et = (EditText) rootView.findViewById(R.id.registration_edittext);

        // encryptuser();

        menu.setOnClickListener(this);
        pendingorder.setOnClickListener(this);
        todayfollowup.setOnClickListener(this);
        pendingfollowup.setOnClickListener(this);
        searchenquiry.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.pendingorders) {

           /* Toast.makeText(getActivity(), "pending orders", Toast.LENGTH_SHORT).show();*/
            pendingorderText.setTextColor(Color.WHITE);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);
            // progress();
            if (NetConnections.isConnected(getContext())) {
                // Toast.makeText(getActivity(), "Please wait for a while !!", Toast.LENGTH_SHORT).show();
                transaction(new PendingOrdersFragment());

            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();


        } else if (i == R.id.followup) {

            // Toast.makeText(getActivity(), "today followup", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.WHITE);
            searchenquiryText.setTextColor(Color.GRAY);
            if (NetConnections.isConnected(getContext())) {
                transaction(new TodayFollowupFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();


        } else if (i == R.id.pendingfollowup) {
            //  Toast.makeText(getActivity(), "penidng followup", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.WHITE);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.GRAY);
            if (NetConnections.isConnected(getContext())) {
                transaction(new PendingFollowupFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.searchenquiry) {
            // Toast.makeText(getActivity(), "search enquiry", Toast.LENGTH_SHORT).show();
            pendingorderText.setTextColor(Color.GRAY);
            pendingfollowupText.setTextColor(Color.GRAY);
            todayfollowupText.setTextColor(Color.GRAY);
            searchenquiryText.setTextColor(Color.WHITE);
            if (NetConnections.isConnected(getContext())) {
                transaction(new TestRideFeedbackFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
            // Toast.makeText(getActivity(), "work in progress", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.imageView_submit_home) {
            if (NetConnections.isConnected(getContext())) {
                transaction(new PersonalinfoFragment());
            } else
                Toast.makeText(getActivity(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void transaction(final Fragment f) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("home");
        ft.replace(R.id.content_dsehome, f);
        ft.commit();

    }
}

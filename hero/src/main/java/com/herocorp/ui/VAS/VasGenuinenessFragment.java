package com.herocorp.ui.VAS;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 07-Oct-16.
 */
public class VasGenuinenessFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.vas_genuineness_fragment, container, false);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView close = (ImageView) rootView.findViewById(R.id.close_icon);

        customViewParams.setImageViewCustomParams(close, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{20, 20, 20, 20}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        RelativeLayout topLayout1 = (RelativeLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{10, 0, 0, 10}, new int[]{0, 0, 0, 0}, topLayout1.getParent());
        LinearLayout topLayout2 = (LinearLayout) rootView.findViewById(R.id.top_layout2);
        customViewParams.setMarginAndPadding(topLayout2, new int[]{10, 0, 0, 10}, new int[]{0, 0, 0, 0}, topLayout2.getParent());


        TextView headername = (TextView) rootView.findViewById(R.id.textview_name);
        TextView subheadername = (TextView) rootView.findViewById(R.id.textview_subname);
        customViewParams.setMarginAndPadding(headername, new int[]{60, 0, 0, 0}, new int[]{0, 0, 0, 0}, headername.getParent());
        customViewParams.setMarginAndPadding(subheadername, new int[]{60, 0, 0, 0}, new int[]{0, 0, 0, 0}, subheadername.getParent());

        Button back = (Button) rootView.findViewById(R.id.button_back);
        customViewParams.setButtonCustomParams(back, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, 180, 40, customTypeFace.gillSansItalic, 0);

     /*   LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
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
        submit.setOnClickListener(this);*//*
*/
    }

    @Override
    public void onClick(View v) {

    }
}

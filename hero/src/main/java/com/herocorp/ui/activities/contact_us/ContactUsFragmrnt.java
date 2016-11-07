package com.herocorp.ui.activities.contact_us;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.Params;

public class ContactUsFragmrnt extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.contact_us_fragment, container, false);

        ((BaseDrawerActivity)getActivity()).closeDrawer();
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        CustomViewParams customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{80, 30, 80, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 400, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout detailLayout = (LinearLayout) rootView.findViewById(R.id.detail_layout);
        customViewParams.setMarginAndPadding(detailLayout, new int[]{100, 50, 100, 50}, new int[]{40, 0, 0, 40}, detailLayout.getParent());

        TextView webUrl = (TextView) rootView.findViewById(R.id.web_url_text);
        TextView fullAddress = (TextView) rootView.findViewById(R.id.full_address);
        TextView tollFree = (TextView) rootView.findViewById(R.id.toll_free);
        TextView tollFree2 = (TextView) rootView.findViewById(R.id.toll_free2);

        customViewParams.setTextViewCustomParams(webUrl, new int[]{20, 0, 0, 25}, new int[]{0, 0, 0, 0}, 46, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(fullAddress, new int[]{20, 25, 100, 25}, new int[]{0, 0, 0, 0}, 45, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(tollFree, new int[]{20, 25, 0, 0}, new int[]{0, 0, 0, 0}, 45, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(tollFree2, new int[]{10, 25, 0, 0}, new int[]{0, 0, 0, 0}, 45, customTypeFace.gillSans, 0);

        webUrl.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.contactus_url), 60, 60), null, null, null);
        fullAddress.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.contactus_address), 60, 60),null, null, null);
        tollFree.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.contactus_tollfree), 60, 60),null, null, null);

        webUrl.setCompoundDrawablePadding(new Params(getResources().getDisplayMetrics()).getSpacing(20));
        fullAddress.setCompoundDrawablePadding(new Params(getResources().getDisplayMetrics()).getSpacing(20));
        tollFree.setCompoundDrawablePadding(new Params(getResources().getDisplayMetrics()).getSpacing(20));

        TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0,10,0,5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0,2,0,30}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30),null, null, null);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseDrawerActivity)getActivity()).toggleDrawer();
            }
        });
    }
}

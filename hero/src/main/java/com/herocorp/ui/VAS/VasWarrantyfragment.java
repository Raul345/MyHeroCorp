package com.herocorp.ui.VAS;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 23-Sep-16.
 */
public class VasWarrantyfragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.vas_warranty_fragment, container, false);

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
        customViewParams.setMarginAndPadding(topLayout, new int[]{200, 20, 200, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{0, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{80, 20, 80, 140}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        //setting header text
        String simple = "VALUE ADDED SERVICES ";
        String colored = "(VAS)";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        buttonHeader.setText(builder);

        TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 30}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);


        LinearLayout warranty_layout = (LinearLayout) rootView.findViewById(R.id.warranty_layout);
        LinearLayout maintenance_layout = (LinearLayout) rootView.findViewById(R.id.maintenance_layout);
        LinearLayout safety_layout = (LinearLayout) rootView.findViewById(R.id.safety_layout);
        LinearLayout genuine_layout = (LinearLayout) rootView.findViewById(R.id.genuineparts_layout);


        warranty_layout.setOnClickListener(this);
        maintenance_layout.setOnClickListener(this);
        safety_layout.setOnClickListener(this);
        genuine_layout.setOnClickListener(this);
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Fragment f;
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        } else if (i == R.id.maintenance_layout) {
            f = new VasMaintenanceFragment();
            transaction(f);
        } else if (i == R.id.safety_layout) {
            f = new VasSafetytipsFragment();
            transaction(f);
        } else if (i == R.id.genuineparts_layout) {
            f = new VasGenuinePartsFragment();
            transaction(f);
        }


    }

    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_vaswarranty, f);
        ft.commit();
    }
}




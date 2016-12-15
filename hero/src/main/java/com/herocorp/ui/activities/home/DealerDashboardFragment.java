package com.herocorp.ui.activities.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.AppConstants;
import com.herocorp.ui.VAS.VasWarrantyfragment;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Home.HomeFragment;
import com.herocorp.ui.activities.products.ProductDetailFragment;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

public class DealerDashboardFragment extends Fragment implements View.OnClickListener {

    private String TAG = "HeroCorp";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dealer_dashboard_fragment, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        CustomViewParams customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView logoImageView = (ImageView) rootView.findViewById(R.id.logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(logoImageView, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 260, 210);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout containerLayout = (LinearLayout) rootView.findViewById(R.id.buttons_container);
        customViewParams.setMarginAndPadding(containerLayout, new int[]{10, 10, 10, 10}, new int[]{0, 0, 0, 0}, containerLayout.getParent());

        LinearLayout productsLayout = (LinearLayout) rootView.findViewById(R.id.products_layout);
        LinearLayout valueLayout = (LinearLayout) rootView.findViewById(R.id.value_layout);
        LinearLayout newsLayout = (LinearLayout) rootView.findViewById(R.id.news_layout);
        LinearLayout contactUsLayout = (LinearLayout) rootView.findViewById(R.id.contact_us_layout);

        customViewParams.setMarginAndPadding(productsLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, productsLayout.getParent());
        customViewParams.setMarginAndPadding(valueLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, valueLayout.getParent());
        customViewParams.setMarginAndPadding(newsLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, newsLayout.getParent());
        customViewParams.setMarginAndPadding(contactUsLayout, new int[]{20, 15, 20, 15}, new int[]{35, 0, 35, 0}, contactUsLayout.getParent());

        customViewParams.setHeightAndWidth(productsLayout, 320, 320);
        customViewParams.setHeightAndWidth(valueLayout, 320, 320);
        customViewParams.setHeightAndWidth(newsLayout, 320, 320);
        customViewParams.setHeightAndWidth(contactUsLayout, 320, 320);

        TextView productsText = (TextView) rootView.findViewById(R.id.products_text);
        TextView valueText = (TextView) rootView.findViewById(R.id.value_text);
        TextView newsText = (TextView) rootView.findViewById(R.id.news_text);
        TextView contactUsText = (TextView) rootView.findViewById(R.id.contact_us_text);

        customViewParams.setTextViewCustomParams(productsText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(valueText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(newsText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(contactUsText, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);

        ImageView products = (ImageView) rootView.findViewById(R.id.products);
        ImageView valueAddedServices = (ImageView) rootView.findViewById(R.id.value_added_service);
        ImageView news = (ImageView) rootView.findViewById(R.id.news);
        ImageView contactUs = (ImageView) rootView.findViewById(R.id.contact_us);

        customViewParams.setImageViewCustomParams(products, new int[]{7, 7, 7, 7}, new int[]{0, 0, 0, 0}, 100, 100);
        customViewParams.setImageViewCustomParams(valueAddedServices, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 100, 100);
        customViewParams.setImageViewCustomParams(news, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 100, 100);
        customViewParams.setImageViewCustomParams(contactUs, new int[]{7, 7, 7, 12}, new int[]{0, 0, 0, 0}, 100, 100);

        TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 10}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);

        menu.setOnClickListener(this);
        productsLayout.setOnClickListener(this);
        contactUsLayout.setOnClickListener(this);
        valueLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();


        } else if (i == R.id.products_layout) {
            if (sharedPreferences.getBoolean(AppConstants.IS_SYNC_COMPLETED, false)) {
                ((BaseDrawerActivity) getActivity()).openFragment(new ProductDetailFragment(), true);
            } else {
                Toast.makeText(getActivity(), "Application is not synced properly, please sync it first.", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.contact_us_layout) {
            try {

                ((BaseDrawerActivity) getActivity()).openFragment(new HomeFragment(), true);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "DSE App not installed!", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.value_layout) {
            try {

              //  ((BaseDrawerActivity) getActivity()).openFragment(new VasWarrantyfragment(), true);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "VAS  not installed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
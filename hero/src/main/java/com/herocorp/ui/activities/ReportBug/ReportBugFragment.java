package com.herocorp.ui.activities.ReportBug;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 22-Jun-17.
 */

public class ReportBugFragment extends Fragment {
    TextView tv_device, tv_appversion, tv_androidversion;
    EditText et_report;
    ProgressDialog progressDialog;
    ImageView iv_submit;
    private String deviceImei;
    private String appVersion;
    private String deviceVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reportissue_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((BaseDrawerActivity) getActivity()).closeDrawer();
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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{70, 0, 0, 0}, 90, 400, 40, customTypeFace.gillSansItalic, 0);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollview);

        customViewParams.setMarginAndPadding(scrollView, new int[]{80, 20, 80, 20}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        menu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((BaseDrawerActivity) getActivity()).toggleDrawer();
                                    }
                                }

        );

        tv_androidversion = (TextView) rootView.findViewById(R.id.tv_androidversion);
        tv_appversion = (TextView) rootView.findViewById(R.id.tv_appversion);
        tv_device = (TextView) rootView.findViewById(R.id.tv_device);
        et_report = (EditText) rootView.findViewById(R.id.et_report);
        iv_submit = (ImageView) rootView.findViewById(R.id.iv_submitreport);

        PackageManager manager = getActivity().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            tv_appversion.setText("App Version: " + info.versionName);
            tv_androidversion.setText("Android Version: " + getMobileOs(getContext()));
            tv_device.setText("Device: " + getDevicename());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private String getDevicename() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String getMobileOs(Context context) {
        String osVersion = android.os.Build.VERSION.RELEASE;
        return osVersion;
    }
}

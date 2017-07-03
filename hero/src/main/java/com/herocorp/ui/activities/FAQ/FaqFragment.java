package com.herocorp.ui.activities.FAQ;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rsawh on 22-Jun-17.
 */

public class FaqFragment extends Fragment {
    TextView tv_msg;
    LinearLayout layout_content;
    ProgressDialog progressDialog;
    ArrayList<TextView> tviews = new ArrayList<TextView>();
    ArrayList<ImageView> iviews = new ArrayList<ImageView>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faq_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((BaseDrawerActivity) getActivity()).closeDrawer();
        initView(rootView);
        fetch_faq();
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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{120, 0, 0, 0}, 90, 400, 40, customTypeFace.gillSansItalic, 0);

        layout_content = (LinearLayout) rootView.findViewById(R.id.layout_faq);
        tv_msg=(TextView)rootView.findViewById(R.id.faq_message);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollview);
        customViewParams.setMarginAndPadding(scrollView, new int[]{80, 20, 80, 20}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        menu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((BaseDrawerActivity) getActivity()).toggleDrawer();
                                    }
                                }

        );
    }

    public void addView(LinearLayout layout, final String header, final String data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_vas_content, null);
        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        final TextView tv_data = (TextView) view.findViewById(R.id.tv_data);
        final ImageView iv_expand = (ImageView) view.findViewById(R.id.iv_expand);
        LinearLayout layout_content = (LinearLayout) view.findViewById(R.id.layout_content);
        tv_header.setText(header);
        tv_data.setText(Html.fromHtml(data));
        tviews.add(tv_data);
        iviews.add(iv_expand);
        iv_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_data.getVisibility() == View.VISIBLE) {
                    tv_data.setVisibility(View.GONE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                } else {
                    for (int i = 0; i < tviews.size(); i++) {
                        tviews.get(i).setVisibility(View.GONE);
                        iviews.get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                    }
                    tv_data.setVisibility(View.VISIBLE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_collapse));
                }
            }
        });
        layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_data.getVisibility() == View.VISIBLE) {
                    tv_data.setVisibility(View.GONE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                } else {
                    for (int i = 0; i < tviews.size(); i++) {
                        tviews.get(i).setVisibility(View.GONE);
                        iviews.get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                    }
                    tv_data.setVisibility(View.VISIBLE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_collapse));
                }
            }
        });
        layout.addView(view);
    }

    public void fetch_faq() {
        if (NetConnections.isConnected(getContext())) {
            try {
                progressDialog = ProgressDialog.show(getActivity(), null, null);
                progressDialog.setContentView(R.layout.progresslayout);
                JSONObject jsonParams = new JSONObject();
                Log.e("request", jsonParams.toString());
                new AsyncHttpClient().get(getActivity(), URLConstants.FETCH_FAQ, new AsyncHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        progressDialog.dismiss();
                        String str = new String(responseBody);
                        Log.e("response", str);
                        tv_msg.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            JSONArray jsonArray = jsonObject.getJSONArray("Result");
                            tviews.clear();
                            iviews.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                addView(layout_content, jsonObject1.getString("heading"), jsonObject1.getString("headingContent"));
                            }
                        } catch (Exception e) {
                            Log.e("error", e.toString());
                            tv_msg.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        progressDialog.dismiss();
                        tv_msg.setVisibility(View.VISIBLE);
                        Log.e("error", error.toString());

                    }
                });
            } catch (Exception e) {
                tv_msg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }

}

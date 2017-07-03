package com.herocorp.ui.activities.VAS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.EMI.EmicalcFragment;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.FAQ.FaqFragment;
import com.herocorp.ui.activities.news.Fragment.NewsWebViewLayout;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.FileDownload;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rsawh on 21-Jun-17.
 */

public class VasHomeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    LinearLayout layout_vascontent;
    ProgressDialog progressDialog;
    ArrayList<TextView> tviews = new ArrayList<TextView>();
    ArrayList<TextView> tdetailviews = new ArrayList<TextView>();
    ArrayList<ImageView> iviews = new ArrayList<ImageView>();
    TextView tv_msg;
    String filenewname;
    int flag = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.vas_home, container, false);
        ((BaseDrawerActivity) getActivity()).closeDrawer();
        initView(rootView);
        if (NetConnections.isConnected(getContext())) {
            fetch_vas();
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_msg.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No Internet Connection !!", Toast.LENGTH_LONG).show();
        }


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
        customViewParams.setMarginAndPadding(topLayout, new int[]{80, 10, 80, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 620, 40, customTypeFace.gillSansItalic, 0);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{80, 20, 80, 0}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{80, 20, 80, 140}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        tv_msg = (TextView) rootView.findViewById(R.id.tv_message);

        TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 30}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);

        LinearLayout emi_layout = (LinearLayout) rootView.findViewById(R.id.emi_layout);
        LinearLayout faq_layout = (LinearLayout) rootView.findViewById(R.id.faq_layout);
        LinearLayout reportbug_layout = (LinearLayout) rootView.findViewById(R.id.report_layout);

        layout_vascontent = (LinearLayout) rootView.findViewById(R.id.layout_vascontent);

        emi_layout.setOnClickListener(this);
        faq_layout.setOnClickListener(this);
        reportbug_layout.setOnClickListener(this);
        menu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((BaseDrawerActivity) getActivity()).toggleDrawer();
                                    }
                                }

        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emi_layout:
                startActivity(new Intent(getContext(), EmicalcFragment.class));
                break;
            case R.id.faq_layout:
                if (NetConnections.isConnected(getContext()))
                    setFragment(new FaqFragment());
                else
                    Toast.makeText(getContext(), "No Internet Connection !!", Toast.LENGTH_LONG).show();

                break;
            case R.id.report_layout:
                if (NetConnections.isConnected(getContext())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", "https://172.20.5.25/codesk");
                    Fragment f = new NewsWebViewLayout();
                    f.setArguments(bundle);
                    FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.content_vashome, f);
                    ft.commit();
                } else
                    Toast.makeText(getContext(), "No Internet Connection !!", Toast.LENGTH_LONG).show();

                // setFragment(new ReportBugFragment());
                break;
        }
    }

    public void addView(LinearLayout layout, final String header, final String data, String url) {
        //  String url = "http:\\/\\/www.heromotocorp.com\\/en-in\\/press-releases\\/dream-dakar-debut-for-hero-motosports-joaquim-rodrigues-claims-10th-place-cs-santosh-finishes-in-47-314.html";
        //String url = "http://dnd.wizards.com/sites/default/files/media/2016_010516_Yawning_Portal_TH.jpg";

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_vas_content, null);
        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        final TextView tv_data = (TextView) view.findViewById(R.id.tv_data);
        final ImageView iv_expand = (ImageView) view.findViewById(R.id.iv_expand);
        final TextView tv_details = (TextView) view.findViewById(R.id.tv_details);
        LinearLayout layout_content = (LinearLayout) view.findViewById(R.id.layout_content);
        tv_header.setText(header);
        tv_data.setText(Html.fromHtml(data));
        tviews.add(tv_data);
        tdetailviews.add(tv_details);
        iviews.add(iv_expand);
        iv_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_data.getVisibility() == View.VISIBLE) {
                    tv_data.setVisibility(View.GONE);
                    tv_details.setVisibility(View.GONE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                } else {
                    for (int i = 0; i < tviews.size(); i++) {
                        tviews.get(i).setVisibility(View.GONE);
                        tdetailviews.get(i).setVisibility(View.GONE);
                        iviews.get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                    }
                    tv_data.setVisibility(View.VISIBLE);
                    tv_details.setVisibility(View.VISIBLE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_collapse));
                }
            }
        });
        layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_data.getVisibility() == View.VISIBLE) {
                    tv_data.setVisibility(View.GONE);
                    tv_details.setVisibility(View.GONE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                } else {
                    for (int i = 0; i < tviews.size(); i++) {
                        tviews.get(i).setVisibility(View.GONE);
                        tdetailviews.get(i).setVisibility(View.GONE);
                        iviews.get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
                    }
                    tv_data.setVisibility(View.VISIBLE);
                    tv_details.setVisibility(View.VISIBLE);
                    iv_expand.setImageDrawable(getResources().getDrawable(R.drawable.ic_collapse));
                }
            }
        });

        final String finalUrl = url;
        tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalUrl.equalsIgnoreCase("null") && ((finalUrl.startsWith("http") || finalUrl.startsWith("https")))) {
                    if (finalUrl.endsWith("html") || ((finalUrl.startsWith("http") || finalUrl.startsWith("https")) && !(finalUrl.endsWith("pdf") || finalUrl.endsWith("PDF") || finalUrl.endsWith("doc") || finalUrl.endsWith("DOC") || finalUrl.endsWith("DOCX")) || finalUrl.endsWith("docx"))) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", finalUrl);
                        Fragment f = new NewsWebViewLayout();
                        f.setArguments(bundle);
                        FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.addToBackStack(null);
                        ft.replace(R.id.content_vashome, f);
                        ft.commit();
                    } else if (finalUrl.endsWith("pdf") || finalUrl.endsWith("PDF")) {
                        flag = 1;
                        filenewname = finalUrl.substring(finalUrl.lastIndexOf('/') + 1);
                        new FileDownload(finalUrl, getContext(), filenewname, flag).execute();
                    } else if (finalUrl.endsWith("doc") || finalUrl.endsWith("DOC") || finalUrl.endsWith("DOCX") || finalUrl.endsWith("docx")) {
                        filenewname = finalUrl.substring(finalUrl.lastIndexOf('/') + 1);
                        flag = 2;
                        new FileDownload(finalUrl, getContext(), filenewname, flag).execute();
                    } else if (finalUrl.endsWith("jpg") || finalUrl.endsWith("jpeg") || finalUrl.endsWith("JPG") || finalUrl.endsWith("JPEG") || finalUrl.endsWith("PNG") || finalUrl.endsWith("png")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", finalUrl);
                        Fragment f = new NewsWebViewLayout();
                        f.setArguments(bundle);
                        FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.addToBackStack(null);
                        ft.replace(R.id.content_vashome, f);
                        ft.commit();
                    }
                } else
                    Toast.makeText(getContext(), "No Details Available !!", Toast.LENGTH_LONG).show();
            }

        });
        layout.addView(view);

    }

    public void fetch_vas() {
        if (NetConnections.isConnected(getContext())) {
            try {
                progressDialog = ProgressDialog.show(getActivity(), null, null);
                progressDialog.setContentView(R.layout.progresslayout);
                JSONObject jsonParams = new JSONObject();
                Log.e("request", jsonParams.toString());
                new AsyncHttpClient().get(getActivity(), URLConstants.FETCH_VAS, new AsyncHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            progressDialog.dismiss();
                            String str = new String(responseBody);
                            Log.d("response", str);
                            JSONObject jsonObject = new JSONObject(str);
                            JSONArray jsonArray = jsonObject.getJSONArray("Result");
                            tviews.clear();
                            iviews.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                addView(layout_vascontent, jsonObject1.getString("heading"), jsonObject1.getString("content"), jsonObject1.getString("content_url"));
                            }
                        } catch (Exception e) {
                            tv_msg.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        try {
                            progressDialog.dismiss();
                            tv_msg.setVisibility(View.VISIBLE);
                            Log.e("error", error.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (Exception e) {
                progressDialog.dismiss();
                tv_msg.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
    }

    public void setFragment(Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_vashome, f);
        ft.addToBackStack(null);
        ft.commit();
    }

}


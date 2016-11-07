package com.herocorp.ui.activities.DSEapp.Fragment.Search;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.TodayFollowup.TodayFollowupFragment;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rsawh on 14-Sep-16.
 */
public class SearchfilterFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;

    ImageView exppurchasedate_button, nextfollowup_button;
    Button fromdate_button, todate_button, followdate_button;
    Button all_button, finance_button, nonfinance_button, submit_button;
    Spinner spin_model;

    private int mYear, mMonth, mDay;
    String date, fromdate = "", todate, followdate, model, filter = "";
    int purch_flag = 0, followup_flag = 0;

    String encryptdata;
    ArrayList<String> arr_modellist = new ArrayList<String>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_searchfilter_fragment, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{90, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        exppurchasedate_button = (ImageView) rootView.findViewById(R.id.exppurchasedate_button);
        nextfollowup_button = (ImageView) rootView.findViewById(R.id.nextfollowup_button);

        fromdate_button = (Button) rootView.findViewById(R.id.fromdate_button);
        todate_button = (Button) rootView.findViewById(R.id.todate_button);
        followdate_button = (Button) rootView.findViewById(R.id.followdate_button);
        fromdate_button.setText(new SimpleDateFormat("dd-MMM-yy").format(new Date()));
        todate_button.setText(new SimpleDateFormat("dd-MMM-yy").format(new Date()));
        followdate_button.setText(new SimpleDateFormat("dd-MMM-yy").format(new Date()));

        all_button = (Button) rootView.findViewById(R.id.all_button);
        finance_button = (Button) rootView.findViewById(R.id.finanace_button);
        nonfinance_button = (Button) rootView.findViewById(R.id.nonfinance_button);
        submit_button = (Button) rootView.findViewById(R.id.submit_button);

        all_button.setTextColor(Color.RED);
        all_button.setBackgroundColor(Color.WHITE);
        finance_button.setTextColor(Color.RED);
        finance_button.setBackgroundColor(Color.WHITE);
        nonfinance_button.setTextColor(Color.RED);
        nonfinance_button.setBackgroundColor(Color.WHITE);

        spin_model = (Spinner) rootView.findViewById(R.id.model_spinner);

        spin_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            Bundle bundle = this.getArguments();
            encryptdata = bundle.getString("user_id");
            String newurlparams = "data=" + URLEncoder.encode(encryptdata, "UTF-8");
            NetworkConnect networkConnect = new NetworkConnect(URLConstants.BIKE_MAKE_MODEL, newurlparams);
            jsonparse(networkConnect.execute());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        exppurchasedate_button.setOnClickListener(this);
        nextfollowup_button.setOnClickListener(this);
        fromdate_button.setOnClickListener(this);
        todate_button.setOnClickListener(this);
        followdate_button.setOnClickListener(this);
        all_button.setOnClickListener(this);
        finance_button.setOnClickListener(this);
        nonfinance_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        menu.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.exppurchasedate_button) {
            if (purch_flag == 0) {
                exppurchasedate_button.setImageResource(R.drawable.button_on);
                fromdate_button.setEnabled(true);
                todate_button.setEnabled(true);
                fromdate_button.setTextColor(Color.BLACK);
                todate_button.setTextColor(Color.BLACK);
                fromdate_button.setText(new SimpleDateFormat("dd-MMM-yy").format(new Date()));
                todate_button.setText(new SimpleDateFormat("dd-MMM-yy").format(new Date()));
                purch_flag = 1;

            } else {
                exppurchasedate_button.setImageResource(R.drawable.button_offf);
                fromdate_button.setEnabled(false);
                todate_button.setEnabled(false);
                fromdate_button.setTextColor(Color.GRAY);
                todate_button.setTextColor(Color.GRAY);

                purch_flag = 0;
            }
        } else if (i == R.id.nextfollowup_button) {
            if (followup_flag == 0) {
                nextfollowup_button.setImageResource(R.drawable.button_on);
                followdate_button.setEnabled(true);
                followdate_button.setTextColor(Color.BLACK);
                followdate_button.setText(new SimpleDateFormat("dd-MMM-yy").format(new Date()));
                followup_flag = 1;
            } else {
                nextfollowup_button.setImageResource(R.drawable.button_offf);
                followdate_button.setEnabled(false);
                followdate_button.setTextColor(Color.GRAY);
                followup_flag = 0;
            }

        } else if (i == R.id.fromdate_button) {
            showdatepicker(fromdate_button);

        } else if (i == R.id.todate_button) {
            showdatepicker(todate_button);

        } else if (i == R.id.followdate_button) {
            showdatepicker(followdate_button);

        } else if (i == R.id.all_button) {
            all_button.setTextColor(Color.WHITE);
            all_button.setBackgroundColor(Color.RED);
            finance_button.setTextColor(Color.RED);
            finance_button.setBackgroundColor(Color.WHITE);
            nonfinance_button.setTextColor(Color.RED);
            nonfinance_button.setBackgroundColor(Color.WHITE);
            filter = "all";

        } else if (i == R.id.finanace_button) {
            all_button.setTextColor(Color.RED);
            all_button.setBackgroundColor(Color.WHITE);
            finance_button.setTextColor(Color.WHITE);
            finance_button.setBackgroundColor(Color.RED);
            nonfinance_button.setTextColor(Color.RED);
            nonfinance_button.setBackgroundColor(Color.WHITE);
            filter = "finance";

        } else if (i == R.id.nonfinance_button) {
            all_button.setTextColor(Color.RED);
            all_button.setBackgroundColor(Color.WHITE);
            finance_button.setTextColor(Color.RED);
            finance_button.setBackgroundColor(Color.WHITE);
            nonfinance_button.setTextColor(Color.WHITE);
            nonfinance_button.setBackgroundColor(Color.RED);
            filter = "nonfinance";

        } else if (i == R.id.submit_button) {
            if (!filter.equals(""))
                pickdata(filter);
            else
                Toast.makeText(getContext(), "Please select an option to search !!", Toast.LENGTH_SHORT).show();
        }
    }


    public String showdatepicker(final Button button) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        datechange(date);
                        mYear = year;
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        button.setText(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        return date;

    }

    public void datechange(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("dd-MMM-yy");
            date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void jsonparse(String result) {
        try {
            String id = "";
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("make");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                if (object.getString("make_name").equals("HERO MOTOCORP"))
                    id = object.getString("id");
            }

            jarray = jsono.getJSONArray("model");
            arr_modellist.add("--select--");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                if (object.getString("make_id").equals(id))
                    arr_modellist.add(object.getString("model_name"));
            }
            ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, arr_modellist);
            spin_model.setAdapter(at1);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));
        }
    }


    public void pickdata(String filter) {
        int check = 0;

        if (!(purch_flag == 1 && followup_flag == 1 && !model.equals("--select--")) && filter.equals("all")) {
            check = 1;

        }
        if (!(purch_flag == 1 && followup_flag == 1 && !model.equals("--select--")) && filter.equals("finance")) {
            check = 2;

        }
        if (!(purch_flag == 1 && followup_flag == 1 && !model.equals("--select--")) && filter.equals("nonfinance")) {
            check = 3;

        }
        if (purch_flag == 1 && followup_flag == 1 && !model.equals("--select--") && filter.equals("all")) {
            check = 4;

        }
        if (purch_flag == 1 && followup_flag == 1 && !model.equals("--select--") && filter.equals("finance")) {

            check = 5;
        }
        if (purch_flag == 1 && followup_flag == 1 && !model.equals("--select--") && filter.equals("nonfinance")) {

            check = 6;
        }
        if (purch_flag == 1 && followup_flag == 1 && model.equals("--select--") && filter.equals("all")) {

            check = 7;
        }
        if (purch_flag == 1 && followup_flag == 1 && model.equals("--select--") && filter.equals("finance")) {

            check = 8;
        }
        if (purch_flag == 1 && followup_flag == 1 && model.equals("--select--") && filter.equals("nonfinance")) {

            check = 9;
        }
        if (purch_flag == 0 && followup_flag == 1 && model.equals("--select--") && filter.equals("all")) {

            check = 10;
        }
        if (purch_flag == 0 && followup_flag == 1 && model.equals("--select--") && filter.equals("finance")) {

            check = 11;
        }
        if (purch_flag == 0 && followup_flag == 1 && model.equals("--select--") && filter.equals("nonfinance")) {

            check = 12;
        }
        if (purch_flag == 1 && followup_flag == 0 && model.equals("--select--") && filter.equals("all")) {

            check = 13;
        }
        if (purch_flag == 1 && followup_flag == 0 && model.equals("--select--") && filter.equals("finance")) {

            check = 14;
        }
        if (purch_flag == 1 && followup_flag == 0 && model.equals("--select--") && filter.equals("nonfinance")) {

            check = 15;
        }
        if (purch_flag == 0 && followup_flag == 0 && !model.equals("--select--") && filter.equals("all")) {

            check = 16;
        }
        if (purch_flag == 0 && followup_flag == 0 && !model.equals("--select--") && filter.equals("finance")) {

            check = 17;
        }
        if (purch_flag == 0 && followup_flag == 0 && !model.equals("--select--") && filter.equals("nonfinance")) {

            check = 18;
        }
        if (purch_flag == 1 && followup_flag == 0 && !model.equals("--select--") && filter.equals("all")) {

            check = 19;
        }
        if (purch_flag == 1 && followup_flag == 0 && !model.equals("--select--") && filter.equals("finance")) {

            check = 20;
        }
        if (purch_flag == 1 && followup_flag == 0 && !model.equals("--select--") && filter.equals("nonfinance")) {

            check = 21;
        }
        if (purch_flag == 0 && followup_flag == 1 && !model.equals("--select--") && filter.equals("all")) {

            check = 22;
        }
        if (purch_flag == 0 && followup_flag == 1 && !model.equals("--select--") && filter.equals("finance")) {

            check = 23;
        }
        if (purch_flag == 0 && followup_flag == 1 && !model.equals("--select--") && filter.equals("nonfinance")) {

            check = 24;
        }

        fromdate = fromdate_button.getText().toString();
        todate = todate_button.getText().toString();
        followdate = followdate_button.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("fromdate", fromdate);
        bundle.putString("todate", todate);
        bundle.putString("followdate", followdate);
        bundle.putString("model", model);
        bundle.putString("filter", filter);
        bundle.putInt("check", check);
        bundle.putString("user_id", encryptdata);
        bundle.putString("user", "ROBINK11610");
        bundle.putInt("flag", 0);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = new TodayFollowupFragment();
        f.setArguments(bundle);
        ft.replace(R.id.content_searchfilter, f);
        ft.commit();
    }
}




package com.herocorp.ui.activities.DSEapp.Fragment.Followup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rsawh on 26-Sep-16.
 */
public class FollowupFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    EditText followupreason;
    ImageView imageview_close, imageview_submit;
    Button newscheduledate;
    private int mYear, mMonth, mDay;
    String date, follow_date, reason;
    String encryptuser, user, enquiryid;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_followup_fragment, container, false);

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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{70, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 10, 100, 40}, new int[]{0, 0, 0, 0}, topLayout1.getParent());


        followupreason = (EditText) rootView.findViewById(R.id.edittext_followupreason);
        imageview_close = (ImageView) rootView.findViewById(R.id.imageView_closefollowup);
        imageview_submit = (ImageView) rootView.findViewById(R.id.imageView_submitfollowup);
        newscheduledate = (Button) rootView.findViewById(R.id.button_scheduledate);

        //setting dates
        date = new SimpleDateFormat("dd MMM yyyy").format(new Date());
        follow_date = new SimpleDateFormat("dd MMM yyyy").format(new Date());
        newscheduledate.setText(follow_date);

        //fetching user
        try {
            Bundle bundle = this.getArguments();
            encryptuser = bundle.getString("user_id");
            user = bundle.getString("user");
            enquiryid = bundle.getString("enquiry_id");

        } catch (Exception e) {
            e.printStackTrace();
        }

        menu.setOnClickListener(this);
        imageview_close.setOnClickListener(this);
        imageview_submit.setOnClickListener(this);
        newscheduledate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.imageView_closefollowup) {
            getActivity().onBackPressed();
        } else if (i == R.id.button_scheduledate) {
            showdatepicker(newscheduledate);
        } else if (i == R.id.imageView_submitfollowup) {

            reason = followupreason.getText().toString();
            if (reason.equals("") || date.equals("")) {
                Toast.makeText(getContext(), "Please fill all the details !!", Toast.LENGTH_SHORT).show();

            } else {
                if (NetConnections.isConnected(getContext())) {
                    String newurlparams = null;
                    Toast.makeText(getContext(), reason + date + follow_date+enquiryid, Toast.LENGTH_SHORT).show();
                    ProgressDialog progress = new ProgressDialog(getContext());
                    try {
                        String data = "{\"date\":\"" + date + "\",\"remarks\":\"" + reason + "\",\"fol_date\":\"" + follow_date +
                                "\", \"user_id\":\"" + user + "\",\"dms_enquiry_id\":\"" + enquiryid + "\"}";
                        newurlparams = "data=" + URLEncoder.encode(data, "UTF-8");
                        new NetworkConnect1(URLConstants.SYNC_FOLLOW_UP, newurlparams, progress, "Followup has been successfully submitted.", getContext(),1).execute();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                    }
                } else
                    Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
            }
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

                        follow_date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        datechange(follow_date);
                        mYear = year;
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        button.setText(follow_date);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        return date;

    }

    public void datechange(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("dd MMM yyyy");
            follow_date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}

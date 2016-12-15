package com.herocorp.ui.activities.DSEapp.Fragment.Followup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.herocorp.ui.activities.DSEapp.Utilities.Dateformatter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Next_Followup;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    String encryptuser;
    String user, enquiryid;
    DatabaseHelper db;
    String followup_status;
    int enq_flag = 0;
    String purch_date;
    Date start, end;


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
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 10, 80, 40}, new int[]{0, 0, 0, 0}, topLayout1.getParent());


        followupreason = (EditText) rootView.findViewById(R.id.edittext_followupreason);
        imageview_close = (ImageView) rootView.findViewById(R.id.imageView_closefollowup);
        imageview_submit = (ImageView) rootView.findViewById(R.id.imageView_submitfollowup);
        newscheduledate = (Button) rootView.findViewById(R.id.button_scheduledate);

        //setting dates
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 3);  // number of days to add
        String dt1 = sdf.format(c.getTime());
        newscheduledate.setText(dt1);

        follow_date = dt1;
        // datechange(dt1);

        // follow_date = new SimpleDateFormat("dd MMM yyyy").format(new Date());


        //fetching user
        try {
            Bundle bundle = this.getArguments();
           /* encryptuser = bundle.getString("user_id");*/
            user = bundle.getString("user");
            enquiryid = bundle.getString("enquiry_id");
            purch_date = bundle.getString("pur_date");
            if (bundle.containsKey("enq_flag"))
                enq_flag = bundle.getInt("enq_flag");

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
            try {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                reason = followupreason.getText().toString();
                convertdate();
                String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
                Date current_date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                        .parse(date);
                if (reason.equals("")) {
                    Toast.makeText(getContext(), "Please fill all the details !!", Toast.LENGTH_SHORT).show();

                }/* else if (end.before(start)) {
                    Toast.makeText(getContext(), "New schedule must be before purchase date!!", Toast.LENGTH_SHORT).show();

                }*/ else if (start.before(current_date)) {
                    Toast.makeText(getContext(), "Invalid schedule!!", Toast.LENGTH_SHORT).show();

                } else {
                    followup_status = "1";
                    db = new DatabaseHelper(getContext());
                    if (enq_flag == 0) {
                        db.update_followup(followup_status, follow_date.toUpperCase(), reason, enquiryid);
                    } else
                        db.update_contactfollowup(followup_status, follow_date.toUpperCase(), reason, enquiryid);

                    if (NetConnections.isConnected(getContext())) {
                        String newurlparams = null;
                        ProgressDialog progress = new ProgressDialog(getContext());
                        try {
                       /* String data = "{\"date\":\"" + date + "\",\"remarks\":\"" + reason + "\",\"fol_date\":\"" + follow_date +
                                "\", \"user_id\":\"" + user + "\",\"dms_enquiry_id\":\"" + enquiryid + "\"}";*/
                            follow_date = Dateformatter.dateformat1(follow_date);

                            JSONObject jsonparams = new JSONObject();
                            jsonparams.put("date", date);
                            jsonparams.put("remarks", reason);
                            jsonparams.put("fol_date", follow_date);
                            jsonparams.put("user_id", user);
                            jsonparams.put("dms_enquiry_id", enquiryid);
                            String json = jsonparams.toString().replace("\\/", "/");
                            Log.e("followup", json);
                            newurlparams = "data=" + URLEncoder.encode(json, "UTF-8");

                            new NetworkConnect1(URLConstants.SYNC_FOLLOW_UP, newurlparams, progress, "Followup has been successfully submitted.", getContext(), 1).execute();
                        } catch (Exception e) {
                        }
                    } else {
                        db.add_next_followup(new Next_Followup(date, reason, follow_date, user, enquiryid, "1"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String showdatepicker(final Button button) {
        // Get Current Date
        Date currentdate = new Date();
        SimpleDateFormat newFormatDate = new SimpleDateFormat(
                "dd-MMM-yyyy");
        try {
            currentdate = newFormatDate.parse(button.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(currentdate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        /*view.setMinDate(c.getTimeInMillis() - 1000);*/
                        /*datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis() - 1000);*/
                        follow_date = (dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;
                        mYear = year;
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        follow_date = Dateformatter.dateformat2(follow_date);
                        button.setText(follow_date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        return date;
    }

    /* public String showdatepicker(final Button button) {
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

     }*/
    public void datechange(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("MM/dd/yyyy");
            follow_date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void datechange1(String olddate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate;
        try {
            newDate = format.parse(olddate);
            format = new SimpleDateFormat("dd-MMM-yyyy");
            follow_date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void convertdate() throws ParseException {
        start = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH).parse(follow_date);
       /* end = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH)
                .parse(purch_date);*/
    }
}

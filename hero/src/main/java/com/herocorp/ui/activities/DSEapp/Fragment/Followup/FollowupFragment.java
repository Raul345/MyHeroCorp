package com.herocorp.ui.activities.DSEapp.Fragment.Followup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.activities.DSEapp.DbSyncservice;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.DialogPitchImage;
import com.herocorp.ui.activities.DSEapp.Utilities.Dateformatter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Next_Followup;
import com.herocorp.ui.activities.DSEapp.models.Pitch;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    LinearLayout layout_lov, layout_finance, layout_testdrive, layout_visit, layout_schedulecall;
    CheckBox chk_lov, chk_finance, chk_testdrive, chk_visit, chk_schedulecall;
    TextView tv_lov, tv_finance, tv_testdrive, tv_visit, tv_schedulecall;

    String lov = "contacted", finance = "No", testdrive = "No", visit = "No", schedulecall = "No", sales_pitch_no = "";
    String gender = "", age = "", occupation = "", ownership = "", area = "", usage = "";
    ImageView image_addpitch;
    RelativeLayout layout_addpitch;
    TextView tv_pitchid;
    LinearLayout layout_pitch;


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

        layout_lov = (LinearLayout) rootView.findViewById(R.id.layout_lov);
        layout_finance = (LinearLayout) rootView.findViewById(R.id.layout_finance);
        layout_schedulecall = (LinearLayout) rootView.findViewById(R.id.layout_schedulecall);
        layout_visit = (LinearLayout) rootView.findViewById(R.id.layout_visit);
        layout_testdrive = (LinearLayout) rootView.findViewById(R.id.layout_testdrive);


        tv_lov = (TextView) rootView.findViewById(R.id.tv_lov);
        tv_finance = (TextView) rootView.findViewById(R.id.tv_finance);
        tv_schedulecall = (TextView) rootView.findViewById(R.id.tv_schedulecall);
        tv_visit = (TextView) rootView.findViewById(R.id.tv_visit);
        tv_testdrive = (TextView) rootView.findViewById(R.id.tv_testdrive);

        chk_lov = (CheckBox) rootView.findViewById(R.id.chk_lov);
        chk_finance = (CheckBox) rootView.findViewById(R.id.chk_finance);
        chk_schedulecall = (CheckBox) rootView.findViewById(R.id.chk_schedulecall);
        chk_visit = (CheckBox) rootView.findViewById(R.id.chk_visit);
        chk_testdrive = (CheckBox) rootView.findViewById(R.id.chk_testdrive);

        image_addpitch = (ImageView) rootView.findViewById(R.id.image_addpitch);
        layout_addpitch = (RelativeLayout) rootView.findViewById(R.id.layout_addpitch);
        tv_pitchid = (TextView) rootView.findViewById(R.id.tv_pitchid);
        layout_pitch = (LinearLayout) rootView.findViewById(R.id.layout_pitch);

        //setting dates
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

       /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);  // number of days to add
        String dt1 = sdf.format(c.getTime());*/
        String dt1 = followup_datechange(3);
        newscheduledate.setText(dt1);

        follow_date = dt1;

        // datechange(dt1);

        // follow_date = new SimpleDateFormat("dd MMM yyyy").format(new Date());

        //fetching user
        try {
            Bundle bundle = this.getArguments();
            user = bundle.getString("user");
            enquiryid = bundle.getString("enquiryid");
            purch_date = bundle.getString("pur_date");
            if (bundle.containsKey("sales_pitch_no"))
                sales_pitch_no = bundle.getString("sales_pitch_no");
            if (bundle.containsKey("enq_flag"))
                enq_flag = bundle.getInt("enq_flag");
            if(sales_pitch_no.equals(null) || sales_pitch_no.equals("null"))
                sales_pitch_no="";
            fetchpitch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        chk_lov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lov = "not_contacted";
                    String dt1 = followup_datechange(1);
                    newscheduledate.setText(dt1);
                    follow_date = dt1;
                } else {
                    lov = "contacted";
                    String dt1 = followup_datechange(3);
                    newscheduledate.setText(dt1);
                    follow_date = dt1;
                }
            }
        });
        chk_testdrive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    testdrive = "Yes";
                } else {
                    testdrive = "No";
                }
            }
        });
        chk_finance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    finance = "Yes";
                } else {
                    finance = "No";
                }
            }
        });
        chk_visit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    visit = "Yes";
                } else {
                    visit = "No";
                }
            }
        });
        chk_schedulecall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    schedulecall = "Yes";
                } else {
                    schedulecall = "No";
                }
            }
        });

        image_addpitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_pitch.getVisibility() == View.VISIBLE)
                    layout_pitch.setVisibility(View.GONE);
                else layout_pitch.setVisibility(View.VISIBLE);
            }
        });

        menu.setOnClickListener(this);
        imageview_close.setOnClickListener(this);
        imageview_submit.setOnClickListener(this);
        newscheduledate.setOnClickListener(this);
        layout_lov.setOnClickListener(this);
        layout_testdrive.setOnClickListener(this);
        layout_visit.setOnClickListener(this);
        layout_schedulecall.setOnClickListener(this);
        layout_finance.setOnClickListener(this);
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
                    Toast.makeText(getContext(), "Please fill Followup Action !!", Toast.LENGTH_SHORT).show();

                }/* else if (end.before(start)) {
                    Toast.makeText(getContext(), "New schedule must be before purchase date!!", Toast.LENGTH_SHORT).show();

                }*/ else if (start.before(current_date)) {
                    Toast.makeText(getContext(), "Invalid schedule!!", Toast.LENGTH_SHORT).show();

                } else {
                    followup_status = "1";
                    db = new DatabaseHelper(getContext());
                    if (enq_flag == 0) {
                        db.update_followup(followup_status, Dateformatter.dateformat4(follow_date).toUpperCase(), reason, enquiryid);
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
                            jsonparams.put("followup_done", lov);
                            jsonparams.put("remarks", reason + "~" + tv_testdrive.getText() + "~" + testdrive + "~" + tv_finance.getText() +
                                    "~" + finance + "~" + tv_visit.getText() + "~" + visit + "~" + tv_schedulecall.getText() + "~" + schedulecall);
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
                        db.add_next_followup(new Next_Followup(date, reason, follow_date, user, enquiryid, lov, "1"));
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "Followup has been successfully submitted.");
                        bundle.putInt("flag", 1);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        AlertDialogFragment dialogFragment = new AlertDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.setCancelable(false);
                        dialogFragment.show(fm, "Sample Fragment");
                       /* Intent intent = new Intent(getContext(), DbSyncservice.class);
                        getActivity().startService(intent);*/
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i == R.id.layout_lov) {
            if (chk_lov.isChecked()) {
                chk_lov.setChecked(false);
                lov = "contacted";
                String dt1 = followup_datechange(3);
                newscheduledate.setText(dt1);
                follow_date = dt1;

            } else {
                chk_lov.setChecked(true);
                lov = "not_contacted";
                String dt1 = followup_datechange(1);
                newscheduledate.setText(dt1);
                follow_date = dt1;
            }
        } else if (i == R.id.layout_visit) {
            if (chk_visit.isChecked()) {
                chk_visit.setChecked(false);
                visit = "No";
            } else {
                chk_visit.setChecked(true);
                visit = "Yes";
            }
        } else if (i == R.id.layout_finance) {
            if (chk_finance.isChecked()) {
                chk_finance.setChecked(false);
                finance = "No";
            } else {
                chk_finance.setChecked(true);
                finance = "Yes";
            }
        } else if (i == R.id.layout_schedulecall) {
            if (chk_schedulecall.isChecked()) {
                chk_schedulecall.setChecked(false);
                schedulecall = "No";
            } else {
                chk_schedulecall.setChecked(true);
                schedulecall = "Yes";
            }
        } else if (i == R.id.layout_testdrive) {
            if (chk_testdrive.isChecked()) {
                chk_testdrive.setChecked(false);
                testdrive = "No";
            } else {
                chk_testdrive.setChecked(true);
                testdrive = "Yes";
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

    public String followup_datechange(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return sdf.format(c.getTime());
    }

    public void fetchpitch() {
        db = new DatabaseHelper(getContext());
      /*  layout_pitch.removeAllViews();
        layout_addpitch.setVisibility(View.VISIBLE);
        tv_pitchid.setText("10");
        List<Pitch> row = db.getPitchRow("10");
        Log.e("listsize", row.size()+"");
        for (Pitch record : row) {
            try {
                Log.e("img_path", record.getImg_path());
                JSONObject jo = new JSONObject(record.getImg_path());
                String pleasure = jo.getString("Pleasure");
                String maestro = jo.getString("Maestro Edge");
                String duet = jo.getString("Duet");
                Log.e("pleasureurl", pleasure);
                Log.e("maestrourl", maestro);
                Log.e("dueturl", duet);
                addview("P", pleasure);
                addview("M", maestro);
                addview("D", duet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        if (!sales_pitch_no.equals("")) {
            layout_addpitch.setVisibility(View.VISIBLE);
            tv_pitchid.setText(sales_pitch_no);
            List<Pitch> row = db.getPitchRow(sales_pitch_no);
            for (Pitch record : row) {
                try {
                    JSONObject jo = new JSONObject(record.getImg_path());
                    String pleasure = jo.getString("Pleasure");
                    String maestro = jo.getString("Maestro Edge");
                    String duet = jo.getString("Duet");
                    Log.e("pleasureurl", pleasure);
                    Log.e("maestrourl", maestro);
                    Log.e("dueturl", duet);
                    addview("P", pleasure);
                    addview("M", maestro);
                    addview("D", duet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addview(final String str, final String imageUrl) {
        View view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_pitchimage, null);

        TextView tv_id = (TextView) view.findViewById(R.id.tv_pos);
        ImageView image = (ImageView) view.findViewById(R.id.img_newpitch);
        tv_id.setText(str);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogPitchImage dialogFragment = new DialogPitchImage();
                Bundle bundle = new Bundle();
                bundle.putString("id", str);
                bundle.putString("url", imageUrl);
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(true);
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        layout_pitch.addView(view);
    }
}

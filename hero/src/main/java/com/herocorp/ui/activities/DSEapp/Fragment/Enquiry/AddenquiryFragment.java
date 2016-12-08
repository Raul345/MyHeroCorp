package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.activities.DSEapp.Utilities.Dateformatter;
import com.herocorp.ui.activities.DSEapp.adapter.Campaignadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Campaign;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.activities.auth.SignInActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by rsawh on 14-Sep-16.
 */
public class AddenquiryFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    Spinner model_spinner, exist_vehicle_spinner, spin_existmake, spin_existmodel;
    ;
    CheckBox exchange_chkbox, finance_chkbox, test_chkbox;
    Button nextfollowdate_btn, purchdate_btn;
    EditText remarks_et;
    String encryptdata, encryptuser;
    int flag = 0;
    private int mYear, mMonth, mDay;

    String date, follow_date, purch_date, remark = "", exchange = "N", finance = "N", test = "N", model = "", existvehicle = "", existmake = "", existmodel = "";

    String firstname, lastname, mobile, email, age, gender, state, district, tehsil, village, address1, address2, pincode;

    DatabaseHelper db;

    Campaignadapter userAdapter, userAdapter1;
    ArrayList<Campaign> userArray = new ArrayList<Campaign>();
    ArrayList<String> arr_modellist = new ArrayList<String>();
    ArrayList<Bikemake> arr_makelist = new ArrayList<Bikemake>();
    ArrayList<String> arr_modellist1 = new ArrayList<String>();
    ArrayList<String> chk_campaignid = new ArrayList<String>();

    ListView userList;

    String[] vehicle_list = {"--select--", "Two Wheeler",
            "Four wheeler",
            "First Time Buyer"};

    String data;

    SharedPreferences mypref;
    SharedPreferences.Editor edit;

    private SharedPreferences sharedPreferences;
    String user_id, dealer_code, key;

    NetworkConnect networkConnect;
    LinearLayout layout_existmake, layout_existmodel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_addenquiry_fragment, container, false);

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        mypref = getActivity().getSharedPreferences("herocorp", 0);
        edit = mypref.edit();
        if (mypref.contains("key"))
            mypref.edit().remove("key").commit();

        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        final ScrollView scrollview = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollview, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, scrollview.getParent());

        model_spinner = (Spinner) rootView.findViewById(R.id.model_spinner);
        exist_vehicle_spinner = (Spinner) rootView.findViewById(R.id.existvehicle_spinner);
        spin_existmake = (Spinner) rootView.findViewById(R.id.existmake_spinner);
        spin_existmodel = (Spinner) rootView.findViewById(R.id.existmodel_spinner);

        exchange_chkbox = (CheckBox) rootView.findViewById(R.id.exchange_chkbox);
        finance_chkbox = (CheckBox) rootView.findViewById(R.id.finance_chkbox);
        test_chkbox = (CheckBox) rootView.findViewById(R.id.testride_chkbox);

        nextfollowdate_btn = (Button) rootView.findViewById(R.id.nextfollowupdate_button);
        purchdate_btn = (Button) rootView.findViewById(R.id.exptpurchasedate_button);
        layout_existmake = (LinearLayout) rootView.findViewById(R.id.layout_existmake);
        layout_existmodel = (LinearLayout) rootView.findViewById(R.id.layout_existmodel);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 3);  // number of days to add
        String dt1 = sdf.format(c.getTime());
        nextfollowdate_btn.setText(dt1);
        c.add(Calendar.DATE, 7);  // number of days to add
        dt1 = sdf.format(c.getTime());
        purchdate_btn.setText(dt1);

        remarks_et = (EditText) rootView.findViewById(R.id.edittext_remark);

        ImageView button_submit = (ImageView) rootView.findViewById(R.id.imageView_submit_addenquiry);

        userAdapter = new Campaignadapter(getContext(), R.layout.dse_campaign_row, userArray);
        userAdapter1 = new Campaignadapter(getContext(), R.layout.dse_campaign_row, userArray);

        userList = (ListView) rootView.findViewById(R.id.list_campaign);


        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, vehicle_list);
        exist_vehicle_spinner.setAdapter(at);

        try {
            fetch_data();
            fetch_pref();
            //getting make and model
            fetch_make_model();
            //    data = "{\"user_id\":\"" + username + "\",\"dealer_code\":\"" + dealercode + "\"}";


        } catch (Exception e) {
            e.printStackTrace();
        }


        model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 && !model.equalsIgnoreCase(parent.getItemAtPosition(position).toString())) {
                    model = parent.getItemAtPosition(position).toString();
                    JSONObject jsonparams = new JSONObject();
                    try {
                        jsonparams.put("user_id", user_id);
                        jsonparams.put("dealer_code", dealer_code);
                        send_request(URLConstants.GET_CAMPAIGN_DATA, jsonparams.toString(), 0);
                        chk_campaignid.clear();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                /*else {
                    userList.setAdapter(null);
                    setListViewHeightBasedOnChildren(userList);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exist_vehicle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                existvehicle = parent.getItemAtPosition(position).toString();
                if (position == 1) {
                    layout_existmake.setVisibility(View.VISIBLE);
                    layout_existmodel.setVisibility(View.VISIBLE);
                    ArrayAdapter<Bikemake> at1 = new ArrayAdapter<Bikemake>(getContext(), R.layout.spinner_textview2, arr_makelist);
                    spin_existmake.setAdapter(at1);
                } else
                    reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin_existmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    existmake = parent.getItemAtPosition(position).toString();
                    Bikemake bikemake = (Bikemake) parent.getSelectedItem();
                    Bikemodel bikemodel = new Bikemodel();
                    arr_modellist1.clear();
                    arr_modellist1 = bikemodel.getModelname(bikemake.getId());
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, arr_modellist1);
                    spin_existmodel.setAdapter(at2);

                } else {
                    existmake = "";
                    arr_modellist1.clear();
                    arr_modellist1.add("--select--");
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, arr_modellist1);
                    spin_existmodel.setAdapter(at2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spin_existmodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                existmodel = parent.getItemAtPosition(position).toString();
                if (existmodel.equals("--select--"))
                    existmodel = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        exchange_chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exchange_chkbox.isChecked()) {
                    exchange = "Y";
                    flag++;
                } else {
                    exchange = "N";
                    flag--;
                }
            }
        });
        finance_chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finance_chkbox.isChecked()) {
                    finance = "Y";
                    flag++;
                } else {
                    finance = "N";
                    flag--;
                }
            }
        });
        test_chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test_chkbox.isChecked()) {
                    test = "Y";
                    // flag = 1;
                } else {
                    test = "N";
                    // flag = 0;
                }
            }
        });


        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campaign data = userAdapter.getItem(position);
                CheckBox box = (CheckBox) view.findViewById(R.id.campaign_chkbox);
                if (box.isChecked()) {
                    box.setChecked(false);
                    chk_campaignid.remove(data.getCamp_id());
                } else {
                    box.setChecked(true);
                    chk_campaignid.add(data.getCamp_id());
                    // Toast.makeText(getContext(), chk_campaignid.get(0), Toast.LENGTH_SHORT).show();
                }
            }
        });


        button_submit.setOnClickListener(this);
        nextfollowdate_btn.setOnClickListener(this);
        purchdate_btn.setOnClickListener(this);
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        } else if (i == R.id.nextfollowupdate_button) {
            showdatepicker(nextfollowdate_btn);
        } else if (i == R.id.exptpurchasedate_button) {
            showdatepicker(purchdate_btn);
        } else if (i == R.id.imageView_submit_addenquiry) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            addenquiry();
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

                        date = (dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;
                        follow_date = Dateformatter.dateformat2(date);
                        mYear = year;
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        button.setText(follow_date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        return date;

    }

    /*  public void datechange(String olddate) {
          SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
          Date newDate;
          try {
              newDate = format.parse(olddate);
              format = new SimpleDateFormat("MM/dd/yyyy");
              follow_date = format.format(newDate);
          } catch (ParseException e) {
              e.printStackTrace();
          }
      }
  */
    public void send_request(String url, String data, int flag) {
        if (NetConnections.isConnected(getContext())) {
            try {
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");
                if (flag == 0) {
                    new Fetch_campaign(url, urlParameters).execute();
                }
                if (flag == 1) {
                    ProgressDialog progress = new ProgressDialog(getContext());
                    PreferenceUtil.set_Address(getContext(),state,district,tehsil,village);
                    new NetworkConnect1(url, urlParameters, progress, "Enquiry has been successfully submitted.", getContext(), 2).execute();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }

    /* public void jsonparse_campaign(String result) {
         try {
             userAdapter.clear();
             userAdapter1.clear();
             progressDialog.dismiss();
             // Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
             JSONObject jsono = new JSONObject(result);
             JSONArray jarray = jsono.getJSONArray("campaign_data");
             for (int i = 0; i < jarray.length(); i++) {
                 JSONObject object = jarray.getJSONObject(i);
                 String dealer_code = object.getString("dealer_code");
                 String dealer_name = object.getString("dealer_name");
                 String camp_id = object.getString("camp_id");
                 String campaign_name = object.getString("campaign_name");
                 String start_date = object.getString("start_date");
                 String end_date = object.getString("end_date");
                 String category = object.getString("category");
                 String status = object.getString("status");
                 String sub_category = object.getString("sub_category");
                 String camp_type = object.getString("camp_type");
                 String camp_source = object.getString("camp_source");
                 String model1 = object.getString("model");

                 if (model1.equalsIgnoreCase(model)) {
                     userAdapter1.add(new Campaign(dealer_code,
                             dealer_name,
                             camp_id,
                             campaign_name,
                             start_date,
                             end_date,
                             category,
                             status,
                             sub_category,
                             camp_type,
                             camp_source,
                             model1
                     ));
                     userAdapter1.notifyDataSetChanged();
                 }
                 if (model1.equalsIgnoreCase("")) {
                     userAdapter.add(new Campaign(dealer_code,
                             dealer_name,
                             camp_id,
                             campaign_name,
                             start_date,
                             end_date,
                             category,
                             status,
                             sub_category,
                             camp_type,
                             camp_source,
                             model1));
                     userAdapter.notifyDataSetChanged();
                 }
             }


         } catch (JSONException e) {
             progressDialog.dismiss();
             e.printStackTrace();
         } catch (Exception e) {
             progressDialog.dismiss();
             System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));
         }
     }
 */
    private void updateList() {
        if (userAdapter1.getCount() == 0)
            userList.setAdapter(userAdapter);
        else
            userList.setAdapter(userAdapter1);

        setListViewHeightBasedOnChildren(userList);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void fetch_data() {
        Bundle bundle = this.getArguments();
        firstname = bundle.getString("firstname");
        lastname = bundle.getString("lastname");
        mobile = bundle.getString("mobile");
        email = bundle.getString("email");
        age = bundle.getString("age");
        gender = bundle.getString("gender");
        state = bundle.getString("state");
        tehsil = bundle.getString("tehsil");
        district = bundle.getString("district");
        village = bundle.getString("village");
        address1 = bundle.getString("address1");
        address2 = bundle.getString("address2");
        pincode = bundle.getString("pin");

    }

    //adding enquiry
    public void addenquiry() {
        try {
            remark = remarks_et.getText().toString();
            Date expt_purc_date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                    .parse(purchdate_btn.getText().toString());
            Log.e("e:", expt_purc_date.toString());
            Date followup_date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                    .parse(nextfollowdate_btn.getText().toString());
            Log.e("f:", followup_date.toString());

            String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
            Date current_date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                    .parse(date);
            Log.e("c:", current_date.toString());


            follow_date = Dateformatter.dateformat1(nextfollowdate_btn.getText().toString());
            purch_date = Dateformatter.dateformat1(purchdate_btn.getText().toString());
            key = random_key(7);

            if (model.equals("") || flag == 0 || existvehicle.equals("--select--"))
                Toast.makeText(getContext(), "Please fill all the details !!", Toast.LENGTH_LONG).show();
            else if (followup_date.before(current_date))
                Toast.makeText(getContext(), "Please check the followup date!!", Toast.LENGTH_LONG).show();
            else if (expt_purc_date.before(followup_date))
                Toast.makeText(getContext(), "Please check the purchase date!!", Toast.LENGTH_LONG).show();
            else {
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("mobile", mobile);
                jsonparams.put("email", email);
                jsonparams.put("fname", firstname);
                jsonparams.put("lname", lastname);
                jsonparams.put("age", age);
                jsonparams.put("gender", gender);
                jsonparams.put("address1", address1);
                jsonparams.put("address2", address2);

                jsonparams.put("pincode", pincode);
                jsonparams.put("fol_date", follow_date);
                jsonparams.put("exp_purchase_date", purch_date);
                jsonparams.put("model_interested", model);
                jsonparams.put("exchange_req", exchange);
                jsonparams.put("finance_req", finance);
                jsonparams.put("test_ride", test);
                jsonparams.put("remarks", remark);

                jsonparams.put("existVeh", existvehicle);
                jsonparams.put("user_id", user_id);
                jsonparams.put("key", key);
                jsonparams.put("state", state);
                jsonparams.put("district", district);
                jsonparams.put("tehsil", tehsil);
                jsonparams.put("village", village);
                jsonparams.put("existMake", existmake);
                jsonparams.put("existModel", existmodel);
                jsonparams.put("dealer_code", dealer_code);

                for (int i = 0; i < chk_campaignid.size(); i++) {
                    jsonparams.put("campid" + (i + 1), chk_campaignid.get(i));
                }

                edit.putString("key", key);
                edit.commit();
                String json = jsonparams.toString().replace("\\/", "/");
                Log.e("add_enquiry", json);
                send_request(URLConstants.ADD_ENQUIRY, json, 1);
            }

        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }

    }

    //random key generator
    public String random_key(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public void onDestroy() {
        // Remove adapter reference from list
//        userList.setAdapter(null);
        super.onDestroy();
    }

    public void reset() {
        ArrayList<String> arr_reset = new ArrayList<String>();
        arr_reset.clear();
        arr_reset.add("--select--");
        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, arr_reset);
        spin_existmake.setAdapter(at);
        spin_existmodel.setAdapter(at);
        layout_existmake.setVisibility(View.GONE);
        layout_existmodel.setVisibility(View.GONE);
    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(getContext());
        dealer_code = PreferenceUtil.get_DealerCode(getContext());
       /* sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        if (sharedPreferences.contains("username"))
            user_id = sharedPreferences.getString("username", null);
        if (sharedPreferences.contains("dealercode"))
            dealer_code = sharedPreferences.getString("dealercode", null);*/
    }

    public void fetch_make_model() {
        try {
            String id = "";

            db = new DatabaseHelper(getContext());
            arr_makelist.clear();
            arr_makelist.add(new Bikemake("", "--select--"));
            List<Bikemake> allrecords = db.getAllBikemakes();
            for (Bikemake record : allrecords) {
                arr_makelist.add(new Bikemake(record.getId(), record.getMakename()));
                if (record.getMakename().equalsIgnoreCase("HERO MOTOCORP"))
                    id = record.getId();
            }

            new Bikemodel("", "--select--");
            arr_modellist.add("--select--");

            List<Bike_model> records = db.getAllBikemodels();
            for (Bike_model record : records) {
                new Bikemodel(record.getMakeid(), record.getModelname());
                if (record.getMakeid().equals(id))
                    arr_modellist.add(record.getModelname());
            }
            ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, arr_modellist);
            model_spinner.setAdapter(at1);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
        }
    }

    public class Fetch_campaign extends AsyncTask<Void, Void, String> {
        String targetURL;
        String newurlParameters;
        NetworkConnect networkConnect;
        private ProgressDialog progressDialog;

        public Fetch_campaign(String targeturl, String urlParameters) {
            this.targetURL = targeturl;
            this.newurlParameters = urlParameters;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), null, null);
            progressDialog.setContentView(R.layout.progresslayout);
        }

        protected String doInBackground(Void... params) {
            try {
                if (NetConnections.isConnected(getContext())) {
                    networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlParameters);
                    String result = networkConnect.execute();
                    if (result != null)
                        encryptdata = result.replace("\\/", "/");
                    String newurlparams = null;
                    newurlparams = "data=" + URLEncoder.encode(encryptdata, "UTF-8");
                    networkConnect = new NetworkConnect(targetURL, newurlparams);
                    result = networkConnect.execute();
                    return result;
                } else {
                    Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (UnsupportedEncodingException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                userAdapter.clear();
                userAdapter1.clear();
                Log.e("campaign", s);
                JSONObject jsono = new JSONObject(s);
                JSONArray jarray = jsono.getJSONArray("campaign_data");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    String dealer_code = object.getString("dealer_code");
                    String dealer_name = object.getString("dealer_name");
                    String camp_id = object.getString("camp_id");
                    String campaign_name = object.getString("campaign_name");
                    String start_date = object.getString("start_date");
                    String end_date = object.getString("end_date");
                    String category = object.getString("category");
                    String status = object.getString("status");
                    String sub_category = object.getString("sub_category");
                    String camp_type = object.getString("camp_type");
                    String camp_source = object.getString("camp_source");
                    String model1 = object.getString("model");

                    if (model1.equalsIgnoreCase(model)) {
                        userAdapter1.add(new Campaign(dealer_code,
                                dealer_name,
                                camp_id,
                                campaign_name,
                                start_date,
                                end_date,
                                category,
                                status,
                                sub_category,
                                camp_type,
                                camp_source,
                                model1
                        ));
                        userAdapter1.notifyDataSetChanged();
                    }
                    if (model1.equalsIgnoreCase("")) {
                        userAdapter.add(new Campaign(dealer_code,
                                dealer_name,
                                camp_id,
                                campaign_name,
                                start_date,
                                end_date,
                                category,
                                status,
                                sub_category,
                                camp_type,
                                camp_source,
                                model1));
                        userAdapter.notifyDataSetChanged();
                    }
                }
                updateList();
                progressDialog.dismiss();
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Check your Connection !!" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
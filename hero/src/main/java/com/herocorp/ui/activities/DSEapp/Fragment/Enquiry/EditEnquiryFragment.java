package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.activities.DSEapp.Utilities.Dateformatter;
import com.herocorp.ui.activities.DSEapp.adapter.Campaignadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Campaign;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

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
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class EditEnquiryFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private View rootView;
    private CustomViewParams customViewParams;
    private int mPage;

    Spinner spin_interested_model, spin_existvehicle, spin_existmake, spin_existmodel;
    CheckBox exchange_chkbox, finance_chkbox, test_chkbox;
    Button nextfollowdate_btn, purchdate_btn;
    EditText remarks_et;
    ImageView button_submit;
    ListView userList;

    String[] vehicle_list = {"--select--", "Two Wheeler",
            "Four wheeler",
            "First Time Buyer"};

    Campaignadapter userAdapter, userAdapter1;
    ArrayList<Campaign> userArray = new ArrayList<Campaign>();
    ArrayList<String> arr_modellist = new ArrayList<String>();
    ArrayList<String> arr_modellist1 = new ArrayList<String>();
    ArrayList<Bikemake> arr_makelist = new ArrayList<Bikemake>();
    ArrayList<String> chk_campaignid = new ArrayList<String>();

    //String username = "ROBINK11610", dealercode = "11610" ;
    int flag = 0;

    private int mYear, mMonth, mDay;
    String date, follow_date = "", next_follow_date = "", purch_date = "", remark = "", exchange = "N", finance = "N", test = "N", model = "", existvehicle = "", existmake = "", existmodel = "";
    String firstname = "", lastname = "", email = "", mobile = "", age = "", gender = "", state = "", district = "", tehsil = "", village = "", address1 = "", address2 = "", pincode = "";

    String encryptdata, data;
    int page_flag;
    NetworkConnect networkConnect;

    SharedPreferences mypref;
    String user_id, dealer_code, key = "", enquiry_id = "";

    LinearLayout layout_existmake, layout_existmodel;
    DatabaseHelper db;

    public static EditEnquiryFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EditEnquiryFragment fragment = new EditEnquiryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mypref = getActivity().getSharedPreferences("herocorp", 0);
        rootView = inflater.inflate(R.layout.dse_editenquiry_fragment, container, false);
        fetch_data1();
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        customViewParams = new CustomViewParams(getActivity());

        RelativeLayout relativeLayout1 = (RelativeLayout) rootView.findViewById(R.id.relativelayout1);
        customViewParams.setMarginAndPadding(relativeLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, relativeLayout1.getParent());

        ScrollView scrollview = (ScrollView) rootView.findViewById(R.id.scrollview);
        customViewParams.setMarginAndPadding(scrollview, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, scrollview.getParent());

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        exchange_chkbox = (CheckBox) rootView.findViewById(R.id.exchange_chkbox);
        finance_chkbox = (CheckBox) rootView.findViewById(R.id.finance_chkbox);
        test_chkbox = (CheckBox) rootView.findViewById(R.id.testride_chkbox);

        remarks_et = (EditText) rootView.findViewById(R.id.edittext_remark);

        button_submit = (ImageView) rootView.findViewById(R.id.imageView_submit_addenquiry);

        nextfollowdate_btn = (Button) rootView.findViewById(R.id.nextfollowupdate_button);
        purchdate_btn = (Button) rootView.findViewById(R.id.exptpurchasedate_button);

        userAdapter = new Campaignadapter(getContext(), R.layout.dse_campaign_row, userArray);
        userAdapter1 = new Campaignadapter(getContext(), R.layout.dse_campaign_row, userArray);
        userList = (ListView) rootView.findViewById(R.id.list_campaign);

        spin_interested_model = (Spinner) rootView.findViewById(R.id.model_spinner);
        spin_existvehicle = (Spinner) rootView.findViewById(R.id.existvehicle_spinner);
        spin_existmake = (Spinner) rootView.findViewById(R.id.existmake_spinner);
        spin_existmodel = (Spinner) rootView.findViewById(R.id.existmodel_spinner);

        layout_existmake = (LinearLayout) rootView.findViewById(R.id.layout_existmake);
        layout_existmodel = (LinearLayout) rootView.findViewById(R.id.layout_existmodel);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        if (follow_date.equals("")) {
            c.add(Calendar.DATE, 3);  // number of days to add
            String dt1 = sdf.format(c.getTime());
            nextfollowdate_btn.setText(dt1);
        } else
            nextfollowdate_btn.setText(Dateformatter.dateformat3(follow_date));


        if (purch_date.equals("")) {
            c.add(Calendar.DATE, 6);  // number of days to add
            String dt1 = sdf.format(c.getTime());
            purchdate_btn.setText(dt1);
        } else
            purchdate_btn.setText(Dateformatter.dateformat3(purch_date));


        remarks_et.setText(remark);

        if (exchange.equals("Y"))
            exchange_chkbox.setChecked(true);

        if (finance.equals("Y"))
            finance_chkbox.setChecked(true);

        if (test.equals("Y"))
            test_chkbox.setChecked(false);

        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, vehicle_list);
        spin_existvehicle.setAdapter(at);
        spin_existvehicle.setSelection(at.getPosition(existvehicle));


      /* data = "{\"user_id\":\"" + username + "\"}";
        encryptuser1(URLConstants.BIKE_MAKE_MODEL, data, 2);*/
        fetch_pref();
        fetch_make_model();


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


        spin_interested_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* && !model.equalsIgnoreCase(parent.getItemAtPosition(position).toString())*/
                if (position != 0) {
                    model = parent.getItemAtPosition(position).toString();
                    JSONObject jsonparams = new JSONObject();
                    try {
                        jsonparams.put("user_id", user_id);
                        jsonparams.put("dealer_code", dealer_code);
                        Log.e("campaign_request",jsonparams.toString());
                        send_request(URLConstants.GET_CAMPAIGN_DATA, jsonparams.toString(), 0);
                        updateList();
                        chk_campaignid.clear();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               /* else
                    userList.setAdapter(null);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_existvehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                existvehicle = parent.getItemAtPosition(position).toString();
                if (position == 1) {
                    layout_existmake.setVisibility(View.VISIBLE);
                    layout_existmodel.setVisibility(View.VISIBLE);
                    ArrayAdapter<Bikemake> at1 = new ArrayAdapter<Bikemake>(getContext(), R.layout.spinner_textview2, arr_makelist);
                    spin_existmake.setAdapter(at1);
                } else {
                    reset();
                }

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
                }
            }
        });


        nextfollowdate_btn.setOnClickListener(this);
        purchdate_btn.setOnClickListener(this);
        button_submit.setOnClickListener(this);

    }

    private void updateList() {
        if (userAdapter1.getCount() == 0)
            userList.setAdapter(userAdapter);
        else
            userList.setAdapter(userAdapter1);

        setListViewHeightBasedOnChildren(userList);

    }

    public void onDestroy() {
        // Remove adapter reference from list
//        userList.setAdapter(null);
        super.onDestroy();
    }

    public void send_request(String url, String data, int flag) {
        if (NetConnections.isConnected(getContext())) {
            try {
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");

                if (flag == 0) {
                    new Fetch_campaign(url, urlParameters).execute();
                }
                if (flag == 1) {
                    ProgressDialog progress = new ProgressDialog(getContext());
                    if (page_flag == 0) {
                        db = new DatabaseHelper(getContext());
                        db.update_edit_followup(new Followup(firstname, lastname, mobile, age, gender, email, state, district, tehsil, village, model,
                                purchdate_btn.getText().toString().toUpperCase(), exchange, finance, existvehicle, remark, enquiry_id, nextfollowdate_btn.getText().toString().toUpperCase(), "0"));
                        new NetworkConnect1(url, urlParameters, progress, "Enquiry has been successfully submitted.", getContext(), 1).execute();
                    } else {
                        new NetworkConnect1(url, urlParameters, progress, "Enquiry has been successfully submitted.", getContext(), 5).execute();

                    }
                }
                if (flag == 2) {
                    // networkConnect = new NetworkConnect(url, newurlparams);
                    //  jsonparse_model(networkConnect.execute());
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
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


   /* public void jsonparse_campaign(String result) {
        try {
            // Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("campaign_data");
            userAdapter.clear();
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
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));

        }
    }*/

   /* public void jsonparse_model(String result) {
        try {
            String id = "";
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("make");
            arr_makelist.clear();
            arr_makelist.add(new Bikemake("", "--select--"));

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_makelist.add(new Bikemake(object.getString("id"), object.getString("make_name")));
                if (object.getString("make_name").equals("HERO MOTOCORP"))
                    id = object.getString("id");
            }

            jarray = jsono.getJSONArray("model");
            new Bikemodel("", "--select--");
            arr_modellist.add("--select--");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                new Bikemodel(object.getString("make_id"), object.getString("model_name"));
                if (object.getString("make_id").equals(id))
                    arr_modellist.add(object.getString("model_name"));
            }

            ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, arr_modellist);
            spin_interested_model.setAdapter(at1);
            spin_interested_model.setSelection(((ArrayAdapter<String>) spin_interested_model.getAdapter()).getPosition(model));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));

        }
    }*/

    //datepicker
    public String showdatepicker(final Button button, final String key) {
        // Get Current Date
        Date currentdate = new Date();
        SimpleDateFormat newFormatDate = new SimpleDateFormat(
                "dd-MMM-yy");
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

                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
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
  */
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

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.nextfollowupdate_button) {
            showdatepicker(nextfollowdate_btn, "follow_date");
        } else if (i == R.id.exptpurchasedate_button) {
            showdatepicker(purchdate_btn, "pur_date");
        } else if (i == R.id.imageView_submit_addenquiry) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            edit_enquiry();
        }
    }

    public void edit_enquiry() {
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

            fetch_data2();
            if (model.equals("") || existvehicle.equals("--select--"))
                Toast.makeText(getContext(), "Please fill all the details !!", Toast.LENGTH_SHORT).show();
            else if (followup_date.before(current_date) && page_flag != 0)
                Toast.makeText(getContext(), "Please check the followup date!!", Toast.LENGTH_SHORT).show();
            else if (expt_purc_date.before(followup_date))
                Toast.makeText(getContext(), "Please check the purchase date!!", Toast.LENGTH_SHORT).show();
            else if (state.equalsIgnoreCase("") || district.equalsIgnoreCase("") || tehsil.equalsIgnoreCase("") || village.equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Please fill the address details!!", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("key", key);
                jsonparams.put("mobile", mobile);
                jsonparams.put("email", email);
                jsonparams.put("fname", firstname);
                jsonparams.put("lname", lastname);
                jsonparams.put("age", age);
                jsonparams.put("gender", gender);
                jsonparams.put("address1", address1);
                jsonparams.put("address2", address2);
                jsonparams.put("pincode", pincode);
                jsonparams.put("exist_enq_id", enquiry_id);
                jsonparams.put("user_id", user_id);

                jsonparams.put("state", state);
                jsonparams.put("district", district);
                jsonparams.put("tehsil", tehsil);
                jsonparams.put("village", village);
                jsonparams.put("exchange_req", exchange);
                jsonparams.put("finance_req", finance);
                jsonparams.put("test_ride", test);
                jsonparams.put("remarks", remark);
                jsonparams.put("existVeh", existvehicle);
                jsonparams.put("existMake", existmake);
                jsonparams.put("existModel", existmodel);
                jsonparams.put("model_interested", model);
                jsonparams.put("fol_date", follow_date);
                jsonparams.put("exp_purchase_date", purch_date);
                jsonparams.put("dealer_code", dealer_code);

                //  String sel_campaign = "";
                for (int i = 0; i < chk_campaignid.size(); i++) {
                    jsonparams.put("campid" + (i + 1), chk_campaignid.get(i));
                }
                String json = jsonparams.toString().replace("\\/", "/");

                Log.e("edit_enquiry", json);
                send_request(URLConstants.ADD_ENQUIRY, json, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetch_data1() {
      /*  final Bundle bundle = getArguments();
        model = bundle.getString("model");
        purch_date = bundle.getString("pur_date");
        next_follow_date = bundle.getString("followdate");
        exchange = bundle.getString("exchange");
        finance = bundle.getString("finance");
        existvehicle = bundle.getString("vtype");
        remark = bundle.getString("comment");
        enquiry_id = bundle.getString("enquiryid");*/

       /* if (mypref.contains("firstname")) {
            firstname = mypref.getString("firstname", "");
        }
        if (mypref.contains("lastname")) {
            lastname = mypref.getString("lastname", "");
        }
        if (mypref.contains("mobile")) {
            mobile = mypref.getString("mobile", "");
        }
        if (mypref.contains("age")) {
            age = mypref.getString("age", "");
        }
        if (mypref.contains("email")) {
            email = mypref.getString("email", "");
        }
        if (mypref.contains("gender")) {
            gender = mypref.getString("gender", "");
        }
        if (mypref.contains("state")) {
            state = mypref.getString("state", "");
        }
        if (mypref.contains("district")) {
            district = mypref.getString("district", "");
        }
        if (mypref.contains("tehsil")) {
            tehsil = mypref.getString("tehsil", "");
        }
        if (mypref.contains("city")) {
            village = mypref.getString("city", "");
        }
        if (mypref.contains("address1")) {
            address1 = mypref.getString("address1", "");
        }
        if (mypref.contains("address2")) {
            address2 = mypref.getString("address2", "");
        }
        if (mypref.contains("pincode")) {
            pincode = mypref.getString("pincode", "");
        }*/

        if (mypref.contains("model")) {
            model = mypref.getString("model", "");
        }
        if (mypref.contains("purch_date")) {
            purch_date = mypref.getString("purch_date", "");
        }
        if (mypref.contains("follow_date")) {
            follow_date = mypref.getString("follow_date", "");
        }
        if (mypref.contains("comment")) {
            remark = mypref.getString("comment", "");
        }
        if (mypref.contains("exchange")) {
            exchange = mypref.getString("exchange", "");
        }
        if (mypref.contains("finance")) {
            finance = mypref.getString("finance", "");
        }
        if (mypref.contains("existvehicle")) {
            existvehicle = mypref.getString("existvehicle", "");
        }
        if (mypref.contains("enquiry_id")) {
            enquiry_id = mypref.getString("enquiry_id", "");
        }
        if (exchange.equals("Y") || finance.equals("Y"))
            flag++;
        if (purch_date.equalsIgnoreCase("null"))
            purch_date = "";
        if (remark.equalsIgnoreCase("null"))
            remark = "";
        if (follow_date.equalsIgnoreCase("null"))
            follow_date = "";

    }

    public void fetch_data2() {
      /*  final Bundle bundle = getArguments();
        model = bundle.getString("model");
        purch_date = bundle.getString("pur_date");
        next_follow_date = bundle.getString("followdate");
        exchange = bundle.getString("exchange");
        finance = bundle.getString("finance");
        existvehicle = bundle.getString("vtype");
        remark = bundle.getString("comment");
        enquiry_id = bundle.getString("enquiryid");*/

        if (mypref.contains("firstname")) {
            firstname = mypref.getString("firstname", "");
        }
        if (mypref.contains("lastname")) {
            lastname = mypref.getString("lastname", "");
        }
        if (mypref.contains("mobile")) {
            mobile = mypref.getString("mobile", "");
        }
        if (mypref.contains("age")) {
            age = mypref.getString("age", "");
        }
        if (mypref.contains("email")) {
            email = mypref.getString("email", "");
        }
        if (mypref.contains("gender")) {
            gender = mypref.getString("gender", "");
        }
        if (mypref.contains("state")) {
            state = mypref.getString("state", "");
        }
        if (mypref.contains("district")) {
            district = mypref.getString("district", "");
        }
        if (mypref.contains("tehsil")) {
            tehsil = mypref.getString("tehsil", "");
        }
        if (mypref.contains("city")) {
            village = mypref.getString("city", "");
        }
        if (mypref.contains("address1")) {
            address1 = mypref.getString("address1", "");
        }
        if (mypref.contains("address2")) {
            address2 = mypref.getString("address2", "");
        }
        if (mypref.contains("pincode")) {
            pincode = mypref.getString("pincode", "");
        }
        if (mypref.contains("key")) {
            key = mypref.getString("key", "");
        }
        if (mypref.contains("page_flag")) {
            page_flag = mypref.getInt("page_flag", 0);
        }
        if (email.equalsIgnoreCase("null"))
            email = "";

       /* if (mypref.contains("model")) {
            model = mypref.getString("model", "");
        }
        if (mypref.contains("purch_date")) {
            purch_date = mypref.getString("purch_date", "");
        }
        if (mypref.contains("follow_date")) {
            follow_date = mypref.getString("follow_date", "");
        }
        if (mypref.contains("comment")) {
            remark = mypref.getString("comment", "");
        }
        if (mypref.contains("exchange")) {
            exchange = mypref.getString("exchange", "");
        }
        if (mypref.contains("finance")) {
            finance = mypref.getString("finance", "");
        }
        if (mypref.contains("existvehicle")) {
            existvehicle = mypref.getString("existvehicle", "");
        }
        if (mypref.contains("enquiryid")) {
            enquiry_id = mypref.getString("enquiryid", "");
        }
        if (exchange.equals("Y") || finance.equals("Y"))
            flag = 1;
*/
    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(getContext());
        dealer_code = PreferenceUtil.get_DealerCode(getContext());
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
            spin_interested_model.setAdapter(at1);
            spin_interested_model.setSelection(((ArrayAdapter<String>) spin_interested_model.getAdapter()).getPosition(model));

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
                Log.e("campaign_response", s);
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
                e.printStackTrace();
               // Toast.makeText(getContext(), "Check your Connection !!" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }


}

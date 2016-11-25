package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.herocorp.ui.activities.DSEapp.adapter.Campaignadapter;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Campaign;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

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
    NetworkConnect networkConnect;
    String encryptdata, encryptuser;
    int flag = 0;
    private int mYear, mMonth, mDay;

    String date, follow_date, purch_date, remark = "", exchange = "N", finance = "N", test = "", model = "", existvehicle = "", existmake = "", existmodel = "";

    String firstname, lastname, mobile, email, age, gender, state, district, tehsil, village, address1, address2, pincode;

    String username = "ROBINK11610", dealercode = "11610", key;

    Campaignadapter userAdapter;
    ArrayList<Campaign> userArray = new ArrayList<Campaign>();
    ArrayList<String> arr_modellist = new ArrayList<String>();
    ArrayList<Bikemake> arr_makelist = new ArrayList<Bikemake>();
    ArrayList<String> arr_modellist1 = new ArrayList<String>();
    ArrayList<String> chk_campaignid = new ArrayList<String>();

    ListView userList;

    String[] vehicle_list = {"--select--", "Two Wheeler",
            "Four Wheeler",
            "First Time Buyer"};

    String data;

    SharedPreferences mypref;
    SharedPreferences.Editor edit;

    private SharedPreferences sharedPreferences;
    String user_id, dealer_code;

    private String appVersion;
    // private String deviceImei = "911441757449230";
    private String deviceImei = "911441757449230";
    private String userCode, uuid = "0";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_addenquiry_fragment, container, false);

        initView(rootView);
        fetch_data();

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

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
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
        userList = (ListView) rootView.findViewById(R.id.list_campaign);


        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, vehicle_list);
        exist_vehicle_spinner.setAdapter(at);


        data = "{\"user_id\":\"ROBINK11610\"}";
        encryptuser1(URLConstants.BIKE_MAKE_MODEL, data, 2);

        data = "{\"user_id\":\"" + username + "\",\"dealer_code\":\"" + dealercode + "\"}";
        encryptuser1(URLConstants.GET_CAMPAIGN_DATA, data, 0);

        model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    model = parent.getItemAtPosition(position).toString();
                    updateList();
                    chk_campaignid.clear();

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
                if (position == 1) {
                    existvehicle = parent.getItemAtPosition(position).toString();
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
                    flag = 1;
                } else {
                    exchange = "N";
                    flag = 0;
                }
            }
        });
        finance_chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finance_chkbox.isChecked()) {
                    finance = "Y";
                    flag = 1;
                } else {
                    finance = "N";
                    flag = 0;
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
                    Toast.makeText(getContext(), chk_campaignid.get(0), Toast.LENGTH_SHORT).show();
                }
            }
        });


     /*   PackageManager manager = getActivity().getPackageManager();
        PackageInfo info = null;
        try {


            TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);


            info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            appVersion = info.versionName;
            deviceImei = telephonyManager.getDeviceId();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/


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
           /* Toast.makeText(getContext(), "Enquiry Under testing" + chk_campaignid.size(), Toast.LENGTH_SHORT).show();*/
            addenquiry();
        }
    }

    public String showdatepicker(final Button button) {
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
                        datechange(date);
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
            format = new SimpleDateFormat("dd-MMM-yyyy");
            follow_date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void encryptuser1(String url, String data, int flag) {
        if (NetConnections.isConnected(getContext())) {
            try {
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptdata = result.replace("\\/", "/");
                String newurlparams = "data=" + URLEncoder.encode(encryptdata, "UTF-8");
                if (flag == 0) {
                    networkConnect = new NetworkConnect(url, newurlparams);
                    jsonparse_campaign(networkConnect.execute());
                }
                if (flag == 1) {
                    ProgressDialog progress = new ProgressDialog(getContext());
                    new NetworkConnect1(url, newurlparams, progress, "Enquiry has been successfully submitted.", getContext(), 2).execute();
                }
                if (flag == 2) {
                    networkConnect = new NetworkConnect(url, newurlparams);
                    jsonparse_model(networkConnect.execute());
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }

    public void jsonparse_model(String result) {
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
            model_spinner.setAdapter(at1);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));

        }
    }

    public void jsonparse_campaign(String result) {
        try {
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
                String model = object.getString("model");


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
                        model
                ));
                userAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));

        }
    }

    private void updateList() {
        userList.setAdapter(userAdapter);
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
            follow_date = nextfollowdate_btn.getText().toString();
            purch_date = purchdate_btn.getText().toString();
            key = random_key(7);

            if (model.equals("") || flag == 0 || existvehicle.equals(""))
                Toast.makeText(getContext(), "Please fill all the details !!", Toast.LENGTH_LONG).show();
            else {
                String json = "{\"mobile\":\"" + mobile + "\",\"email\":\"" + email + "\",\"fname\":\"" + firstname + "\",\"lname\":\"" + lastname +
                        "\",\"age\":\"" + age + "\",\"gender\":\"" + gender + "\",\"address1\":\"" + address1 + "\",\"address2\":\"" + address2 +
                        "\",\"pincode\":\"" + pincode + "\",\"fol_date\":\"" + follow_date + "\",\"exp_purchase_date\":\"" + purch_date +
                        "\",\"model_interested\":\"" + model + "\",\"exchange_req\":\"" + exchange + "\",\"finance_req\":\"" + finance +
                        "\",\"test_ride\":\"" + test + "\",\"remarks\":\"" + remark + "\",\"existVeh\":\"" + existvehicle +
                        "\",\"user_id\":\"" + username + "\",\"key\":\"" + key + "\",\"state\":\"" + state + "\",\"district\":\"" + district +
                        "\",\"tehsil\":\"" + tehsil + "\",\"village\":\"" + village + "\",\"existMake\":\"" + existmake + "\",\"existModel\":\"" + existmodel + "\",\"dealer_code\":\"" + dealercode +
                        "\"";


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
                jsonparams.put("user_id", username);
                jsonparams.put("key", key);
                jsonparams.put("state", state);
                jsonparams.put("district", district);
                jsonparams.put("tehsil", tehsil);
                jsonparams.put("village", village);
                jsonparams.put("existMake", existmake);
                jsonparams.put("existModel", existmodel);
                jsonparams.put("dealer_code", dealercode);

                String sel_campaign = "";
                for (int i = 0; i < chk_campaignid.size(); i++) {
                    jsonparams.put("campid" + (i + 1), chk_campaignid.get(i));
                    sel_campaign += ",\"campid" + (i + 1) + ":\"" + chk_campaignid.get(i) + "\"";
                }

                sel_campaign += "}";

                data = json + sel_campaign;

                edit.putString("key", key);
                edit.commit();
                Log.e("add_enquiry", jsonparams.toString());
                // Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();
                encryptuser1(URLConstants.ADD_ENQUIRY, jsonparams.toString(), 1);
            }

        } catch (Exception e) {
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
    }

    public void fetch_pref() {
        sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        if (sharedPreferences.contains("username"))
            user_id = sharedPreferences.getString("username", null);
        if (sharedPreferences.contains("dealercode"))
            dealer_code = sharedPreferences.getString("dealercode", null);
    }
}
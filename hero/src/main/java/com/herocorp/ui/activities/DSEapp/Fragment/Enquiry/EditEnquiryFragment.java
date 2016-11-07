package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.activities.DSEapp.adapter.Campaignadapter;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Campaign;
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
            "Four Wheeler",
            "First Time Buyer"};


    Campaignadapter userAdapter;
    ArrayList<Campaign> userArray = new ArrayList<Campaign>();
    ArrayList<String> arr_modellist = new ArrayList<String>();
    ArrayList<String> arr_modellist1 = new ArrayList<String>();
    ArrayList<Bikemake> arr_makelist = new ArrayList<Bikemake>();
    ArrayList<String> chk_campaignid = new ArrayList<String>();

    String username = "ROBINK11610", dealercode = "11610", key = "", enquiry_id = "";
    int flag = 0;

    private int mYear, mMonth, mDay;
    String date, follow_date = "", next_follow_date = "", purch_date = "", remark = "", exchange = "", finance = "", test = "", model = "", existvehicle = "", existmake = "", existmodel = "";
    String firstname = "", lastname = "", email = "", mobile = "", age = "", gender = "", state = "", district = "", tehsil = "", village = "", address1 = "", address2 = "", pincode = "";

    String encryptdata, data;

    NetworkConnect networkConnect;

    SharedPreferences mypref;

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
        fetch_data();
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        customViewParams = new CustomViewParams(getActivity());

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        exchange_chkbox = (CheckBox) rootView.findViewById(R.id.exchange_chkbox);
        finance_chkbox = (CheckBox) rootView.findViewById(R.id.finance_chkbox);
        test_chkbox = (CheckBox) rootView.findViewById(R.id.testride_chkbox);

        remarks_et = (EditText) rootView.findViewById(R.id.edittext_remark);

        button_submit = (ImageView) rootView.findViewById(R.id.imageView_submit_addenquiry);

        nextfollowdate_btn = (Button) rootView.findViewById(R.id.nextfollowupdate_button);
        purchdate_btn = (Button) rootView.findViewById(R.id.exptpurchasedate_button);


        userAdapter = new Campaignadapter(getContext(), R.layout.dse_campaign_row, userArray);
        userList = (ListView) rootView.findViewById(R.id.list_campaign);

        spin_interested_model = (Spinner) rootView.findViewById(R.id.model_spinner);
        spin_existvehicle = (Spinner) rootView.findViewById(R.id.existvehicle_spinner);
        spin_existmake = (Spinner) rootView.findViewById(R.id.existmake_spinner);
        spin_existmodel = (Spinner) rootView.findViewById(R.id.existmodel_spinner);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        Calendar c = Calendar.getInstance();
        if (follow_date.equals("")) {
            c.add(Calendar.DATE, 3);  // number of days to add
            String dt1 = sdf.format(c.getTime());
            nextfollowdate_btn.setText(dt1);
        } else
            nextfollowdate_btn.setText(follow_date);


        if (purch_date.equals("")) {
            c.add(Calendar.DATE, 6);  // number of days to add
            String dt1 = sdf.format(c.getTime());
            purchdate_btn.setText(dt1);
        } else
            purchdate_btn.setText(purch_date);


        remarks_et.setText(remark);

        if (exchange.equals("Y"))
            exchange_chkbox.setChecked(true);

        if (finance.equals("Y"))
            finance_chkbox.setChecked(true);

        if (test.equals("Y"))
            test_chkbox.setChecked(true);

        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, vehicle_list);
        spin_existvehicle.setAdapter(at);
        spin_existvehicle.setSelection(at.getPosition(existvehicle));


        data = "{\"user_id\":\"" + username + "\"}";
        encryptuser1(URLConstants.BIKE_MAKE_MODEL, data, 2);


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


        spin_interested_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    model = parent.getItemAtPosition(position).toString();
                    data = "{\"user_id\":\"" + username + "\",\"dealer_code\":\"" + dealercode + "\"}";
                    encryptuser1(URLConstants.GET_CAMPAIGN_DATA, data, 0);
                    updateList();
                    chk_campaignid.clear();
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
        userList.setAdapter(userAdapter);
        setListViewHeightBasedOnChildren(userList);

    }

    public void onDestroy() {
        // Remove adapter reference from list
//        userList.setAdapter(null);
        super.onDestroy();
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
                    new NetworkConnect1(url, newurlparams, progress, "Enquiry has been successfully submitted.", getContext(), 1).execute();
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

    public void jsonparse_campaign(String result) {
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
            spin_interested_model.setAdapter(at1);
            spin_interested_model.setSelection(((ArrayAdapter<String>) spin_interested_model.getAdapter()).getPosition(model));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT));

        }
    }

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

    public void reset() {
        ArrayList<String> arr_reset = new ArrayList<String>();
        arr_reset.clear();
        arr_reset.add("--select--");
        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview2, arr_reset);
        spin_existmake.setAdapter(at);
        spin_existmodel.setAdapter(at);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.nextfollowupdate_button) {
            showdatepicker(nextfollowdate_btn, "follow_date");
        } else if (i == R.id.exptpurchasedate_button) {
            showdatepicker(purchdate_btn, "pur_date");
        } else if (i == R.id.imageView_submit_addenquiry) {
            edit_enquiry();
        }
    }

    public void edit_enquiry() {
        try {
            remark = remarks_et.getText().toString();
            follow_date = nextfollowdate_btn.getText().toString();
            purch_date = purchdate_btn.getText().toString();

            if (model.equals("") || flag == 0 || existvehicle.equals(""))
                Toast.makeText(getContext(), "Please fill all the details !!" + model + flag + existvehicle, Toast.LENGTH_LONG).show();
            else {
                String json = "{\"key\":\"" + key + "\",\"mobile\":\"" + mobile + "\",\"email\":\"" + email + "\",\"fname\":\"" + firstname + "\",\"lname\":\"" + lastname +
                        "\",\"age\":\"" + age + "\",\"gender\":\"" + gender + "\",\"address1\":\"" + address1 + "\",\"address2\":\"" + address2 + "\",\"pincode\":\"" + pincode +
                        "\",\"exist_enq_id\":\"" + enquiry_id + "\",\"user_id\":\"" + username + "\",\"state\":\"" + state + "\",\"district\":\"" + district +
                        "\",\"tehsil\":\"" + tehsil + "\",\"village\":\"" + village + "\",\"exchange_req\":\"" + exchange + "\",\"finance_req\":\"" + finance +
                        "\",\"test_ride\":\"" + test + "\",\"remarks\":\"" + remark + "\",\"existVeh\":\"" + existvehicle +
                        "\",\"existMake\":\"" + existmake + "\",\"existModel\":\"" + existmodel + "\",\"model_interested\":\"" + model + "\",\"fol_date\":\"" + follow_date + "\",\"exp_purchase_date\":\"" + purch_date + "\",\"dealer_code\":\"" + dealercode +
                        "\"";

                String sel_campaign = "";
                for (int i = 0; i < chk_campaignid.size(); i++) {
                    sel_campaign += ",\"campid" + (i + 1) + ":\"" + chk_campaignid.get(i) + "\"";
                }
                sel_campaign += "}";

                data = json + sel_campaign;

                //   Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();

                encryptuser1(URLConstants.ADD_ENQUIRY, data, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fetch_data() {
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
        if (mypref.contains("enquiryid")) {
            enquiry_id = mypref.getString("enquiryid", "");
        }
        if (exchange.equals("Y") || finance.equals("Y"))
            flag = 1;



    }

}

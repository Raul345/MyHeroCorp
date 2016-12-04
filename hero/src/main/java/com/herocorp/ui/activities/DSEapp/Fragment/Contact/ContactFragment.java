package com.herocorp.ui.activities.DSEapp.Fragment.Contact;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.PersonalinfoFragment;
import com.herocorp.ui.activities.DSEapp.adapter.EnquiryContactadapter;
import com.herocorp.ui.activities.DSEapp.adapter.VinContactadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.CampaignContact;
import com.herocorp.ui.activities.DSEapp.models.Contact;
import com.herocorp.ui.activities.DSEapp.models.EnquiryContact;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.activities.DSEapp.models.LocalEnquiry;
import com.herocorp.ui.activities.DSEapp.models.Pendingorder;
import com.herocorp.ui.activities.DSEapp.models.VinnContact;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsawh on 12-Sep-16.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    Button addenquiry_button;
    RelativeLayout addlayout;
    TextView enquirytitle, vintitle;
    ListView enquirycontacts, vincontacts;
    EnquiryContactadapter enquiryContactadapter;
    ArrayList<EnquiryContact> enquirycontactarray = new ArrayList<EnquiryContact>();

    VinContactadapter vinContactadapter;
    ArrayList<Contact> vincontactarray = new ArrayList<Contact>();

    Fragment f;
    DatabaseHelper db;

    String fst_name1, last_name1, cell_ph_num1, age1, gender1, email_addr1, state1, district1, tehsil1, city1, x_con_seq_no_1, x_model_interested,
            expctd_dt_purchase, x_exchange_required, x_finance_required, existing_vehicle, followup_comments, enquiry_id,
            x_test_ride_req, enquiry_entry_date, addr1, addr_line_2_1, make_cd, model_cd1, dealer_bu_id, follow_date;
    String fst_name2, last_name2, cell_ph_num2, age2, gender2, email_addr2, state2, district2, tehsil2, city2, addr2, addr_line_2_2,
            x_con_seq_no_2, x_hmgl_card_num, last_srvc_dt, next_srvc_due_dt, ins_policy_num, x_ins_exp_dt, model_cd2, serial_num,
            primary_dealer_name, attrib_42, prod_attrib02_CD, desc_text, first_sale_dt, ins_policy_co, x_hmgl_card_points;
    String row_id, camp_name, opty_id;

    String phone_no, reg_no, dealer_code, user_id;
    String encryptdata;
    ProgressBar progressBar;

    NetworkConnect networkConnect;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_contact_fragment, container, false);

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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{190, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        RelativeLayout topLayout1 = (RelativeLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        addlayout = (RelativeLayout) rootView.findViewById(R.id.top_layout2);
        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        enquiryContactadapter = new EnquiryContactadapter(getContext(), R.layout.dse_enquirycontact_row, enquirycontactarray);
        enquirycontacts = (ListView) rootView.findViewById(R.id.list_enquirycontacts);

        enquirytitle = (TextView) rootView.findViewById(R.id.enquirytitle);

        vintitle = (TextView) rootView.findViewById(R.id.vintitle);


        vinContactadapter = new VinContactadapter(getContext(), R.layout.dse_vincontact_row, vincontactarray);
        vincontacts = (ListView) rootView.findViewById(R.id.list_vincontacts);

        addenquiry_button = (Button) rootView.findViewById(R.id.addenquiry_button);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);


        fetch_pref();
        fetch_data();

        enquirycontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnquiryContact data = enquiryContactadapter.getItem(position);
                VinnContact detail = new VinnContact();

                ArrayList vinarray = detail.getvindetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList variantarray = detail.getvariantdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList dealerarray = detail.getdealerdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList colourarray = detail.getcolourdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList modelarray = detail.getmodeldetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList datearray = detail.getpur_datedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList descarray = detail.getskudetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());


                ArrayList cardnoarray = detail.getcardnodetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList currentpointsarray = detail.getCurrentpointsdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList lastservicearray = detail.getLastservicedatedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList nextservicearray = detail.getNextservicedatedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList expirydatearray = detail.getExpirydatedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList policynoarray = detail.getPolicynodetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList insurancecoarray = detail.getInsurancecodetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());


                Bundle bundle = new Bundle();
                bundle.putString("fst_name1", data.getFst_name());
                bundle.putString("last_name1", data.getLast_name());
                bundle.putString("cell_ph_num1", data.getCell_ph_num());
                bundle.putString("age1", data.getAge());
                bundle.putString("gender1", data.getGender());
                bundle.putString("email_addr1", data.getEmail_addr());
                bundle.putString("state1", data.getState());
                bundle.putString("district1", data.getDistrict());
                bundle.putString("tehsil1", data.getTehsil());
                bundle.putString("city1", data.getCity());
                bundle.putString("x_con_seq_no_1", data.getX_con_seq_no());
               /* bundle.putString("x_model_interested", data.getX_model_interested());
                bundle.putString("expctd_dt_purchase", data.getExpctd_dt_purchase());
                bundle.putString("enquiry_entry_date", data.getEnquiry_entry_date());
                bundle.putString("follow_date", data.getFollow_date());*/

                bundle.putString("x_exchange_required", data.getX_exchange_required());
                bundle.putString("x_finance_required", data.getX_finance_required());
                bundle.putString("existing_vehicle", data.getExisting_vehicle());
                bundle.putString("followup_comments", data.getFollowup_comments());
                bundle.putString("enquiry_id", data.getEnquiry_id());
                bundle.putString(" x_test_ride_req", data.getX_test_ride_req());

                bundle.putString("addr1", data.getAddr());
                bundle.putString("addr_line_2_1", data.getAddr_line_2());
                bundle.putString("make_cd", data.getMake_cd());
                bundle.putString("model_cd1", data.getModel_cd());
                bundle.putString("dealer_bu_id", data.getDealer_bu_id());

                bundle.putStringArrayList("vinarray", vinarray);
                bundle.putStringArrayList("variantarray", variantarray);
                bundle.putStringArrayList("dealerarray", dealerarray);
                bundle.putStringArrayList("colourarray", colourarray);
                bundle.putStringArrayList("modelarray", modelarray);
                bundle.putStringArrayList("datearray", datearray);
                bundle.putStringArrayList("descarray", descarray);

                bundle.putStringArrayList("cardnoarray", cardnoarray);
                bundle.putStringArrayList("currentpointsarray", currentpointsarray);
                bundle.putStringArrayList("lastservicearray", lastservicearray);
                bundle.putStringArrayList("nextservicearray", nextservicearray);
                bundle.putStringArrayList("expirydatearray", expirydatearray);
                bundle.putStringArrayList("policynoarray", policynoarray);
                bundle.putStringArrayList("insurancecoarray", insurancecoarray);
                send_id(bundle);

  /*              for (int i = 0; i < vinarray.size(); i++) {
                    Toast.makeText(getContext(), vinarray.get(i).toString() + variantarray.get(i).toString() +
                            colourarray.get(i).toString() + modelarray.get(i).toString() +
                            dealerarray.get(i).toString() + datearray.get(i).toString() +
                            descarray.get(i).toString(), Toast.LENGTH_SHORT).show();
                }
   */
                f = new ContactDetailFragment();
                f.setArguments(bundle);
                transaction(f);
            }
        });

        vincontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact data = vinContactadapter.getItem(position);
                VinnContact detail = new VinnContact();

                ArrayList vinarray = detail.getvindetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList variantarray = detail.getvariantdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList dealerarray = detail.getdealerdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList colourarray = detail.getcolourdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList modelarray = detail.getmodeldetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList datearray = detail.getpur_datedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList descarray = detail.getskudetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());

                ArrayList cardnoarray = detail.getcardnodetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList currentpointsarray = detail.getCurrentpointsdetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList lastservicearray = detail.getLastservicedatedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList nextservicearray = detail.getNextservicedatedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList expirydatearray = detail.getExpirydatedetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList policynoarray = detail.getPolicynodetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                ArrayList insurancecoarray = detail.getInsurancecodetail(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());


                EnquiryContact enq = new EnquiryContact();
                String enquiryid = enq.getEnquiry_id(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());

                String model = enq.getModel_cd(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());

                String enquirydate = enq.getEnquiry_entry_date(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                String purchasedate = enq.getExpctd_dt_purchase(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());
                String followupdate = enq.getFollow_date(data.getFst_name(), data.getLast_name(), data.getCell_ph_num()
                        , data.getState(), data.getDistrict());

              /*  for (int i = 0; i < vinarray.size(); i++) {
                    Toast.makeText(getContext(), model + vinarray.get(i).toString() + variantarray.get(i).toString() +
                            colourarray.get(i).toString()+policynoarray.get(i).toString(), Toast.LENGTH_SHORT).show();
                }
*/

                Bundle bundle = new Bundle();
                bundle.putString("fst_name2", data.getFst_name());
                bundle.putString("last_name2", data.getLast_name());
                bundle.putString("cell_ph_num2", data.getCell_ph_num());
                bundle.putString("age2", data.getAge());
                bundle.putString("gender2", data.getGender());
                bundle.putString("email_addr2", data.getEmail_addr());
                bundle.putString("state2", data.getState());
                bundle.putString("district2", data.getDistrict());
                bundle.putString("tehsil2", data.getTehsil());
                bundle.putString("city2", data.getCity());
                bundle.putString("x_con_seq_no_2", data.getX_con_seq_no());

                bundle.putString("enquiry_id", enquiryid);
              /* bundle.putString("x_model_interested", model);
                bundle.putString("enquiry_entry_date", enquirydate);
                bundle.putString("expctd_dt_purchase", purchasedate);
                bundle.putString("follow_date", followupdate);
*/
                bundle.putStringArrayList("vinarray", vinarray);
                bundle.putStringArrayList("variantarray", variantarray);
                bundle.putStringArrayList("dealerarray", dealerarray);
                bundle.putStringArrayList("colourarray", colourarray);
                bundle.putStringArrayList("modelarray", modelarray);
                bundle.putStringArrayList("datearray", datearray);
                bundle.putStringArrayList("descarray", descarray);

                bundle.putStringArrayList("cardnoarray", cardnoarray);
                bundle.putStringArrayList("currentpointsarray", currentpointsarray);
                bundle.putStringArrayList("lastservicearray", lastservicearray);
                bundle.putStringArrayList("nextservicearray", nextservicearray);
                bundle.putStringArrayList("expirydatearray", expirydatearray);
                bundle.putStringArrayList("policynoarray", policynoarray);
                bundle.putStringArrayList("insurancecoarray", insurancecoarray);
                send_id(bundle);


                f = new ContactDetailFragment();
                f.setArguments(bundle);
                transaction(f);
            }
        });
        addenquiry_button.setOnClickListener(this);
        menu.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.addenquiry_button) {
            save_data();
            Bundle bundle = new Bundle();
            bundle.putString("phoneno", phone_no);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = new PersonalinfoFragment();
            f.setArguments(bundle);
            // ft.addToBackStack(null);
            ft.replace(R.id.content_contact, f);
            ft.commit();
        }
    }

    public void fetch_data() {
        Bundle bundle = this.getArguments();
        phone_no = bundle.getString("phone_no");
        reg_no = bundle.getString("reg_no");
        try {
            JSONObject jsonparams = new JSONObject();
            jsonparams.put("user_id", user_id);
            jsonparams.put("dealer_code", dealer_code);
            jsonparams.put("phn_no", phone_no);
            jsonparams.put("reg_no", reg_no);
            Log.e("submit_data:", jsonparams.toString());
            new Contactlist(jsonparams.toString()).execute();
            //    encryptuser(jsonparams.toString(), URLConstants.FETCH_CONTACT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

  /*  public void encryptuser(String data, String url) {
        if (NetConnections.isConnected(getContext())) {
            try {
                Log.e("submit_contact", data);
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptdata = result.replace("\\/", "/");
                String newurlparams = "data=" + URLEncoder.encode(encryptdata, "UTF-8");
                NetworkConnect networkConnect = new NetworkConnect(url, newurlparams);

                jsonparse_contact(networkConnect.execute());

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }

    public void jsonparse_contact(String result) {
        try {
            DatabaseHelper db = new DatabaseHelper(getContext());
            db.clearcontactfollowup_table();
            Log.e("contact_response", result);
            JSONObject jsono = new JSONObject(result);
            if (jsono.has("enquiry")) {
                JSONArray jarray = jsono.getJSONArray("enquiry");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    fst_name1 = object.getString("FST_NAME");
                    last_name1 = object.getString("LAST_NAME");
                    cell_ph_num1 = object.getString("CELL_PH_NUM");
                    age1 = object.getString("AGE");
                    gender1 = object.getString("GENDER");
                    email_addr1 = object.getString("EMAIL_ADDR");
                    state1 = object.getString("STATE");
                    district1 = object.getString("DISTRICT");
                    tehsil1 = object.getString("TEHSIL");
                    city1 = object.getString("CITY");
                    x_con_seq_no_1 = object.getString("X_CON_SEQ_NUM");
                    x_model_interested = object.getString("X_MODEL_INTERESTED");
                    expctd_dt_purchase = object.getString("EXPCTD_DT_PURCHASE");
                    x_exchange_required = object.getString("X_EXCHANGE_REQUIRED");
                    x_finance_required = object.getString("X_FINANCE_REQUIRED");
                    existing_vehicle = object.getString("EXISTING_VEHICLE");
                    followup_comments = object.getString("FOLLOWUP_COMMENTS");
                    enquiry_id = object.getString("ENQUIRY_ID");
                    x_test_ride_req = object.getString("X_TEST_RIDE_REQ");
                    enquiry_entry_date = object.getString("ENQUIRY_ENTRY_DATE");
                    addr1 = object.getString("ADDR");
                    addr_line_2_1 = object.getString("ADDR_LINE_2");
                    make_cd = object.getString("MAKE_CD");
                    model_cd1 = object.getString("MODEL_CD");
                    dealer_bu_id = object.getString("DEALER_BU_ID");
                    follow_date = object.getString("FOLLOW_DATE");
                    try {

                        db.addcontactfollowup(new Followup(object.getString("X_MODEL_INTERESTED"), object.getString("ENQUIRY_ENTRY_DATE"),
                                object.getString("EXPCTD_DT_PURCHASE"), object.getString("FOLLOW_DATE"), object.getString("ENQUIRY_ID"), object.getString("FOLLOWUP_COMMENTS"), "0"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    enquiryContactadapter.add(new EnquiryContact(fst_name1, last_name1, cell_ph_num1, age1, gender1, email_addr1, state1, district1, tehsil1, city1, x_con_seq_no_1, x_model_interested,
                            expctd_dt_purchase, x_exchange_required, x_finance_required, existing_vehicle, followup_comments, enquiry_id,
                            x_test_ride_req, enquiry_entry_date, addr1, addr_line_2_1, make_cd, model_cd1, dealer_bu_id, follow_date));

                    enquiryContactadapter.notifyDataSetChanged();
                }
            }

            ArrayList<String> firstname = new ArrayList<String>();
            ArrayList<String> lastname = new ArrayList<String>();
            ArrayList<String> mobile = new ArrayList<String>();
            ArrayList<String> state = new ArrayList<String>();
            ArrayList<String> district = new ArrayList<String>();
            int check = 0;
            firstname.add("");
            lastname.add("");
            mobile.add("");
            state.add("");
            district.add("");

            if (jsono.has("vin")) {
                JSONArray jarray = jsono.getJSONArray("vin");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    fst_name2 = object.getString("FST_NAME");
                    last_name2 = object.getString("LAST_NAME");
                    cell_ph_num2 = object.getString("CELL_PH_NUM");
                    age2 = object.getString("AGE");
                    gender2 = object.getString("GENDER");
                    email_addr2 = object.getString("EMAIL_ADDR");
                    state2 = object.getString("STATE");
                    district2 = object.getString("DISTRICT");
                    tehsil2 = object.getString("TEHSIL");
                    city2 = object.getString("CITY");
                    x_con_seq_no_2 = object.getString("X_CON_SEQ_NUM");
                    x_hmgl_card_num = object.getString("X_HMGL_CARD_NUM");
                    last_srvc_dt = object.getString("LAST_SRVC_DT");
                    next_srvc_due_dt = object.getString("NEXT_SERVICE_DUE_DT");
                    ins_policy_num = object.getString("INS_POLICY_NUM");
                    x_ins_exp_dt = object.getString("X_INS_EXP_DT");
                    model_cd2 = object.getString("MODEL_CD");
                    serial_num = object.getString("SERIAL_NUM");
                    primary_dealer_name = object.getString("PRIMARY_DEALER_NAME");
                    attrib_42 = object.getString("ATTRIB_42");
                    prod_attrib02_CD = object.getString("PROD_ATTRIB02_CD");
                    desc_text = object.getString("DESC_TEXT");
                    first_sale_dt = object.getString("FIRST_SALE_DT");
                    ins_policy_co = object.getString("INS_POLICY_CO");
                    x_hmgl_card_points = object.getString("X_HMGL_CARD_POINTS");
                    addr2 = object.getString("ADDR");
                    addr_line_2_2 = object.getString("ADDR_LINE_2");
                    new VinnContact(fst_name2, last_name2, cell_ph_num2, gender2, email_addr2, state2, district2, tehsil2, city2,
                            x_con_seq_no_2, x_hmgl_card_num, last_srvc_dt, next_srvc_due_dt, ins_policy_num, x_ins_exp_dt, model_cd2, serial_num,
                            primary_dealer_name, attrib_42, prod_attrib02_CD, desc_text, first_sale_dt, ins_policy_co, x_hmgl_card_points, age2, addr2, addr_line_2_2);


                    for (int j = 0; j < firstname.size(); j++) {
                        if (firstname.get(j).equals(fst_name2) && lastname.get(j).equals(last_name2)
                                && mobile.get(j).equals(cell_ph_num2) && state.get(j).equals(state2) && district.get(j).equals(district2)) {
                            check = 0;
                        } else {
                            check = 1;
                        }
                    }
                    if (check == 1) {
                        vinContactadapter.add(new Contact(fst_name2, last_name2, cell_ph_num2, age2, gender2, email_addr2, state2, district2, tehsil2, city2, x_con_seq_no_2));
                        vinContactadapter.notifyDataSetChanged();
                        firstname.add(fst_name2);
                        lastname.add(last_name2);
                        mobile.add(cell_ph_num2);
                        state.add(state2);
                        district.add(district2);
                    }

                }
            }
            if (jsono.has("campaign")) {
                JSONArray jarray = jsono.getJSONArray("campaign");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    row_id = object.getString("ROW_ID");
                    camp_name = object.getString("CAMPAIGN_NAME");
                    opty_id = object.getString("OPTY_ID");
                    new CampaignContact(row_id, camp_name, opty_id);
                }
            }
            updateList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);

        }
    }
*/

    private void updateList() {
        enquirycontacts.setAdapter(enquiryContactadapter);

        vincontacts.setAdapter(vinContactadapter);

        if (enquirycontacts.getCount() > 0) {
            addlayout.setVisibility(View.GONE);
            enquirytitle.setVisibility(View.VISIBLE);
            vintitle.setVisibility(View.VISIBLE);
            enquirycontacts.setVisibility(View.VISIBLE);
            setListViewHeightBasedOnChildren(enquirycontacts);


        }
        if (vincontacts.getCount() > 0) {
            addlayout.setVisibility(View.GONE);
            vincontacts.setVisibility(View.VISIBLE);
            setListViewHeightBasedOnChildren(vincontacts);
        }
        if (enquirycontacts.getCount() == 0 && vincontacts.getCount() == 0) {
           /* DatabaseHelper db = new DatabaseHelper(getContext());
            List<LocalEnquiry> allrecords = db.getAllEnquiries();
            int flag = 0;*/
            /*for (LocalEnquiry record : allrecords) {
                Log.e("db_ph_no:",record.getContact_no());
                if (phone_no.equalsIgnoreCase(record.getContact_no()) || reg_no.equalsIgnoreCase(record.getReg_no())) {
                    flag = 1;
                }
            }
            if (flag == 0) {*/
            addlayout.setVisibility(View.VISIBLE);
            enquirytitle.setVisibility(View.GONE);
            vintitle.setVisibility(View.GONE);
            enquirycontacts.setVisibility(View.GONE);
            vincontacts.setVisibility(View.GONE);

           /* } else {
                Toast.makeText(getContext(),
                        "No contact found. You already created a new enquiry for this contact. Check after some time !!", Toast.LENGTH_LONG).show();
            }*/
            //   progressbar.setVisibility(View.GONE);

        }
    }

    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_contact, f);
        ft.commit();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void send_id(Bundle bundle) {
        bundle.putString("user", user_id);
    }

    public void fetch_pref() {
        user_id = PreferenceUtil.get_UserId(getContext());
        dealer_code = PreferenceUtil.get_DealerCode(getContext());
    }

    public void save_data() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("contact_no", phone_no);
        edit.putString("reg_no", reg_no);
        edit.commit();
    }

    public class Contactlist extends AsyncTask<Void, Void, String> {
        String newurlParameters;
        NetworkConnect networkConnect;

        public Contactlist(String urlParameters) {
            this.newurlParameters = urlParameters;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //      pendingorders_msg.setVisibility(View.INVISIBLE);
        }

        protected String doInBackground(Void... params) {
            if (NetConnections.isConnected(getContext())) {
                try {
                    String urlParameters = "data=" + URLEncoder.encode(newurlParameters, "UTF-8");
                    networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                    String result = networkConnect.execute();
                    if (result != null)
                        encryptdata = result.replace("\\/", "/");
                    String newurlparams = "data=" + URLEncoder.encode(encryptdata, "UTF-8");
                    NetworkConnect networkConnect = new NetworkConnect(URLConstants.FETCH_CONTACT, newurlparams);
                    result = networkConnect.execute();
                    return result;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            try {
                DatabaseHelper db = new DatabaseHelper(getContext());
                db.clearcontactfollowup_table();
                Log.e("contact_response", result);
                JSONObject jsono = new JSONObject(result);
                if (jsono.has("enquiry")) {
                    JSONArray jarray = jsono.getJSONArray("enquiry");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        fst_name1 = object.getString("FST_NAME");
                        last_name1 = object.getString("LAST_NAME");
                        cell_ph_num1 = object.getString("CELL_PH_NUM");
                        age1 = object.getString("AGE");
                        gender1 = object.getString("GENDER");
                        email_addr1 = object.getString("EMAIL_ADDR");
                        state1 = object.getString("STATE");
                        district1 = object.getString("DISTRICT");
                        tehsil1 = object.getString("TEHSIL");
                        city1 = object.getString("CITY");
                        x_con_seq_no_1 = object.getString("X_CON_SEQ_NUM");
                        x_model_interested = object.getString("X_MODEL_INTERESTED");
                        expctd_dt_purchase = object.getString("EXPCTD_DT_PURCHASE");
                        x_exchange_required = object.getString("X_EXCHANGE_REQUIRED");
                        x_finance_required = object.getString("X_FINANCE_REQUIRED");
                        existing_vehicle = object.getString("EXISTING_VEHICLE");
                        followup_comments = object.getString("FOLLOWUP_COMMENTS");
                        enquiry_id = object.getString("ENQUIRY_ID");
                        x_test_ride_req = object.getString("X_TEST_RIDE_REQ");
                        enquiry_entry_date = object.getString("ENQUIRY_ENTRY_DATE");
                        addr1 = object.getString("ADDR");
                        addr_line_2_1 = object.getString("ADDR_LINE_2");
                        make_cd = object.getString("MAKE_CD");
                        model_cd1 = object.getString("MODEL_CD");
                        dealer_bu_id = object.getString("DEALER_BU_ID");
                        follow_date = object.getString("FOLLOW_DATE");
                        try {

                            db.addcontactfollowup(new Followup(object.getString("X_MODEL_INTERESTED"), object.getString("ENQUIRY_ENTRY_DATE"),
                                    object.getString("EXPCTD_DT_PURCHASE"), object.getString("FOLLOW_DATE"), object.getString("ENQUIRY_ID"), object.getString("FOLLOWUP_COMMENTS"), "0"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        enquiryContactadapter.add(new EnquiryContact(fst_name1, last_name1, cell_ph_num1, age1, gender1, email_addr1, state1, district1, tehsil1, city1, x_con_seq_no_1, x_model_interested,
                                expctd_dt_purchase, x_exchange_required, x_finance_required, existing_vehicle, followup_comments, enquiry_id,
                                x_test_ride_req, enquiry_entry_date, addr1, addr_line_2_1, make_cd, model_cd1, dealer_bu_id, follow_date));

                        enquiryContactadapter.notifyDataSetChanged();
                    }
                }

                ArrayList<String> firstname = new ArrayList<String>();
                ArrayList<String> lastname = new ArrayList<String>();
                ArrayList<String> mobile = new ArrayList<String>();
                ArrayList<String> state = new ArrayList<String>();
                ArrayList<String> district = new ArrayList<String>();
                int check = 0;
                firstname.add("");
                lastname.add("");
                mobile.add("");
                state.add("");
                district.add("");

                if (jsono.has("vin")) {
                    JSONArray jarray = jsono.getJSONArray("vin");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        fst_name2 = object.getString("FST_NAME");
                        last_name2 = object.getString("LAST_NAME");
                        cell_ph_num2 = object.getString("CELL_PH_NUM");
                        age2 = object.getString("AGE");
                        gender2 = object.getString("GENDER");
                        email_addr2 = object.getString("EMAIL_ADDR");
                        state2 = object.getString("STATE");
                        district2 = object.getString("DISTRICT");
                        tehsil2 = object.getString("TEHSIL");
                        city2 = object.getString("CITY");
                        x_con_seq_no_2 = object.getString("X_CON_SEQ_NUM");
                        x_hmgl_card_num = object.getString("X_HMGL_CARD_NUM");
                        last_srvc_dt = object.getString("LAST_SRVC_DT");
                        next_srvc_due_dt = object.getString("NEXT_SERVICE_DUE_DT");
                        ins_policy_num = object.getString("INS_POLICY_NUM");
                        x_ins_exp_dt = object.getString("X_INS_EXP_DT");
                        model_cd2 = object.getString("MODEL_CD");
                        serial_num = object.getString("SERIAL_NUM");
                        primary_dealer_name = object.getString("PRIMARY_DEALER_NAME");
                        attrib_42 = object.getString("ATTRIB_42");
                        prod_attrib02_CD = object.getString("PROD_ATTRIB02_CD");
                        desc_text = object.getString("DESC_TEXT");
                        first_sale_dt = object.getString("FIRST_SALE_DT");
                        ins_policy_co = object.getString("INS_POLICY_CO");
                        x_hmgl_card_points = object.getString("X_HMGL_CARD_POINTS");
                        addr2 = object.getString("ADDR");
                        addr_line_2_2 = object.getString("ADDR_LINE_2");
                        new VinnContact(fst_name2, last_name2, cell_ph_num2, gender2, email_addr2, state2, district2, tehsil2, city2,
                                x_con_seq_no_2, x_hmgl_card_num, last_srvc_dt, next_srvc_due_dt, ins_policy_num, x_ins_exp_dt, model_cd2, serial_num,
                                primary_dealer_name, attrib_42, prod_attrib02_CD, desc_text, first_sale_dt, ins_policy_co, x_hmgl_card_points, age2, addr2, addr_line_2_2);

                        for (int j = 0; j < firstname.size(); j++) {
                            if (firstname.get(j).equals(fst_name2) && lastname.get(j).equals(last_name2)
                                    && mobile.get(j).equals(cell_ph_num2) && state.get(j).equals(state2) && district.get(j).equals(district2)) {
                                check = 0;
                            } else {
                                check = 1;
                            }
                        }
                        if (check == 1) {
                            vinContactadapter.add(new Contact(fst_name2, last_name2, cell_ph_num2, age2, gender2, email_addr2, state2, district2, tehsil2, city2, x_con_seq_no_2));
                            vinContactadapter.notifyDataSetChanged();
                            firstname.add(fst_name2);
                            lastname.add(last_name2);
                            mobile.add(cell_ph_num2);
                            state.add(state2);
                            district.add(district2);
                        }
                    }
                }
                if (jsono.has("campaign")) {
                    JSONArray jarray = jsono.getJSONArray("campaign");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        row_id = object.getString("ROW_ID");
                        camp_name = object.getString("CAMPAIGN_NAME");
                        opty_id = object.getString("OPTY_ID");
                        new CampaignContact(row_id, camp_name, opty_id);
                    }
                }
                updateList();
            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
                addlayout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                addlayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
              /*  Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT);
*/
            }
        }
    }

}

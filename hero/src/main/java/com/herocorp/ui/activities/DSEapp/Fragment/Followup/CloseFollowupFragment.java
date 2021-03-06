package com.herocorp.ui.activities.DSEapp.Fragment.Followup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect1;
import com.herocorp.ui.activities.DSEapp.DbSyncservice;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.activities.DSEapp.models.Close_followup;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by rsawh on 24-Sep-16.
 */
public class CloseFollowupFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    Spinner spin_mainreason, spin_subreason, spin_make, spin_model;
    EditText remark;
    ImageView imageview_close;
    ImageView button_submit;

    String user, encryptuser, enquiryid;
    DatabaseHelper db;
    String sync_status;

    ArrayList<String> arr_mainreason = new ArrayList<String>();
    ArrayList<String> arr_subreason = new ArrayList<String>();
    ArrayList<Bikemake> arr_makelist = new ArrayList<Bikemake>();
    ArrayList<String> arr_modellist = new ArrayList<String>();

    String[] mainreason = {"--select--", "Others",
            "Purchased from Own Dealership",
            "Dropped the Idea",
            "Purchased From Competition",
            "Purchased From CoDealer"};
    String[] others = {"--select--", "Finance not available",
            "SKU Non-Availability",
            "Secondhand Purchase",
            "Others",
            "Exchange price not suitable",
            "Wrong Contact Number"};
    String[] codealer = {"--select--", "Better Finance",
            "Discount",
            "Better exchange value",
            "Proximity",
            "Others",
            "SKU Non-Availability",
    };
    String[] competiton = {"--select--", "Proximity",
            "SKU Non-Availability",
            "First preference",
            "Better Finance",
            "Others",
            "Discount",
            "Better exchange value"};

    String main_reason = mainreason[0], sub_reason = others[0], model, make;
    int enq_flag = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_closefollowup_fragment, container, false);

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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{120, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 10, 80, 40}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        spin_mainreason = (Spinner) rootView.findViewById(R.id.spinner_mainreason);
        spin_subreason = (Spinner) rootView.findViewById(R.id.spinner_subreason);
        spin_make = (Spinner) rootView.findViewById(R.id.spinner_make);
        spin_model = (Spinner) rootView.findViewById(R.id.spinner_model);

        remark = (EditText) rootView.findViewById(R.id.edittext_remark);
        imageview_close = (ImageView) rootView.findViewById(R.id.imageView_closefollowup);
        button_submit = (ImageView) rootView.findViewById(R.id.imageView_submit_closefollowup);

        try {
            Bundle bundle = this.getArguments();
            // encryptuser = bundle.getString("user_id");
            user = bundle.getString("user");
            enquiryid = bundle.getString("enquiryid");
            if (bundle.containsKey("enq_flag"))
                enq_flag = bundle.getInt("enq_flag");

            fetch_make();
            /*String newurlparams = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
            NetworkConnect networkConnect = new NetworkConnect(URLConstants.BIKE_MAKE_MODEL, newurlparams);
            jsonparse(networkConnect.execute());*/

        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < mainreason.length; i++) {
            arr_mainreason.add(mainreason[i]);
        }

        ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_mainreason);
        spin_mainreason.setAdapter(at1);

        spin_mainreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                main_reason = parent.getItemAtPosition(position).toString();
                if (main_reason.equals("--select--")) {
                    arr_subreason.clear();
                    arr_subreason.add("--select--");
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_subreason);
                    spin_subreason.setAdapter(at2);
                    spin_make.setAdapter(at2);
                    spin_model.setAdapter(at2);
                    sub_reason = "--select--";
                } else if (main_reason.equals("Others")) {
                    arr_subreason.clear();
                    for (int i = 0; i < others.length; i++) {
                        arr_subreason.add(others[i]);
                    }
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_subreason);
                    spin_subreason.setAdapter(at2);
                    sub_reason = others[0];
                    fetch_records(0);
                } else if (main_reason.equals("Purchased From Competition")) {
                    arr_subreason.clear();
                    for (int i = 0; i < competiton.length; i++) {
                        arr_subreason.add(competiton[i]);
                    }
                    ArrayAdapter<String> at4 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_subreason);
                    spin_subreason.setAdapter(at4);
                    sub_reason = competiton[0];
                    fetch_records(1);
                } else if (main_reason.equals("Purchased From CoDealer")) {
                    arr_subreason.clear();
                    for (int i = 0; i < codealer.length; i++) {
                        arr_subreason.add(codealer[i]);
                    }
                    ArrayAdapter<String> at3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_subreason);
                    spin_subreason.setAdapter(at3);
                    sub_reason = codealer[0];
                    fetch_records(2);
                } else if (main_reason.equals("Purchased from Own Dealership")) {
                    arr_subreason.clear();
                    arr_subreason.add("N.A.");
                    ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_subreason);
                    spin_subreason.setAdapter(at1);
                    sub_reason = "";
                    fetch_records(0);
                } else {
                    arr_subreason.clear();
                    arr_subreason.add("N.A.");
                    ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_subreason);
                    spin_subreason.setAdapter(at1);
                    spin_make.setAdapter(at1);
                    spin_model.setAdapter(at1);
                    sub_reason = "";
                    make = "";
                    model = "";


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                main_reason = parent.getItemAtPosition(0).toString();
            }


        });
        spin_subreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub_reason = parent.getItemAtPosition(position).toString();
                if (sub_reason == "NA")
                    sub_reason = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sub_reason = parent.getItemAtPosition(0).toString();
            }
        });


        spin_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                make = parent.getItemAtPosition(position).toString();
                if (position != 0) {
                    Bikemake bikemake = (Bikemake) parent.getSelectedItem();
                    Bikemodel bikemodel = new Bikemodel();
                    arr_modellist.clear();
                    arr_modellist = bikemodel.getModelname(bikemake.getId());
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_modellist);
                    spin_model.setAdapter(at2);
                } else if (position == 0 && make == "N.A.") {
                    make = "";
                } else {
                    arr_modellist.clear();
                    arr_modellist.add("--select--");
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview1, arr_modellist);
                    spin_model.setAdapter(at2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spin_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        menu.setOnClickListener(this);
        imageview_close.setOnClickListener(this);
        button_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.imageView_closefollowup) {
            getActivity().onBackPressed();
        } else if (i == R.id.imageView_submit_closefollowup) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            String remarks = remark.getText().toString();
            ProgressDialog progress = new ProgressDialog(getContext());

            /*|| make.equals("--select--") || model.equals("--select--")*/
            if (main_reason.equals("--select--") || sub_reason.equals("--select--") ) {
                Toast.makeText(getContext(), "Reason/SubReason Missing!!", Toast.LENGTH_SHORT).show();
            } else {
                if (main_reason.equalsIgnoreCase("Dropped the Idea")) {
                    sub_reason = "N.A.";
                    model = "N.A.";
                    make = "N.A";
                }
                sync_status = "1";
                db = new DatabaseHelper(getContext());

                if (enq_flag == 0) {
                    db.delete_followup(enquiryid);
                } else
                    db.delete_contactfollowup(enquiryid);

                if (NetConnections.isConnected(getContext())) {
                    String newurlparams = null;
                    try {

                        JSONObject jsonparams = new JSONObject();
                        jsonparams.put("reason", main_reason);
                        jsonparams.put("sub_reason", sub_reason);
                        jsonparams.put("existMake", make);
                        jsonparams.put("existModel", model);
                        jsonparams.put("remarks", remarks);
                        jsonparams.put("user_id", user);
                        jsonparams.put("dms_enquiry_id", enquiryid);
                        Log.e("close_followup", jsonparams.toString());
                        newurlparams = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");

                        new NetworkConnect1(URLConstants.SYNC_FOLLOW_UP, newurlparams, progress, "Followup has been successfully submitted.", getContext(), 1).execute();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                    }
                } else {
                    db.add_close_followup(new Close_followup(main_reason, sub_reason, make, model, remarks, user, enquiryid, sync_status));
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "Followup has been successfully submitted.");
                    bundle.putInt("flag", 1);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AlertDialogFragment dialogFragment = new AlertDialogFragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(fm, "Sample Fragment");
                  /*  Intent intent = new Intent(getContext(), DbSyncservice.class);
                    getActivity().startService(intent);*/
                }
            }
        }
    }

    public void fetch_records(int flag) {
        try {
            db = new DatabaseHelper(getContext());
            arr_makelist.clear();
            arr_makelist.add(new Bikemake("", "--select--"));
            List<Bikemake> allrecords = db.getAllBikemakes();
            for (Bikemake record : allrecords) {
                if (flag == 1) {
                    if (!(record.getMakename().equalsIgnoreCase("HERO MOTOCORP"))) {
                        arr_makelist.add(new Bikemake(record.getId(), record.getMakename()));
                    }
                }
                else if (flag == 2) {
                    if (record.getMakename().equalsIgnoreCase("HERO MOTOCORP")) {
                        arr_makelist.add(new Bikemake(record.getId(), record.getMakename()));
                    }
                }else
                    arr_makelist.add(new Bikemake(record.getId(), record.getMakename()));

            }
            ArrayAdapter<Bikemake> at1 = new ArrayAdapter<Bikemake>(getContext(), R.layout.spinner_textview1, arr_makelist);
            spin_make.setAdapter(at1);


        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetch_make() {
        db = new DatabaseHelper(getContext());
        List<Bike_model> records = db.getAllBikemodels();
        for (Bike_model record : records) {
            new Bikemodel(record.getMakeid(), record.getModelname());
        }
    }
}



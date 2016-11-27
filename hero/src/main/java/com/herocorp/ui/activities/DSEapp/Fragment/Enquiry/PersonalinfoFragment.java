package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.District;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.activities.DSEapp.models.Tehsil;
import com.herocorp.ui.activities.DSEapp.models.Village;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rsawh on 13-Sep-16.
 */
public class PersonalinfoFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    Spinner gender_spinner, state_spinner, district_spinner, tehsil_spinner, village_spinner;
    EditText firstname_et, lastname_et, mobile_et, age_et, address1_et, address2_et, pincode_et, email_et;
    ImageView button_submit;

    ArrayList<State> arr_state = new ArrayList<State>();
    ArrayList<Tehsil> arr_tehsil = new ArrayList<Tehsil>();
    ArrayList<District> arr_district = new ArrayList<District>();
    ArrayList<String> arr_village = new ArrayList<String>();

    String arr_gender[] = {"--select--", "M", "F"};


    String gender = "", state = "", district = "", tehsil = "", village = "";
    String firstname = "", lastname = "", mobile = "", age = "", email = "", address1 = "", address2 = "", pincode = "";
    String username = "ROBINK11610";

    String encryptuser;
    NetworkConnect networkConnect;
    String data;
    DatabaseHelper db;
    private SharedPreferences sharedPreferences;
    String state_name, state_id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_personalinfo_fragment, container, false);

        initView(rootView);
        // data = "{\"username\":\"" + username + "\",\"version\":\"" + version + "\",\"imei\":\"" + imei + "\",\"uuid\":\"" + uuid + "\"}";
//        encryptuser(data, URLConstants.LOGIN, 0);
        fetch_states();

        return rootView;
    }

    private void initView(View rootView) {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{100, 20, 100, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        TextView firstname_tv = (TextView) rootView.findViewById(R.id.firstname_textview);

        firstname_tv.setText(colortext("First Name", "*"));

        TextView lastname_tv = (TextView) rootView.findViewById(R.id.lastname_textview);

        lastname_tv.setText(colortext("Last Name", "*"));

        TextView mobile_tv = (TextView) rootView.findViewById(R.id.mobile_textview);

        mobile_tv.setText(colortext("Mobile", "*"));

        firstname_et = (EditText) rootView.findViewById(R.id.firstname_edittext);
        lastname_et = (EditText) rootView.findViewById(R.id.lastname_edittext);
        mobile_et = (EditText) rootView.findViewById(R.id.mobile_edittext);
        email_et = (EditText) rootView.findViewById(R.id.email_edittext);
        age_et = (EditText) rootView.findViewById(R.id.age_edittext);
        address1_et = (EditText) rootView.findViewById(R.id.address1_edittext);
        address2_et = (EditText) rootView.findViewById(R.id.address2_edittext);
        pincode_et = (EditText) rootView.findViewById(R.id.pincode_edittext);

        button_submit = (ImageView) rootView.findViewById(R.id.imageview_submit_personalinfo);

        gender_spinner = (Spinner) rootView.findViewById(R.id.gender_spinner);
        ArrayAdapter<String> at = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, arr_gender);
        gender_spinner.setAdapter(at);

        state_spinner = (Spinner) rootView.findViewById(R.id.state_spinner);

        district_spinner = (Spinner) rootView.findViewById(R.id.district_spinner);


        tehsil_spinner = (Spinner) rootView.findViewById(R.id.tehsil_spinner);

        village_spinner = (Spinner) rootView.findViewById(R.id.city_spinner);

        Bundle bundle = this.getArguments();
        mobile_et.setText(bundle.getString("phoneno"));

        fetch_pref();
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    reset(0);
                    state = parent.getItemAtPosition(position).toString();
                    State sel_state = (State) parent.getSelectedItem();
                    data = "{\"state_id\":\"" + sel_state.getId() + "\"}";
                    encryptuser(data, URLConstants.GET_DISTRICT, 1);
                } else
                    reset(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                state = "--select--";
            }
        });
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    reset(1);
                    district = parent.getItemAtPosition(position).toString();
                    District sel_district = (District) parent.getSelectedItem();

                    data = "{\"district_id\":\"" + sel_district.getId() + "\"}";

                    encryptuser(data, URLConstants.GET_DISTRICT_DATA, 2);
                } else
                    reset(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                district = "--select--";
            }
        });

        tehsil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    tehsil = parent.getItemAtPosition(position).toString();
                    Tehsil sel_tehsil = (Tehsil) parent.getSelectedItem();

                    Village req_village = new Village();
                    arr_village.clear();
                    arr_village = req_village.getVillage(sel_tehsil.getId());
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, arr_village);
                    village_spinner.setAdapter(at2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tehsil = "--select--";
            }
        });

        village_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                village = parent.getItemAtPosition(position).toString();
                if (village.equals("--select--"))
                    village = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                village = "--select--";
            }
        });

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    gender = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = "--select--";
            }
        });

        button_submit.setOnClickListener(this);
        menu.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (i == R.id.imageview_submit_personalinfo) {
            firstname = firstname_et.getText().toString();
            lastname = lastname_et.getText().toString();
            mobile = mobile_et.getText().toString();
            email = email_et.getText().toString();
            age = age_et.getText().toString();
            address1 = address1_et.getText().toString();
            address2 = address2_et.getText().toString();
            pincode = pincode_et.getText().toString();
            if (firstname.equals("") || lastname.equals("") || mobile.equals("")) {

                Toast.makeText(getContext(), "Please fill all the required details !!", Toast.LENGTH_SHORT).show();

            } else if (!TextUtils.isEmpty(email) && emailValidator(email) == false) {

                Toast.makeText(getContext(), "Invalid Email !!", Toast.LENGTH_LONG).show();

            } else if (mobile.length() < 10)

                Toast.makeText(getContext(), "Invalid Contact !!", Toast.LENGTH_LONG).show();

            else {
                Toast.makeText(getContext(), firstname + lastname + mobile + age + gender + state + district + tehsil + village + address1 + address2 + pincode, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("firstname", firstname);
                bundle.putString("lastname", lastname);
                bundle.putString("mobile", mobile);
                bundle.putString("email", email);
                bundle.putString("age", age);
                bundle.putString("gender", gender);
                bundle.putString("state", state);
                bundle.putString("district", district);
                bundle.putString("tehsil", tehsil);
                bundle.putString("village", village);
                bundle.putString("address1", address1);
                bundle.putString("address2", address2);
                bundle.putString("pin", pincode);
                Log.e("personal_info:", bundle.toString());
                Fragment f = new AddenquiryFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                f.setArguments(bundle);
                // ft.addToBackStack(null);
                ft.replace(R.id.content_personalinfo, f);
                ft.commit();
            }

        }

    }

    public SpannableStringBuilder colortext(String simple, String colored) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;

    }

   /* public void encryptuser1() {
        if (NetConnections.isConnected(getContext())) {
            String json = "{\"username\":\"ROBINK11610\",\"version\":\"1.0\",\"imei\":\"10\",\"uuid\":\"0\"}";
            try {
                String urlParameters = "data=" + URLEncoder.encode(json, "UTF-8");
                networkConnect = new NetworkConnect("http://abym.in/clientProof/hero_motors/encrypt", urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptuser = result.replace("\\/", "/");
                String newurlparams = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
                NetworkConnect networkConnect = new NetworkConnect(URLConstants.LOGIN, newurlparams);
                jsonparse_state(networkConnect.execute());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }
*/
   /* public void jsonparse_state(String result) {
        try {
            Log.e("login_response", result);
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("state");

            arr_state.add(new State("", "--select--"));
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_state.add(new State(object.getString("id"), object.getString("state_name")));
            }
            ArrayAdapter<State> at1 = new ArrayAdapter<State>(getContext(), R.layout.spinner_textview, arr_state);
            state_spinner.setAdapter(at1);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!" + e, Toast.LENGTH_SHORT).show();

        }
    }*/

    public void encryptuser(String data, String url, int flag) {
        if (NetConnections.isConnected(getContext())) {
            try {
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");
                networkConnect = new NetworkConnect("http://abym.in/clientProof/hero_motors/encrypt", urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptuser = result.replace("\\/", "/");
                String newurlparams = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
                NetworkConnect networkConnect = new NetworkConnect(url, newurlparams);
                if (flag == 0) {
                    // jsonparse_state(networkConnect.execute());
                }
                if (flag == 1) {
                    jsonparse_district(networkConnect.execute());
                }
                if (flag == 2) {
                    jsonparse_tehsil_village(networkConnect.execute());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(getContext(), "Check your connection !!", Toast.LENGTH_SHORT).show();
    }

    public void jsonparse_district(String result) {
        try {
            Log.e("district_response", result);
            arr_district.clear();
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("district");
            arr_district.add(new District("", "--select--"));
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_district.add(new District(object.getString("id"), object.getString("district_name")));
            }
            ArrayAdapter<District> at1 = new ArrayAdapter<District>(getContext(), R.layout.spinner_textview, arr_district);
            district_spinner.setAdapter(at1);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!" + e, Toast.LENGTH_SHORT).show();

        }
    }

    public void jsonparse_tehsil_village(String result) {
        try {
            Log.e("tehsil_response", result);
            arr_tehsil.clear();
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("tehsil");
            arr_tehsil.add(new Tehsil("", "", "--select--"));
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_tehsil.add(new Tehsil(object.getString("district_id"), object.getString("id"), object.getString("tehsil_name")));
            }
            ArrayAdapter<Tehsil> at1 = new ArrayAdapter<Tehsil>(getContext(), R.layout.spinner_textview, arr_tehsil);
            tehsil_spinner.setAdapter(at1);

            JSONArray jarray1 = jsono.getJSONArray("village");

            //   new Village("", "", "--select--");
            for (int i = 0; i < jarray1.length(); i++) {
                JSONObject object = jarray1.getJSONObject(i);
                new Village(object.getString("tehsil_id"), object.getString("id"), object.getString("village_name"));
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void reset(int flag) {
        ArrayList<String> arr_reset = new ArrayList<String>();
        arr_reset.clear();
        arr_reset.add("--select--");
        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, arr_reset);
        if (flag == 0) {
            district_spinner.setAdapter(at);
            tehsil_spinner.setAdapter(at);
            village_spinner.setAdapter(at);
            district = "";
            tehsil = "";
            village = "";
        } else if (flag == 1) {
            // tehsil_spinner.setAdapter(at);
            village_spinner.setAdapter(at);
            tehsil = "";
            village = "";
        }
    }

    public void fetch_states() {
        db = new DatabaseHelper(getContext());
        List<State> allrecords = db.getAllStates();
        arr_state.add(new State("", "--select--"));

        int position = 0;
        int i = 1;
        for (State record : allrecords) {
            if (record.getId().equalsIgnoreCase(state_id) || record.getState().equalsIgnoreCase(state_name))
                position = i;
            arr_state.add(new State(record.getId(), record.getState()));
            i++;
        }
        ArrayAdapter<State> at1 = new ArrayAdapter<State>(getContext(), R.layout.spinner_textview, arr_state);
        state_spinner.setAdapter(at1);
        state_spinner.setSelection(position);
    }

    public void fetch_pref() {
        sharedPreferences = getActivity().getSharedPreferences("hero", 0);
        if (sharedPreferences.contains("state_name"))
            state_name = sharedPreferences.getString("state_name", null);

        if (sharedPreferences.contains("state_id"))
            state_id = sharedPreferences.getString("state_id", null);
    }

}

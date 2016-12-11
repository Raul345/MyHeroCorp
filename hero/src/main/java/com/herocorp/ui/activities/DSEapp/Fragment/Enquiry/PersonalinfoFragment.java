package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.herocorp.ui.activities.DSEapp.models.Campaign;
import com.herocorp.ui.activities.DSEapp.models.District;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.activities.DSEapp.models.Tehsil;
import com.herocorp.ui.activities.DSEapp.models.Village;
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


    String data;
    DatabaseHelper db;
    String state_name, state_id, district_name, tehsil_name, city_name;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_personalinfo_fragment, container, false);
        initView(rootView);

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

        TextView age_tv = (TextView) rootView.findViewById(R.id.age_tv);

        age_tv.setText(colortext("Age", "*"));

        TextView gender_tv = (TextView) rootView.findViewById(R.id.gender_tv);

        gender_tv.setText(colortext("Gender", "*"));

        TextView state_tv = (TextView) rootView.findViewById(R.id.state_textview);

        state_tv.setText(colortext("State", "*"));

        TextView district_tv = (TextView) rootView.findViewById(R.id.district_textview);

        district_tv.setText(colortext("District", "*"));

        TextView tehsil_tv = (TextView) rootView.findViewById(R.id.tehsil_textview);

        tehsil_tv.setText(colortext("Tehsil", "*"));

        TextView city_tv = (TextView) rootView.findViewById(R.id.city_textview);

        city_tv.setText(colortext("City/Village/Tc", "*"));

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
                    send_request(data, URLConstants.GET_DISTRICT, 1);
                } else {
                    state = "";
                    reset(0);
                }
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

                    send_request(data, URLConstants.GET_DISTRICT_DATA, 2);
                } else {
                    district = "";
                    reset(1);
                }
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
                    int cposition = 0;
                    for (int i = 0; i < arr_village.size(); i++) {
                        if (city_name.equalsIgnoreCase(arr_village.get(i))) {
                            cposition = i;
                        }
                    }
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, arr_village);
                    village_spinner.setAdapter(at2);
                    village_spinner.setSelection(cposition);
                    state_name = "";
                    tehsil_name = "";
                    district_name = "";
                    city_name = "";

                } else
                    tehsil = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tehsil = "";
            }
        });

        village_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    village = parent.getItemAtPosition(position).toString();
                } else
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
            if (firstname.equals("") || lastname.equals("") || mobile.equals("") || age.equals("") || gender.equals("") || state.equals("")
                    || district.equals("") || tehsil.equals("") || village.equals("")) {
                Toast.makeText(getContext(), "Please fill all the required details !!", Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.isEmpty(email) && emailValidator(email) == false) {

                Toast.makeText(getContext(), "Invalid Email !!", Toast.LENGTH_LONG).show();

            } else if (mobile.length() < 10)

                Toast.makeText(getContext(), "Invalid Contact !!", Toast.LENGTH_LONG).show();

            else {
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
                ft.addToBackStack("personalinfo");
                ft.add(R.id.content_personalinfo, f);
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


    public void send_request(String data, String url, int flag) {
        if (NetConnections.isConnected(getContext())) {
            try {
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");

                if (flag == 0) {
                    // jsonparse_state(networkConnect.execute());
                }
                if (flag == 1) {

                    new Fetch_data(url, urlParameters).execute();

                }
                if (flag == 2) {
                    new Fetch_data(url, urlParameters).execute();

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

            int position = 0;
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_district.add(new District(object.getString("id"), object.getString("district_name")));

                if (object.getString("district_name").equalsIgnoreCase(district_name))
                    position = i + 1;
            }
            ArrayAdapter<District> at1 = new ArrayAdapter<District>(getContext(), R.layout.spinner_textview, arr_district);
            district_spinner.setAdapter(at1);
            district_spinner.setSelection(position);

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

            int tposition = 0;
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_tehsil.add(new Tehsil(object.getString("district_id"), object.getString("id"), object.getString("tehsil_name")));

                if (object.getString("tehsil_name").equalsIgnoreCase(tehsil_name))
                    tposition = i + 1;
            }
            ArrayAdapter<Tehsil> at1 = new ArrayAdapter<Tehsil>(getContext(), R.layout.spinner_textview, arr_tehsil);
            tehsil_spinner.setAdapter(at1);
            tehsil_spinner.setSelection(tposition);

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
            tehsil_spinner.setAdapter(at);
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
        state_name = PreferenceUtil.get_StateName(getContext());
        state_id = PreferenceUtil.get_StateId(getContext());
        tehsil_name = PreferenceUtil.get_TehsilName(getContext());
        district_name = PreferenceUtil.get_DistrictName(getContext());
        city_name = PreferenceUtil.get_CityName(getContext());
    }


    public class Fetch_data extends AsyncTask<Void, Void, String> {
        String targetURL;
        String newurlParameters;
        NetworkConnect networkConnect;
        private ProgressDialog progressDialog;

        public Fetch_data(String targeturl, String urlParameters) {
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
                    String encryptdata = null;
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
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                Log.e("response", s);
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.has("district")) {
                    jsonparse_district(s);
                }
                if (jsonObject.has("tehsil")) {
                    jsonparse_tehsil_village(s);
                }
                progressDialog.dismiss();
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.District;
import com.herocorp.ui.activities.DSEapp.models.State;
import com.herocorp.ui.activities.DSEapp.models.Tehsil;
import com.herocorp.ui.activities.DSEapp.models.Village;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class EditaddressFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private View rootView;

    private int mPage;
    String state = "", district = "", tehsil = "", village = "", address1 = "", address2 = "", pincode = "";
    String state_name = "", district_name = "", tehsil_name = "", village_name = "";
    Spinner state_spinner, district_spinner, tehsil_spinner, village_spinner;
    EditText address1_et, address2_et, pincode_et;
    ImageView editaddress_button;
    String data;

    ArrayList<State> arr_state = new ArrayList<State>();
    ArrayList<Tehsil> arr_tehsil = new ArrayList<Tehsil>();
    ArrayList<District> arr_district = new ArrayList<District>();
    ArrayList<String> arr_village = new ArrayList<String>();


    SharedPreferences mypref;
    SharedPreferences.Editor edit;
    int flag = 0, editflag = 0;

    DatabaseHelper db;

    public static EditaddressFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EditaddressFragment fragment = new EditaddressFragment();
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

        rootView = inflater.inflate(R.layout.dse_editaddress_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mypref = getActivity().getSharedPreferences("herocorp", 0);
        edit = mypref.edit();

        TextView state_tv = (TextView) rootView.findViewById(R.id.state_textview);

        state_tv.setText(colortext("State", "*"));

        TextView district_tv = (TextView) rootView.findViewById(R.id.district_textview);

        district_tv.setText(colortext("District", "*"));

        TextView tehsil_tv = (TextView) rootView.findViewById(R.id.tehsil_textview);

        tehsil_tv.setText(colortext("Tehsil", "*"));

        TextView city_tv = (TextView) rootView.findViewById(R.id.city_textview);

        city_tv.setText(colortext("City/Village/Tc", "*"));

        address1_et = (EditText) rootView.findViewById(R.id.address1_edittext);
        address2_et = (EditText) rootView.findViewById(R.id.address2_edittext);
        pincode_et = (EditText) rootView.findViewById(R.id.pincode_edittext);
        state_spinner = (Spinner) rootView.findViewById(R.id.state_spinner);
        district_spinner = (Spinner) rootView.findViewById(R.id.district_spinner);
        tehsil_spinner = (Spinner) rootView.findViewById(R.id.tehsil_spinner);
        village_spinner = (Spinner) rootView.findViewById(R.id.city_spinner);
        editaddress_button = (ImageView) rootView.findViewById(R.id.editaddress_button);

        fetch_data();

        state_spinner.setEnabled(false);
        district_spinner.setEnabled(false);
        tehsil_spinner.setEnabled(false);
        village_spinner.setEnabled(false);

        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    reset(0);
                    state = parent.getItemAtPosition(position).toString();
                    State sel_state = (State) parent.getSelectedItem();
                    data = "{\"state_id\":\"" + sel_state.getId() + "\"}";
                    send_request(data, URLConstants.GET_DISTRICT, 1);
                    edit.putString("state", state);
                    edit.commit();

                } else if (position == 0) {
                    reset(0);
                    state = "";
                    edit.putString("state", state);
                    edit.commit();
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
                    edit.putString("district", district);
                    edit.commit();
                } else if (position == 0) {
                    reset(1);
                    district = "";
                    edit.putString("district", district);
                    edit.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                district = "";
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
                        if (village_name.equalsIgnoreCase(arr_village.get(i))) {
                            cposition = i;
                        }
                    }
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, arr_village);
                    village_spinner.setAdapter(at2);
                    village_spinner.setSelection(cposition);
                    state_name = "";
                    tehsil_name = "";
                    district_name = "";
                    village_name = "";
                    edit.putString("tehsil", tehsil);
                    edit.commit();
                } else if (position == 0) {
                    reset(2);
                    tehsil = "";
                    village = "";
                    edit.putString("tehsil", tehsil);
                    edit.putString("city", village);
                    edit.commit();
                }
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
                    edit.putString("city", village);
                    edit.commit();
                } else {
                    village = "";
                    edit.putString("city", village);
                    edit.commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                village = "";
            }
        });

        editaddress_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editflag == 0) {
                    editaddress_button.setImageResource(R.drawable.button_on);
                    editflag = 1;
                    state_spinner.setEnabled(true);
                    district_spinner.setEnabled(true);
                    tehsil_spinner.setEnabled(true);
                    village_spinner.setEnabled(true);

                } else {
                    editaddress_button.setImageResource(R.drawable.button_offf);
                    editflag = 0;
                    state_spinner.setEnabled(false);
                    district_spinner.setEnabled(false);
                    tehsil_spinner.setEnabled(false);
                    village_spinner.setEnabled(false);
                }

            }
        });

        address1_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edit.putString("address1", address1_et.getText().toString());
                    edit.commit();
                }
            }
        });
        address2_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edit.putString("address2", address2_et.getText().toString());
                    edit.commit();

                }
            }
        });
        pincode_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edit.putString("pincode", pincode_et.getText().toString());
                    edit.commit();

                }
            }
        });

    }


    public void fetch_data() {
        if (mypref.contains("state")) {
            state_name = mypref.getString("state", "");
        }
        if (mypref.contains("district")) {
            district_name = mypref.getString("district", "");
        }
        if (mypref.contains("tehsil")) {
            tehsil_name = mypref.getString("tehsil", "");
        }
        if (mypref.contains("city")) {
            village_name = mypref.getString("city", "");
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
        Log.e("tehsilpref", tehsil + village);
        fetch_states();
    }

    public void jsonparse_district(String result) {
        try {
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
            arr_tehsil.clear();
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("tehsil");
            arr_tehsil.add(new Tehsil("", "", "--select--"));

            int tposition = 0;
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_tehsil.add(new Tehsil(object.getString("district_id"), object.getString("id"), object.getString("tehsil_name")));
                Log.e("id", i + "" + object.getString("tehsil_name"));
                if (object.getString("tehsil_name").equalsIgnoreCase(tehsil_name)) {
                    tposition = i + 1;
                }
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
        } else if (flag == 2) {
            village_spinner.setAdapter(at);
        }
    }

    public void fetch_states() {
        db = new DatabaseHelper(getContext());
        List<State> allrecords = db.getAllStates();
        arr_state.add(new State("", "--select--"));
        int position = 0;
        int i = 1;
        for (State record : allrecords) {
            if (record.getState().equalsIgnoreCase(state_name))
                position = i;
            arr_state.add(new State(record.getId(), record.getState()));
            i++;
        }
        ArrayAdapter<State> at1 = new ArrayAdapter<State>(getContext(), R.layout.spinner_textview, arr_state);
        state_spinner.setAdapter(at1);
        state_spinner.setSelection(position);
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

}

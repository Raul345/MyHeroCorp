package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    Spinner state_spinner, district_spinner, tehsil_spinner, village_spinner;
    EditText address1_et, address2_et, pincode_et;
    ImageView editaddress_button;
    String data;
    String encryptuser;
    NetworkConnect networkConnect;


    ArrayList<State> arr_state = new ArrayList<State>();
    ArrayList<Tehsil> arr_tehsil = new ArrayList<Tehsil>();
    ArrayList<District> arr_district = new ArrayList<District>();
    ArrayList<String> arr_village = new ArrayList<String>();


    SharedPreferences mypref;
    SharedPreferences.Editor edit;
    int flag = 0, editflag = 0;
    String username = "ROBINK11610", version = "1.0", imei = "10", uuid = "0";

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
                    encryptuser(data, URLConstants.GET_DISTRICT, 1);
                    edit.putString("state", state);
                    edit.commit();

                } else if (position == 0 && editflag == 1) {
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
                    encryptuser(data, URLConstants.GET_DISTRICT_DATA, 2);
                    edit.putString("district", district);
                    edit.commit();
                } else if (position == 0 && editflag == 1) {
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
                    ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, arr_village);
                    village_spinner.setAdapter(at2);
                    edit.putString("tehsil", tehsil);
                    edit.commit();
                    //  village_spinner.setSelection(((ArrayAdapter<String>) village_spinner.getAdapter()).getPosition(village));
                } else if (position == 0 && editflag == 1) {
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
                village = parent.getItemAtPosition(position).toString();
                edit.putString("city", village);
                edit.commit();

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
                    edit.putString("state", "");
                    edit.putString("district", "");
                    edit.putString("tehsil", "");
                    edit.putString("village", "");
                    edit.commit();
                    fetch_states();
                    //  data = "{\"username\":\"ROBINK11610\",\"version\":\"1.0\",\"imei\":\"10\",\"uuid\":\"0\"}";
                    /*data = "{\"username\":\"" + username + "\",\"version\":\"" + version + "\",\"imei\":\"" + imei + "\",\"uuid\":\"" + uuid + "\"}";
                    encryptuser(data, URLConstants.LOGIN, 0);*/

                    reset(0);

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
       /* final Bundle bundle = getArguments();
        state = bundle.getString("state");
        tehsil = bundle.getString("tehsil");
        district = bundle.getString("district");
        village = bundle.getString("city");*/
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

        ArrayList<String> prev_state = new ArrayList<String>();
        ArrayList<String> prev_tehsil = new ArrayList<String>();
        ArrayList<String> prev_district = new ArrayList<String>();
        ArrayList<String> prev_village = new ArrayList<String>();

        prev_state.add(state);
        ArrayAdapter<String> at = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, prev_state);
        state_spinner.setAdapter(at);

        prev_district.add(district);
        ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, prev_district);
        district_spinner.setAdapter(at1);

        prev_tehsil.add(tehsil);
        ArrayAdapter<String> at2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, prev_tehsil);
        tehsil_spinner.setAdapter(at2);

        prev_village.add(village);
        ArrayAdapter<String> at3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_textview, prev_village);
        village_spinner.setAdapter(at3);

    }

    public void jsonparse_state(String result) {
        try {
            JSONObject jsono = new JSONObject(result);
            JSONArray jarray = jsono.getJSONArray("state");

            arr_state.add(new State("", "--select--"));
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_state.add(new State(object.getString("id"), object.getString("state_name")));
            }
            ArrayAdapter<State> at1 = new ArrayAdapter<State>(getContext(), R.layout.spinner_textview, arr_state);
            state_spinner.setAdapter(at1);

            //   state_spinner.setSelection(Integer.parseInt(tehsil));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!" + e, Toast.LENGTH_SHORT).show();

        }
    }

    public void jsonparse_district(String result) {
        try {
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
            //          district_spinner.setSelection(((ArrayAdapter<String>) district_spinner.getAdapter()).getPosition(district));


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
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject object = jarray.getJSONObject(i);
                arr_tehsil.add(new Tehsil(object.getString("district_id"), object.getString("id"), object.getString("tehsil_name")));
            }
            ArrayAdapter<Tehsil> at1 = new ArrayAdapter<Tehsil>(getContext(), R.layout.spinner_textview, arr_tehsil);
            tehsil_spinner.setAdapter(at1);
            //   tehsil_spinner.setSelection(((ArrayAdapter<Tehsil>) tehsil_spinner.getAdapter()).(tehsil));

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

    public void encryptuser(String data, String url, int flag) {
        if (NetConnections.isConnected(getContext())) {
            try {
                String urlParameters = "data=" + URLEncoder.encode(data, "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.ENCRYPT, urlParameters);
                String result = networkConnect.execute();
                if (result != null)
                    encryptuser = result.replace("\\/", "/");
                String newurlparams = "data=" + URLEncoder.encode(encryptuser, "UTF-8");
                NetworkConnect networkConnect = new NetworkConnect(url, newurlparams);
                if (flag == 0) {
                    jsonparse_state(networkConnect.execute());
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
        for (State record : allrecords) {
            arr_state.add(new State(record.getId(), record.getState()));
        }
        ArrayAdapter<State> at1 = new ArrayAdapter<State>(getContext(), R.layout.spinner_textview, arr_state);
        state_spinner.setAdapter(at1);
    }
}

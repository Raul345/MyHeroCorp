package com.herocorp.ui.VAS;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Bike_model;
import com.herocorp.ui.activities.DSEapp.models.Bikemake;
import com.herocorp.ui.activities.DSEapp.models.Bikemodel;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsawh on 06-Oct-16.
 */
public class VasMaintenanceFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    Spinner spin_model;
    ArrayList<String> arr_modellist = new ArrayList<String>();
    String model;
    DatabaseHelper db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.vas_maintenance_fragment, container, false);

        try {
            initView(rootView);
            fetch_make_model();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void initView(View rootView) throws UnsupportedEncodingException {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{200, 20, 200, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{0, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{80, 20, 80, 10}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{80, 20, 80, 140}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        //setting header text
        String simple = "VALUE ADDED SERVICES ";
        String colored = "(VAS)";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        buttonHeader.setText(builder);

        TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 30}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);


        LinearLayout warranty_layout = (LinearLayout) rootView.findViewById(R.id.warranty_layout);
        LinearLayout maintenance_layout = (LinearLayout) rootView.findViewById(R.id.maintenance_layout);
        LinearLayout safety_layout = (LinearLayout) rootView.findViewById(R.id.safety_layout);
        LinearLayout genuine_layout = (LinearLayout) rootView.findViewById(R.id.genuineparts_layout);

        spin_model = (Spinner) rootView.findViewById(R.id.model_spinner);

        spin_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        warranty_layout.setOnClickListener(this);
        maintenance_layout.setOnClickListener(this);
        safety_layout.setOnClickListener(this);
        genuine_layout.setOnClickListener(this);
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Fragment f;
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        } else if (i == R.id.warranty_layout) {
            f = new VasWarrantyfragment();
            transaction(f);
        } else if (i == R.id.safety_layout) {
            f = new VasSafetytipsFragment();
            transaction(f);
        } else if (i == R.id.genuineparts_layout) {
            f = new VasGenuinePartsFragment();
            transaction(f);
        }
    }


    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_vasmaintenance, f);
        ft.commit();
    }

    public void fetch_make_model() {
        try {
            String id = "";
            db = new DatabaseHelper(getContext());
            List<Bikemake> allrecords = db.getAllBikemakes();
            for (Bikemake record : allrecords) {
                if (record.getMakename().equalsIgnoreCase("HERO MOTOCORP"))
                    id = record.getId();
            }

            arr_modellist.add("--select--");

            List<Bike_model> records = db.getAllBikemodels();
            for (Bike_model record : records) {
                if (record.getMakeid().equals(id)) {
                    arr_modellist.add(record.getModelname());

                }
            }
            ArrayAdapter<String> at1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr_modellist);
            spin_model.setAdapter(at1);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
        }
    }

}




package com.herocorp.ui.activities.DSEapp.Fragment.PendingFollowup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.CloseFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.FollowupDetailFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.FollowupFragment;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.adapter.Followupadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rsawh on 21-Sep-16.
 */
public class PendingFollowupFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private CustomViewParams customViewParams;
    DatabaseHelper db;

    com.baoyz.swipemenulistview.SwipeMenuListView userList;
    Followupadapter userAdapter;
    ArrayList<Followup> userArray = new ArrayList<Followup>();
    TextView pendingfollowup_msg;
    SwipeMenuCreator creator;
    String user, encryptuser;

    String first_name;
    String last_name;
    String cell_ph_no;
    String age;
    String gender;
    String email_addr;
    String state;
    String district;
    String tehsil;
    String city;
    String x_con_seq_no;
    String x_model_interested;
    String expected_date_purchase;
    String x_exchange_required;
    String x_finance_required;
    String exist_vehicle;
    String followup_comments;
    String enquiry_id;
    String follow_date;
    String enquiry_entry_date;
    String dealer_bu_id;
    String followup_status;
    String priority;
    String two_wheeler_type;
    String rural_urban;
    String occupation;
    String usage;
    String sales_pitch_no;
    Fragment f;
    SwipeRefreshLayout swipe_refresh_followup;
    String sync_date = "";
    String current_date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_pendingfollowup_fragment, container, false);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        initView(rootView);
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem closeItem = new SwipeMenuItem(
                        getContext());
                // set item background
                closeItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                closeItem.setWidth(120);
                closeItem.setTitle("Close");
                closeItem.setTitleSize(18);
                closeItem.setTitleColor(Color.WHITE);
                // set a icon
                // deleteItem.setIcon(R.drawable.cross_icon);
                // add to menu
                menu.addMenuItem(closeItem);

                SwipeMenuItem followupItem = new SwipeMenuItem(
                        getContext());
                // set item background
                /*editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));*/
                followupItem.setBackground(new ColorDrawable(Color.DKGRAY));
                // set item width
                followupItem.setWidth(160);
                // set item title
                followupItem.setTitle("Followup");
                // set item title fontsize
                followupItem.setTitleSize(18);
                // set item title font color
                followupItem.setTitleColor(Color.WHITE);
                // add to menu
                //  editItem.setIcon(R.drawable.edit_icon);
                menu.addMenuItem(followupItem);

            }
        };
        userList.setMenuCreator(creator);
        userList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        userList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                Followup data = userAdapter.getItem(position);
                Bundle bundle = send_bundle(data);
                switch (index) {
                    case 0:
                        // open
                      /*  Toast.makeText(getContext(), "Close mode" + position, Toast.LENGTH_SHORT).show();*/
                        f = new CloseFollowupFragment();
                        f.setArguments(bundle);
                        transaction(f);
                        break;
                    case 1:
                        // Toast.makeText(getContext(), "Edit mode " + position, Toast.LENGTH_SHORT).show();
                        f = new FollowupFragment();
                        f.setArguments(bundle);
                        transaction(f);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Followup data = userAdapter.getItem(position);
                Bundle bundle = send_bundle(data);
                f = new FollowupDetailFragment();
                f.setArguments(bundle);
                transaction(f);
            }
        });
        return rootView;
    }

    private void updateList() {
        userList.setAdapter(userAdapter);
        if (userAdapter.getCount() > 0)
            pendingfollowup_msg.setVisibility(View.GONE);
        else
            pendingfollowup_msg.setVisibility(View.VISIBLE);
        swipe_refresh_followup.setRefreshing(false);
        //progressbar.setVisibility(View.GONE);
    }

    public void onDestroy() {
        // Remove adapter reference from list
//        userList.setAdapter(null);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetch_records();
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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        RelativeLayout topLayout1 = (RelativeLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        pendingfollowup_msg = (TextView) rootView.findViewById(R.id.pendingfollowup_message);

        userAdapter = new Followupadapter(getContext(), R.layout.dse_pendingfollowup_row, userArray);
        userList = (SwipeMenuListView) rootView.findViewById(R.id.list_pendingfollowup);
        customViewParams.setMarginAndPadding(userList, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, userList.getParent());

        swipe_refresh_followup = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_followup);
        customViewParams.setMarginAndPadding(swipe_refresh_followup, new int[]{100, 30, 100, 40}, new int[]{0, 0, 0, 0}, swipe_refresh_followup.getParent());

        userList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userList.getChildAt(0) != null) {
                    swipe_refresh_followup.setEnabled(userList.getFirstVisiblePosition() == 0 && userList.getChildAt(0).getTop() == 0);
                }
            }
        });


        swipe_refresh_followup.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipe_refresh_followup.setRefreshing(true);
                                            current_date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
                                            fetch_records();
                                            if (!PreferenceUtil.get_Syncdate(getContext()).equalsIgnoreCase(current_date.toString()) && NetConnections.isConnected(getContext())) {
                                                swipe_refresh_followup.setRefreshing(true);
                                                new SyncFollowup1(getContext()).execute();
                                                // fetch_records();
                                            }
                                        }
                                    }
        );

        menu.setOnClickListener(this);
        swipe_refresh_followup.setOnRefreshListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        }
    }

    public void fetch_records() {
        try {
            db = new DatabaseHelper(getContext());
            // List<Followup> allrecords = db.getAllFollowups();
            List<Followup> allrecords = db.getNewAllFollowups();
            userAdapter.clear();
            ArrayList<Followup> listhot = new ArrayList<Followup>();
            ArrayList<Followup> listwarm = new ArrayList<Followup>();
            ArrayList<Followup> listcold = new ArrayList<Followup>();

            for (Followup record : allrecords) {
                first_name = record.getFirst_name();
                last_name = record.getLast_name();
                cell_ph_no = record.getCell_ph_no();
                age = record.getAge();
                gender = record.getGender();
                email_addr = record.getEmail_addr();
                state = record.getState();
                district = record.getDistrict();
                tehsil = record.getTehsil();
                city = record.getCity();
                x_con_seq_no = record.getX_con_seq_no();
                x_model_interested = record.getX_model_interested();
                expected_date_purchase = record.getExpcted_date_purchase();
                x_exchange_required = record.getX_exchange_required();
                x_finance_required = record.getX_finance_required();
                exist_vehicle = record.getExist_vehicle();
                followup_comments = record.getFollowup_comments();
                enquiry_id = record.getEnquiry_id();
                follow_date = record.getFollow_date();
                enquiry_entry_date = record.getEnquiry_entry_date();
                dealer_bu_id = record.getDealer_bu_id();
                followup_status = record.getFollowup_status();
                priority = record.getPriority();
                two_wheeler_type = record.getTwo_wheeler_type();
                rural_urban = record.getRural_urban();
                occupation = record.getOccupation();
                usage = record.getUsage();
                priority = record.getPriority();
                two_wheeler_type = record.getTwo_wheeler_type();
                rural_urban = record.getRural_urban();
                occupation = record.getOccupation();
                usage = record.getUsage();
                sales_pitch_no = record.getSales_pitch_no();
                if (priority.equalsIgnoreCase("Hot"))
                    listhot.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                            expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, priority, two_wheeler_type, rural_urban, occupation, usage, sales_pitch_no, followup_status));
                else if (priority.equalsIgnoreCase("Warm"))
                    listwarm.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                            expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, priority, two_wheeler_type, rural_urban, occupation, usage, sales_pitch_no, followup_status));
                else if (priority.equalsIgnoreCase("Cold"))
                    listcold.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                            expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, priority, two_wheeler_type, rural_urban, occupation, usage, sales_pitch_no, followup_status));
                else
                    userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                            expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, priority, two_wheeler_type, rural_urban, occupation, usage, sales_pitch_no, followup_status));

               /* userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                        expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
              */
            }

            //for hot
            if (listhot.size() > 0) {
                for (int i = 0; i < listhot.size(); i++) {
                    userAdapter.add(new Followup(listhot.get(i).getFirst_name(), listhot.get(i).getLast_name(), listhot.get(i).getCell_ph_no(), listhot.get(i).getAge(), listhot.get(i).getGender(), listhot.get(i).getEmail_addr(), listhot.get(i).getState(), listhot.get(i).getDistrict(), listhot.get(i).getTehsil(), listhot.get(i).getCity(), listhot.get(i).getX_con_seq_no(), listhot.get(i).getX_model_interested(),
                            listhot.get(i).getExpcted_date_purchase(), listhot.get(i).getX_exchange_required(), listhot.get(i).getX_finance_required(), listhot.get(i).getExist_vehicle(), listhot.get(i).getFollowup_comments(), listhot.get(i).getEnquiry_id(), listhot.get(i).getFollow_date(), listhot.get(i).getEnquiry_entry_date(), listhot.get(i).getDealer_bu_id(), listhot.get(i).getPriority(), listhot.get(i).getTwo_wheeler_type(), listhot.get(i).getRural_urban(), listhot.get(i).getOccupation(), listhot.get(i).getUsage(), listhot.get(i).getSales_pitch_no(), listhot.get(i).getFollowup_status()));

                }
            }
            //for warm
            if (listwarm.size() > 0) {
                for (int i = 0; i < listwarm.size(); i++) {
                    userAdapter.add(new Followup(listwarm.get(i).getFirst_name(), listwarm.get(i).getLast_name(), listwarm.get(i).getCell_ph_no(), listwarm.get(i).getAge(), listwarm.get(i).getGender(), listwarm.get(i).getEmail_addr(), listwarm.get(i).getState(), listwarm.get(i).getDistrict(), listwarm.get(i).getTehsil(), listwarm.get(i).getCity(), listwarm.get(i).getX_con_seq_no(), listwarm.get(i).getX_model_interested(),
                            listwarm.get(i).getExpcted_date_purchase(), listwarm.get(i).getX_exchange_required(), listwarm.get(i).getX_finance_required(), listwarm.get(i).getExist_vehicle(), listwarm.get(i).getFollowup_comments(), listwarm.get(i).getEnquiry_id(), listwarm.get(i).getFollow_date(), listwarm.get(i).getEnquiry_entry_date(), listwarm.get(i).getDealer_bu_id(), listwarm.get(i).getPriority(), listwarm.get(i).getTwo_wheeler_type(), listwarm.get(i).getRural_urban(), listwarm.get(i).getOccupation(), listwarm.get(i).getUsage(), listwarm.get(i).getSales_pitch_no(), listwarm.get(i).getFollowup_status()));

                }
            }

            //for cold
            if (listcold.size() > 0) {
                for (int i = 0; i < listcold.size(); i++) {
                    userAdapter.add(new Followup(listcold.get(i).getFirst_name(), listcold.get(i).getLast_name(), listcold.get(i).getCell_ph_no(), listcold.get(i).getAge(), listcold.get(i).getGender(), listcold.get(i).getEmail_addr(), listcold.get(i).getState(), listcold.get(i).getDistrict(), listcold.get(i).getTehsil(), listcold.get(i).getCity(), listcold.get(i).getX_con_seq_no(), listcold.get(i).getX_model_interested(),
                            listcold.get(i).getExpcted_date_purchase(), listcold.get(i).getX_exchange_required(), listcold.get(i).getX_finance_required(), listcold.get(i).getExist_vehicle(), listcold.get(i).getFollowup_comments(), listcold.get(i).getEnquiry_id(), listcold.get(i).getFollow_date(), listcold.get(i).getEnquiry_entry_date(), listcold.get(i).getDealer_bu_id(), listcold.get(i).getPriority(), listcold.get(i).getTwo_wheeler_type(), listcold.get(i).getRural_urban(), listcold.get(i).getOccupation(), listcold.get(i).getUsage(), listcold.get(i).getSales_pitch_no(), listcold.get(i).getFollowup_status()));

                }
            }
            userAdapter.notifyDataSetChanged();
            updateList();
        } catch (Exception e) {
            swipe_refresh_followup.setRefreshing(false);
            e.printStackTrace();
            Toast.makeText(getContext(), "Server Error !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void transaction(final Fragment f) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_pendingfollowup, f);
        ft.commit();
    }

    @Override
    public void onRefresh() {
        fetch_records();
    }

  /*  @Override
    public void onBackStackChanged() {
        onRefresh();
    }*/

    public class SyncFollowup extends AsyncTask<Void, Void, String> {
        DatabaseHelper db;
        NetworkConnect networkConnect;
        String result;
        Context context;
        String first_name;
        String last_name;
        String cell_ph_no;
        String age;
        String gender;
        String email_addr;
        String state;
        String district;
        String tehsil;
        String city;
        String x_con_seq_no;
        String x_model_interested;
        String expected_date_purchase;
        String x_exchange_required;
        String x_finance_required;
        String exist_vehicle;
        String followup_comments;
        String enquiry_id;
        String follow_date;
        String enquiry_entry_date;
        String dealer_bu_id;

        private SharedPreferences sharedPreferences;


        public SyncFollowup(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            try {
                final JSONObject jsonparams = new JSONObject();
                jsonparams.put("user_id", PreferenceUtil.get_UserId(context));
                //jsonparams.put("user_id", "DSE10866");
                Log.e("followup_sync_start:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                String newurlparams = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
                String data = networkConnect.execute();
                String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.PENDING_FOLLOWUP, urldata);
                result = networkConnect.execute();
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                Log.e("response_followup:", result);
                db = new DatabaseHelper(context);
                db.clearfollowup_table();
                JSONObject jsono = null;
                JSONArray jarray = null;
                try {
                    jsono = new JSONObject(result);
                    if (jsono.has("follow_up")) {
                        jarray = jsono.getJSONArray("follow_up");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            first_name = object.getString("FST_NAME");
                            last_name = object.getString("LAST_NAME");
                            cell_ph_no = object.getString("CELL_PH_NUM");
                            age = object.getString("AGE");
                            gender = object.getString("GENDER");
                            email_addr = object.getString("EMAIL_ADDR");
                            state = object.getString("STATE");
                            district = object.getString("DISTRICT");
                            tehsil = object.getString("TEHSIL");
                            city = object.getString("CITY");
                            x_con_seq_no = object.getString("X_CON_SEQ_NUM");
                            x_model_interested = object.getString("X_MODEL_INTERESTED");
                            expected_date_purchase = object.getString("EXPCTD_DT_PURCHASE");
                            x_exchange_required = object.getString("X_EXCHANGE_REQUIRED");
                            x_finance_required = object.getString("X_FINANCE_REQUIRED");
                            exist_vehicle = object.getString("EXISTING_VEHICLE");
                            followup_comments = object.getString("FOLLOWUP_COMMENTS");
                            enquiry_id = object.getString("ENQUIRY_ID");
                            follow_date = object.getString("FOLLOW_DATE");
                            enquiry_entry_date = object.getString("ENQUIRY_ENTRY_DATE");
                            dealer_bu_id = object.getString("DEALER_BU_ID");
                            db.addfollowup(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, "0"));
                        }
                    }
                    PreferenceUtil.set_Syncdate(context, new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                    Log.e("followup_sync_end:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                    onRefresh();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(context, "Check your Connection !!", Toast.LENGTH_SHORT);
            }
        }
    }

    public class SyncFollowup1 extends AsyncTask<Void, Void, String> {
        DatabaseHelper db;
        NetworkConnect networkConnect;
        String result;
        Context context;
        String first_name;
        String last_name;
        String cell_ph_no;
        String age;
        String gender;
        String email_addr;
        String state;
        String district;
        String tehsil;
        String city;
        String x_con_seq_no;
        String x_model_interested;
        String expected_date_purchase;
        String x_exchange_required;
        String x_finance_required;
        String exist_vehicle;
        String followup_comments;
        String enquiry_id;
        String follow_date;
        String enquiry_entry_date;
        String dealer_bu_id;
        String priority = "";
        String two_wheeler_type = "";
        String rural_urban = "";
        String occupation = "";
        String usage = "";
        String sales_pitch_no = "";

        private SharedPreferences sharedPreferences;


        public SyncFollowup1(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            try {
                final JSONObject jsonparams = new JSONObject();
                jsonparams.put("user_id", PreferenceUtil.get_UserId(context));
                //jsonparams.put("user_id", "ROBIN1234");
                Log.e("followup_sync_start:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                String newurlparams = "data=" + URLEncoder.encode(jsonparams.toString(), "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.ENCRYPT, newurlparams);
                String data = networkConnect.execute();
                String urldata = "data=" + URLEncoder.encode(data, "UTF-8");
                networkConnect = new NetworkConnect(URLConstants.PENDING_FOLLOWUP, urldata);
                result = networkConnect.execute();
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                Log.e("response_followup:", result);
                db = new DatabaseHelper(context);
                db.clearfollowup_table();
                JSONObject jsono = null;
                JSONArray jarray = null;
                try {
                    jsono = new JSONObject(result);
                    if (jsono.has("follow_up")) {
                        jarray = jsono.getJSONArray("follow_up");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            first_name = object.getString("FST_NAME");
                            last_name = object.getString("LAST_NAME");
                            cell_ph_no = object.getString("CELL_PH_NUM");
                            age = object.getString("AGE");
                            gender = object.getString("GENDER");
                            email_addr = object.getString("EMAIL_ADDR");
                            state = object.getString("STATE");
                            district = object.getString("DISTRICT");
                            tehsil = object.getString("TEHSIL");
                            city = object.getString("CITY");
                            x_con_seq_no = object.getString("X_CON_SEQ_NUM");
                            x_model_interested = object.getString("X_MODEL_INTERESTED");
                            expected_date_purchase = object.getString("EXPCTD_DT_PURCHASE");
                            x_exchange_required = object.getString("X_EXCHANGE_REQUIRED");
                            x_finance_required = object.getString("X_FINANCE_REQUIRED");
                            exist_vehicle = object.getString("EXISTING_VEHICLE");
                            followup_comments = object.getString("FOLLOWUP_COMMENTS");
                            enquiry_id = object.getString("ENQUIRY_ID");
                            follow_date = object.getString("FOLLOW_DATE");
                            enquiry_entry_date = object.getString("ENQUIRY_ENTRY_DATE");
                            dealer_bu_id = object.getString("DEALER_BU_ID");
                            if (object.has("PRIORITY")) {
                                priority = object.getString("PRIORITY");
                            }
                            if (object.has("TWO_WHEELER_TYPE")) {
                                two_wheeler_type = object.getString("TWO_WHEELER_TYPE");
                            }
                            if (object.has("RURAL_URBAN")) {
                                rural_urban = object.getString("RURAL_URBAN");
                            }
                            if (object.has("OCCUPATION")) {
                                occupation = object.getString("OCCUPATION");
                            }
                            if (object.has("USAGES")) {
                                usage = object.getString("USAGES");
                            }
                            if (object.has("sales_pitch_no"))
                                sales_pitch_no = object.getString("sales_pitch_no");
                         /*   db.addfollowup(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, "0"));
*/
                            db.addNewfollowup(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, priority, two_wheeler_type, rural_urban, occupation, usage, sales_pitch_no, "0"));

                        }
                    }
                    PreferenceUtil.set_Syncdate(context, new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                    Log.e("followup_sync_end:", new SimpleDateFormat("dd-MMM-yy").format(new Date()).toString());
                    onRefresh();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(context, "Check your Connection !!", Toast.LENGTH_SHORT);
            }
        }
    }

    public Bundle send_bundle(Followup data) {
        Bundle bundle = new Bundle();
        bundle.putString("fname", data.getFirst_name());
        bundle.putString("lname", data.getLast_name());
        bundle.putString("mobile", data.getCell_ph_no());
        bundle.putString("age", data.getAge());
        bundle.putString("sex", data.getGender());
        bundle.putString("email", data.getEmail_addr());
        bundle.putString("state", data.getState());
        bundle.putString("district", data.getDistrict());
        bundle.putString("tehsil", data.getTehsil());
        bundle.putString("city", data.getCity());
        bundle.putString("model", data.getX_model_interested());
        bundle.putString("id", data.getX_con_seq_no());
        bundle.putString("pur_date", data.getExpcted_date_purchase());
        bundle.putString("exchange", data.getX_exchange_required());
        bundle.putString("finance", data.getX_finance_required());
        bundle.putString("vtype", data.getExist_vehicle());
        bundle.putString("comment", data.getFollowup_comments());
        bundle.putString("followdate", data.getFollow_date());
        bundle.putString("enquiryid", data.getEnquiry_id());
        bundle.putString("user", PreferenceUtil.get_UserId(getContext()));
        bundle.putString("twowheelertype", data.getTwo_wheeler_type());
        bundle.putString("area", data.getRural_urban());
        bundle.putString("occupation", data.getOccupation());
        bundle.putString("usage", data.getUsage());
        bundle.putString("sales_pitch_no",data.getSales_pitch_no());
        return bundle;
    }
}

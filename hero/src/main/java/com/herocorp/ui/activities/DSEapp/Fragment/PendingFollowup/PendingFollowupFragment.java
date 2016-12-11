package com.herocorp.ui.activities.DSEapp.Fragment.PendingFollowup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.herocorp.ui.activities.DSEapp.SyncFollowup;
import com.herocorp.ui.activities.DSEapp.adapter.Followupadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.interfaces.PageRefreshListener;
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

    private Handler handler = new Handler();
    com.baoyz.swipemenulistview.SwipeMenuListView userList;
    Followupadapter userAdapter;
    ArrayList<Followup> userArray = new ArrayList<Followup>();
    NetworkConnect networkConnect;
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
    Fragment f;
    PageRefreshListener refresh;
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
                Bundle bundle = new Bundle();
                bundle.putString("user_id", encryptuser);
                bundle.putString("user", PreferenceUtil.get_UserId(getContext()));
                bundle.putString("enquiry_id", data.getEnquiry_id());
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

        swipe_refresh_followup.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipe_refresh_followup.setRefreshing(true);
                                            current_date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
                                            fetch_records();
                                            if ((!PreferenceUtil.get_Syncdate(getContext()).equalsIgnoreCase(current_date.toString()) && NetConnections.isConnected(getContext()))) {

                                                new SyncFollowup(getContext()).execute();
                                                fetch_records();
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
            swipe_refresh_followup.setRefreshing(true);
            db = new DatabaseHelper(getContext());
            List<Followup> allrecords = db.getAllFollowups();
            userAdapter.clear();
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
                userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                        expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                userAdapter.notifyDataSetChanged();
            }
            updateList();

        } catch (Exception e) {
            swipe_refresh_followup.setRefreshing(false);
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
}

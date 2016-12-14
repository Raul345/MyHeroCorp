package com.herocorp.ui.activities.DSEapp.Fragment.TodayFollowup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.herocorp.R;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.CloseFollowupFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.FollowupDetailFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Followup.FollowupFragment;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.DSEapp.Fragment.Search.SearchfilterFragment;
import com.herocorp.ui.activities.DSEapp.Utilities.SyncFollowup;
import com.herocorp.ui.activities.DSEapp.adapter.Followupadapter;
import com.herocorp.ui.activities.DSEapp.db.DatabaseHelper;
import com.herocorp.ui.activities.DSEapp.models.Followup;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rsawh on 21-Sep-16.
 */
public class TodayFollowupFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private CustomViewParams customViewParams;
    DatabaseHelper db;


    private Handler handler = new Handler();
    com.baoyz.swipemenulistview.SwipeMenuListView userList;
    Followupadapter userAdapter;
    ArrayList<Followup> userArray = new ArrayList<Followup>();
    ProgressBar progressbar;
    TextView todayfollowup_msg;
    SwipeMenuCreator creator;
    NetworkConnect networkConnect;

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

    String encryptuser, user;
    String sel_model = "", filter = "";
    String fromdate = "", todate = "", followdate = "";
    Date start, end;
    int check = 0, flag = 0;
    String sync_date = "";
    String current_date;

    Fragment f;
    SwipeRefreshLayout swipe_refresh_followup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.dse_todayfollowup_fragment, container, false);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        initView(rootView);

        //creating swiping menu for listview
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
                bundle.putString("user", user);
                bundle.putString("enquiry_id", data.getEnquiry_id());
                bundle.putString("pur_date",data.getExpcted_date_purchase());

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
                bundle.putString("dealerid", data.getDealer_bu_id());
                bundle.putString("user_id", encryptuser);
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
            todayfollowup_msg.setVisibility(View.GONE);
        else {
            todayfollowup_msg.setVisibility(View.VISIBLE);
            if (check != 0)
                todayfollowup_msg.setText("No Enquiry Found !!");

        }
        swipe_refresh_followup.setRefreshing(false);
    }

    public void onDestroy() {
        // Remove adapter reference from list
//        userList.setAdapter(null);
        super.onDestroy();
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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{60, 0, 0, 0}, 90, 180, 40, customTypeFace.gillSansItalic, 0);

        RelativeLayout topLayout1 = (RelativeLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, topLayout1.getParent());
        //   FloatingActionButton filterbutton = (FloatingActionButton) rootView.findViewById(R.id.button_filter);

        ImageView filterbutton = (ImageView) rootView.findViewById(R.id.imageView_filter);

        todayfollowup_msg = (TextView) rootView.findViewById(R.id.todayfollowup_message);
        userAdapter = new Followupadapter(getContext(), R.layout.dse_pendingfollowup_row, userArray);
        userList = (SwipeMenuListView) rootView.findViewById(R.id.list_todayfollowup);
        customViewParams.setMarginAndPadding(userList, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, userList.getParent());

        swipe_refresh_followup = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_followup);
        customViewParams.setMarginAndPadding(swipe_refresh_followup, new int[]{100, 30, 100, 40}, new int[]{0, 0, 0, 0}, swipe_refresh_followup.getParent());

        //fetch_records();

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
                                            if ((!PreferenceUtil.get_Syncdate(getContext()).equalsIgnoreCase(current_date.toString()) && NetConnections.isConnected(getContext()))) {
                                                swipe_refresh_followup.setRefreshing(true);
                                                new SyncFollowup(getContext()).execute();
                                                fetch_records();
                                            }
                                        }
                                    }
        );
        try {
            Bundle bundle = this.getArguments();
           /* encryptuser = bundle.getString("user_id");
            user = bundle.getString("user");*/
            fromdate = bundle.getString("fromdate");
            todate = bundle.getString("todate");
            followdate = bundle.getString("followdate");
            sel_model = bundle.getString("model");
            filter = bundle.getString("filter");
            check = bundle.getInt("check");
            flag = bundle.getInt("flag");

            if (check != 0) {
                buttonHeader.setText("  SEARCH ENQUIRY");

            }
            if (flag != 0) {
                filterbutton.setVisibility(View.VISIBLE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        menu.setOnClickListener(this);
        filterbutton.setOnClickListener(this);
        swipe_refresh_followup.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        } else if (i == R.id.imageView_filter) {

            Bundle bundle = new Bundle();
            bundle.putString("user_id", encryptuser);
            f = new SearchfilterFragment();
            f.setArguments(bundle);
            transaction(f);
        }
    }


    public void transaction(final Fragment f) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.content_todayfollowup, f);
        ft.commit();
    }


    public void fetch_records() {
        try {
            swipe_refresh_followup.setRefreshing(false);
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
                Date expt_purc_date = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH)
                        .parse(expected_date_purchase);

                if (check == 0) {
                    String date = new SimpleDateFormat("dd-MMM-yy").format(new Date());
                    if (follow_date.equalsIgnoreCase(date)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                  /*  userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                            expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
*/
                } else if (check == 1) {
                    userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                            expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                } else if (check == 2) {
                    if (x_finance_required.equalsIgnoreCase("Y")) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 3) {
                    if (x_finance_required.equalsIgnoreCase("N")) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 4) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && followdate.equalsIgnoreCase(follow_date) && sel_model.equalsIgnoreCase(x_model_interested)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 5) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && followdate.equalsIgnoreCase(follow_date) && sel_model.equalsIgnoreCase(x_model_interested)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 6) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && followdate.equalsIgnoreCase(follow_date) && sel_model.equalsIgnoreCase(x_model_interested)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 7) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && followdate.equalsIgnoreCase(follow_date)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 8) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && followdate.equalsIgnoreCase(follow_date)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 9) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && followdate.equalsIgnoreCase(follow_date)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 10) {
                    if (followdate.equalsIgnoreCase(follow_date)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 11) {
                    if (followdate.equalsIgnoreCase(follow_date)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 12) {
                    if (followdate.equalsIgnoreCase(follow_date)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }

                } else if (check == 13) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }

                } else if (check == 14) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }

                } else if (check == 15) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }

                } else if (check == 16) {
                    if (sel_model.equalsIgnoreCase(x_model_interested)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));

                    }

                } else if (check == 17) {
                    if (sel_model.equalsIgnoreCase(x_model_interested)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));

                    }

                } else if (check == 18) {
                    if (sel_model.equalsIgnoreCase(x_model_interested)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));

                    }

                } else if (check == 19) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && sel_model.equalsIgnoreCase(x_model_interested)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 20) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && sel_model.equalsIgnoreCase(x_model_interested)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }

                } else if (check == 21) {
                    convertdate();
                    if (expt_purc_date.after(start) && expt_purc_date.before(end) && sel_model.equalsIgnoreCase(x_model_interested)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 22) {
                    if (sel_model.equalsIgnoreCase(x_model_interested) && followdate.equalsIgnoreCase(follow_date)) {
                        userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));

                    }
                } else if (check == 23) {
                    if (sel_model.equalsIgnoreCase(x_model_interested) && followdate.equalsIgnoreCase(follow_date)) {
                        if (x_finance_required.equals("Y"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));
                    }
                } else if (check == 24) {
                    if (sel_model.equalsIgnoreCase(x_model_interested) && followdate.equalsIgnoreCase(follow_date)) {
                        if (x_finance_required.equals("N"))
                            userAdapter.add(new Followup(first_name, last_name, cell_ph_no, age, gender, email_addr, state, district, tehsil, city, x_con_seq_no, x_model_interested,
                                    expected_date_purchase, x_exchange_required, x_finance_required, exist_vehicle, followup_comments, enquiry_id, follow_date, enquiry_entry_date, dealer_bu_id, followup_status));

                    }
                }
                userAdapter.notifyDataSetChanged();
            }
            updateList();
        } catch (Exception e) {
            swipe_refresh_followup.setRefreshing(false);
            Toast.makeText(getContext(), "Check your Connection !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void convertdate() throws ParseException {
        start = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH).parse(fromdate);
        end = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH)
                .parse(todate);
    }

    @Override
    public void onRefresh() {
        fetch_records();
    }
}

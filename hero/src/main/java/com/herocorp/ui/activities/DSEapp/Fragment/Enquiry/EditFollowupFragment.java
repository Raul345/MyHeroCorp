package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.herocorp.R;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.activities.DSEapp.adapter.Editenquiryadapter;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.PreferenceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class EditFollowupFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    PagerSlidingTabStrip tabsStrip;
    String user, encryptuser, enquiryid;
    String first_name = "";
    String last_name = "";
    String cell_ph_no = "";
    String age = "";
    String gender = "";
    String email_addr = "";
    String existvehicle = "";
    String existmake = "";
    String purchase = "";
    String occupation = "";
    String area = "";
    String usage = "";
    SharedPreferences mypref;
    int page_flag;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.dse_editfollowup_fragment, container, false);
        mypref = getActivity().getSharedPreferences("herocorp", 0);

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
        customViewParams.setMarginAndPadding(topLayout1, new int[]{100, 50, 100, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_editenquiry);
       /* fetch_data1();
        fetch_data2();*/
        Bundle bundle = new Bundle();
        send_data(bundle);
        if (PreferenceUtil.get_Mode(getContext()).equals("1") || PreferenceUtil.get_Mode(getContext()).equals("1"))
            buttonHeader.setText("ADD ENQUIRY");
        else
            buttonHeader.setText("EDIT ENQUIRY");

        viewPager.setAdapter(new Editenquiryadapter(getChildFragmentManager(), bundle, getContext()));

        if (mypref.contains("page_flag")) {
            page_flag = mypref.getInt("page_flag", 0);
        }

        if (page_flag == 1)
            viewPager.setCurrentItem(1);

        viewPager.setOffscreenPageLimit(1);

        // Give the PagerSlidingTabStrip the ViewPager
        tabsStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs_editenquiry);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                              @Override
                                              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                  if (position == 1) {
                                                      fetch_data1();
                                                      if (first_name.equalsIgnoreCase("")) {
                                                          alert("FirstName cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (last_name.equalsIgnoreCase("")) {
                                                          alert("LastName cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (purchase.equalsIgnoreCase("")) {
                                                          alert("Purchase cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (!(email_addr.equalsIgnoreCase("")) && !emailValidator(email_addr)) {
                                                          alert("Invalid Email Address");
                                                          viewPager.setCurrentItem(0);
                                                          //  tabsStrip.setViewPager(viewPager);
                                                      } else if (age.equalsIgnoreCase("")) {
                                                          alert("Age cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                          // tabsStrip.setViewPager(viewPager);
                                                      } else if (gender.equalsIgnoreCase("")) {
                                                          alert("Gender cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (occupation.equalsIgnoreCase("")) {
                                                          alert("Occupation cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (area.equalsIgnoreCase("")) {
                                                          alert("Rural/Urban cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (usage.equalsIgnoreCase("")) {
                                                          alert("Usage cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (existvehicle.equalsIgnoreCase("")) {
                                                          alert("Existing Vehicle cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      } else if (existvehicle.equalsIgnoreCase("Two wheeler") && existmake.equals("")) {
                                                          alert("Make cannot be empty!!");
                                                          viewPager.setCurrentItem(0);
                                                      }
                                                  }

                                              }

                                              @Override
                                              public void onPageSelected(int position) {

                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int state) {

                                              }
                                          }

        );


        menu.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();
        }
    }

 /*   public void fetch_data() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            first_name = bundle.getString("fname");
            last_name = bundle.getString("lname");
            cell_ph_no = bundle.getString("mobile");
            age = bundle.getString("age");
            gender = bundle.getString("sex");
            email_addr = bundle.getString("email");
            state = bundle.getString("state");
            district = bundle.getString("district");
            tehsil = bundle.getString("tehsil");
            city = bundle.getString("city");
            x_con_seq_no = bundle.getString("id");
            x_model_interested = bundle.getString("model");
            expected_date_purchase = bundle.getString("pur_date");
            x_exchange_required = bundle.getString("exchange");
            x_finance_required = bundle.getString("finance");
            existvehicle = bundle.getString("vtype");
            followup_comments = bundle.getString("comment");
            enquiry_id = bundle.getString("enquiryid");
            follow_date = bundle.getString("followdate");
            user = bundle.getString("user");
            encryptuser = bundle.getString("user_id");
            enquiryid = bundle.getString("enquiryid");
        }

    }*/

    public void send_data(Bundle bundle) {

      /*  bundle.putString("user_id", encryptuser);
        bundle.putString("user", user);
        bundle.putString("enquiry_id", enquiryid);*/
       /* bundle.putString("fname", first_name);
        bundle.putString("lname", last_name);
        bundle.putString("mobile", cell_ph_no);
        bundle.putString("age", age);
        bundle.putString("sex", gender);*/
        //  bundle.putString("email", email_addr);
       /* bundle.putString("state", state);
        bundle.putString("district", district);
        bundle.putString("tehsil", tehsil);
        bundle.putString("city", city);
        bundle.putString("model", x_model_interested);
        bundle.putString("id", x_con_seq_no);
        bundle.putString("pur_date", expected_date_purchase);
        bundle.putString("exchange", x_exchange_required);
        bundle.putString("finance", x_finance_required);
        bundle.putString("vtype", existvehicle);
        bundle.putString("comment", followup_comments);
        bundle.putString("followdate", follow_date);
        bundle.putString("enquiryid", enquiry_id);*/
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void fetch_data1() {
        if (mypref.contains("firstname")) {
            first_name = mypref.getString("firstname", "");
        }
        if (mypref.contains("lastname")) {
            last_name = mypref.getString("lastname", "");
        }
        if (mypref.contains("mobile")) {
            cell_ph_no = mypref.getString("mobile", "");
        }
        if (mypref.contains("age")) {
            age = mypref.getString("age", "");
        }
        if (mypref.contains("email")) {
            email_addr = mypref.getString("email", "");
        }
        if (mypref.contains("gender")) {
            gender = mypref.getString("gender", "");
        }
        if (mypref.contains("gender")) {
            existvehicle = mypref.getString("existvehicle", "");
        }
        if (mypref.contains("gender")) {
            existmake = mypref.getString("existmake", "");
        }
        if (mypref.contains("purchase")) {
            purchase = mypref.getString("purchase", "");
        }
        if (mypref.contains("occupation")) {
            occupation = mypref.getString("occupation", "");
        }
        if (mypref.contains("area")) {
            area = mypref.getString("area", "");
        }
        if (mypref.contains("usage")) {
            usage = mypref.getString("usage", "");
        }
    }

    public void alert(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("msg", message);
        bundle.putInt("flag", 0);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        dialogFragment.show(fm, "Sample Fragment");
    }

}

package com.herocorp.ui.activities.DSEapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditEnquiryFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditPersonalInfoFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditaddressFragment;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class Editenquiryadapter extends FragmentPagerAdapter implements
        PagerSlidingTabStrip.IconTabProvider {
    final int PAGE_COUNT = 3;
    private int icons[] = {R.drawable.icon_personalinfo, R.drawable.icon_address, R.drawable.icon_enquiry};
    private final Bundle fragmentBundle;
    //  private String tabTitles[] = new String[]{"GETTING READY TO DONATE", "DONATING YOUR BLOOD", "BLOOD DONATION DONT'S"};

    Context context;

    public Editenquiryadapter(android.support.v4.app.FragmentManager fm, Bundle bundle) {
        super(fm);
        fragmentBundle = bundle;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Fragment f = new Fragment();
        if (position == 0) {
            f = EditPersonalInfoFragment.newInstance(position + 1);
        } else if (position == 1) {
            f = EditaddressFragment.newInstance(position + 1);
        } else if (position == 2) {
            f = EditEnquiryFragment.newInstance(position + 1);
        } else
            f = EditPersonalInfoFragment.newInstance(position + 1);
        f.setArguments(fragmentBundle);
        return f;
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public int getPageIconResId(int position) {
        return icons[position];
    }



}
package com.herocorp.ui.activities.DSEapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.AlertDialogFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditEnquiryFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditPersonalInfoFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.EditaddressFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.NewEnquiryFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Enquiry.NewPersonalInfoFragment;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class Editenquiryadapter extends FragmentStatePagerAdapter implements
        PagerSlidingTabStrip.IconTabProvider {
    final int PAGE_COUNT = 2;
    private int icons[] = {R.drawable.icon_personalinfo, R.drawable.icon_address, R.drawable.icon_enquiry};
    private final Bundle fragmentBundle;
    //  private String tabTitles[] = new String[]{"GETTING READY TO DONATE", "DONATING YOUR BLOOD", "BLOOD DONATION DONT'S"};

    Context context;

    public Editenquiryadapter(android.support.v4.app.FragmentManager fm, Bundle bundle, Context context) {
        super(fm);
        fragmentBundle = bundle;
        this.context = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Fragment f = new Fragment();
        if (position == 0) {
            f = NewPersonalInfoFragment.newInstance(position + 1);
        } /*else if (position == 1) {
            f = EditaddressFragment.newInstance(position + 1);
        }*/ else if (position == 1) {
            f = NewEnquiryFragment.newInstance(position + 1);
        } else
            f = NewPersonalInfoFragment.newInstance(position + 1);
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
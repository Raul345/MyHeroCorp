package com.herocorp.ui.activities.DSEapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.models.Contact;
import com.herocorp.ui.activities.DSEapp.models.EnquiryContact;
import com.herocorp.ui.activities.DSEapp.models.VehicleDetail;
import com.herocorp.ui.activities.DSEapp.models.VinContact;

import java.util.ArrayList;

/**
 * Created by rsawh on 19-Oct-16.
 */
public class VinContactadapter extends ArrayAdapter<Contact> {
        View view;
        Context context;
        int layoutResourceId;
        ArrayList<Contact> data = new ArrayList<Contact>();
        Typeface typeface;

public VinContactadapter(Context context, int layoutResourceId,
                         ArrayList<Contact> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
final UserHolder holder;

        if (row == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(layoutResourceId, null);
        holder = new UserHolder();
        holder.cust_name = (TextView) row.findViewById(R.id.textview_cust_name);
        holder.cell_ph_num = (TextView) row.findViewById(R.id.textview_mobile);
        holder.state = (TextView) row.findViewById(R.id.textview_state);
        holder.district = (TextView) row.findViewById(R.id.textview_district);
        //    typeface = Typeface.createFromAsset(getContext().getAssets(), "DroidSerif-Regular.ttf");
        //   holder.textname.setTypeface(typeface);

        row.setTag(holder);
        } else {
        holder = (UserHolder) row.getTag();
        }
final Contact user = data.get(position);

        holder.cust_name.setText(user.getFst_name() + " " + user.getLast_name());

        holder.cell_ph_num.setPaintFlags(holder.cell_ph_num.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.cell_ph_num.setText(user.getCell_ph_num());

        holder.state.setText(user.getState());
        holder.district.setText(user.getDistrict());
        holder.cell_ph_num.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("mobile", user.getCell_ph_num());
        bundle.putString("header", "Are you Sure?" );
        bundle.putString("msg", "Call this number " + user.getCell_ph_num());
        bundle.putInt("flag", 0);
        FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
        ContactAlertFragment dialogFragment = new ContactAlertFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        dialogFragment.show(fm, "Sample Fragment" );
        }
        });
        return row;

        }

static class UserHolder {
    TextView cust_name;
    TextView cell_ph_num;
    TextView state, district;


}


}
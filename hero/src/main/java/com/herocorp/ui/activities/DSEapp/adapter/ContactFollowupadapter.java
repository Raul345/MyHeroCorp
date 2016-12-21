package com.herocorp.ui.activities.DSEapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.Alert.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.models.Followup;

import java.util.ArrayList;

/**
 * Created by rsawh on 19-Dec-16.
 */

public class ContactFollowupadapter extends ArrayAdapter<Followup> {
    View view;
    Context context;
    int layoutResourceId;
    ArrayList<Followup> data = new ArrayList<Followup>();
    Typeface typeface;

    public ContactFollowupadapter(Context context, int layoutResourceId,
                                  ArrayList<Followup> data) {
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
            holder.x_model_interested = (TextView) row.findViewById(R.id.textview_x_model_interested);
            holder.expctd_dt_purchase = (TextView) row.findViewById(R.id.textview_expctd_dt_purchase);
            holder.follow_date = (TextView) row.findViewById(R.id.textview_follow_date);
            holder.enquiry_date = (TextView) row.findViewById(R.id.textview_enquiry_date);
            holder.status = (ImageView) row.findViewById(R.id.img_contactfollowup_check);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final Followup user = data.get(position);

        Log.e("contact_enquiry", user.getX_model_interested() + user.getEnquiry_entry_date() + user.getExpcted_date_purchase() + (user.getFollow_date() + user.getFollowup_status()));
        holder.x_model_interested.setText(user.getX_model_interested());
        holder.expctd_dt_purchase.setText(user.getExpcted_date_purchase());
        holder.follow_date.setText(user.getFollow_date());
        holder.enquiry_date.setText(user.getEnquiry_entry_date());
        if (user.getFollowup_status().equals("0"))
            holder.status.setImageResource(R.drawable.error_icon);
        else
            holder.status.setImageResource(R.drawable.tick1_image);

        return row;

    }

    static class UserHolder {
        TextView x_model_interested;
        TextView expctd_dt_purchase;
        TextView follow_date;
        TextView enquiry_date;
        ImageView status;

    }


}
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
import android.widget.ImageView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.models.Followup;

import java.util.ArrayList;

/**
 * Created by rsawh on 21-Sep-16.
 */
public class Followupadapter extends ArrayAdapter<Followup> {
    View view;
    Context context;
    int layoutResourceId;
    ArrayList<Followup> data = new ArrayList<Followup>();
    Typeface typeface;

    public Followupadapter(Context context, int layoutResourceId,
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
            holder.cust_name = (TextView) row.findViewById(R.id.textview_cust_name);
            holder.x_model_interested = (TextView) row.findViewById(R.id.textview_x_model_interested);
            holder.cell_ph_num = (TextView) row.findViewById(R.id.textview_cell_ph_num);
            holder.expctd_dt_purchase = (TextView) row.findViewById(R.id.textview_expctd_dt_purchase);
            holder.follow_date = (TextView) row.findViewById(R.id.textview_follow_date);
            holder.enquiry_date = (TextView) row.findViewById(R.id.textview_enquiry_date);
            holder.status = (ImageView) row.findViewById(R.id.img_pendingfollowup_check);

            //    typeface = Typeface.createFromAsset(getContext().getAssets(), "DroidSerif-Regular.ttf");
            //   holder.textname.setTypeface(typeface);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final Followup user = data.get(position);

        holder.cust_name.setText(user.getFirst_name() + " " + user.getLast_name());
        holder.x_model_interested.setText(user.getX_model_interested());
        holder.cell_ph_num.setPaintFlags(holder.cell_ph_num.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.cell_ph_num.setText(user.getCell_ph_no());
        holder.expctd_dt_purchase.setText(user.getExpcted_date_purchase());
        holder.follow_date.setText(user.getFollow_date());
        holder.enquiry_date.setText(user.getEnquiry_entry_date());
        if (user.getFollowup_status().equals("0"))
            holder.status.setImageResource(R.drawable.error_icon);
        else
            holder.status.setImageResource(R.drawable.tick_image);

        holder.cell_ph_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mobile", user.getCell_ph_no());
                bundle.putString("header", "Are you Sure?");
                bundle.putString("msg", "Call this number " + user.getCell_ph_no());
                bundle.putInt("flag", 0);
                FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                ContactAlertFragment dialogFragment = new ContactAlertFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(false);
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        return row;

    }

    static class UserHolder {
        TextView cust_name;
        TextView x_model_interested;
        TextView cell_ph_num;
        TextView expctd_dt_purchase;
        TextView follow_date;
        TextView enquiry_date;
        ImageView status;

    }


}
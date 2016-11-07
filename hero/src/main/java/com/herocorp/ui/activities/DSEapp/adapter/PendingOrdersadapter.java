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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Fragment.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.models.Pendingorder;

import java.util.ArrayList;

/**
 * Created by rsawh on 20-Sep-16.
 */
public class PendingOrdersadapter extends ArrayAdapter<Pendingorder> {
    View view;
    Context context;
    int layoutResourceId;
    ArrayList<Pendingorder> data = new ArrayList<Pendingorder>();
    Typeface typeface;

    public PendingOrdersadapter(Context context, int layoutResourceId,
                                ArrayList<Pendingorder> data) {
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
            holder.model_cd = (TextView) row.findViewById(R.id.textview_model_cd);
            holder.dealer_code = (TextView) row.findViewById(R.id.textview_dealer_code);
            holder.mobile = (TextView) row.findViewById(R.id.textview_mobile);
            holder.order_no = (TextView) row.findViewById(R.id.textview_order_no);
            holder.order_date = (TextView) row.findViewById(R.id.textview_order_date);
            holder.expected_date = (TextView) row.findViewById(R.id.textview_expected_date);
            holder.campaign = (TextView) row.findViewById(R.id.textview_campaign);
            holder.dse_name = (TextView) row.findViewById(R.id.textview_dse_name);
            holder.reason = (TextView) row.findViewById(R.id.textview_reason);
            //    typeface = Typeface.createFromAsset(getContext().getAssets(), "DroidSerif-Regular.ttf");
            //   holder.textname.setTypeface(typeface);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final Pendingorder user = data.get(position);

        holder.cust_name.setText(user.getCust_name());
        holder.order_no.setText(user.getOrder_no());
        holder.dealer_code.setText(user.getDealer_code());

        holder.mobile.setPaintFlags(holder.mobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.mobile.setText(user.getMobile());

        holder.model_cd.setText(user.getModel_cd());
        holder.order_date.setText(user.getOrder_date());

        holder.expected_date.setText(user.getExpected_date());
        holder.campaign.setText(user.getCampaign());

        holder.dse_name.setText(user.getDse_name());
        holder.reason.setText(user.getReason());

        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("mobile",user.getMobile());
                bundle.putString("header", "Are you Sure?");
                bundle.putString("msg", "Call this number " + user.getMobile());
                bundle.putInt("flag",0);
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
        TextView model_cd;
        TextView mobile;
        TextView order_date;
        TextView expected_date;
        TextView dealer_code;
        TextView campaign;
        TextView order_no;
        TextView reason;
        TextView dse_name;
    }


}
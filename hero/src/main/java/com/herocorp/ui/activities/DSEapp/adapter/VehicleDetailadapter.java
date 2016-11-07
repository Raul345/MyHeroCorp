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
import com.herocorp.ui.activities.DSEapp.models.VehicleDetail;

import java.util.ArrayList;

/**
 * Created by rsawh on 24-Oct-16.
 */
public class VehicleDetailadapter extends ArrayAdapter<VehicleDetail> {
    View view;
    Context context;
    int layoutResourceId;
    ArrayList<VehicleDetail> data = new ArrayList<VehicleDetail>();
    Typeface typeface;

    public VehicleDetailadapter(Context context, int layoutResourceId,
                                ArrayList<VehicleDetail> data) {
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
            holder.model = (TextView) row.findViewById(R.id.textview_model);
            holder.dealer = (TextView) row.findViewById(R.id.textview_dealer);
            holder.variant = (TextView) row.findViewById(R.id.textview_variant);
            holder.color = (TextView) row.findViewById(R.id.textview_colour);
            holder.purchasedate = (TextView) row.findViewById(R.id.textview_purchasedate);
            holder.skudesc = (TextView) row.findViewById(R.id.textview_skudesc);
            holder.vinno = (TextView) row.findViewById(R.id.textview_vinno);

            //    typeface = Typeface.createFromAsset(getContext().getAssets(), "DroidSerif-Regular.ttf");
            //   holder.textname.setTypeface(typeface);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final VehicleDetail user = data.get(position);

        holder.model.setText(user.getModel_cd());
        holder.dealer.setText(user.getPrimary_dealer_name());
        holder.variant.setText(user.getAttrib_42());
        holder.color.setText(user.getProd_attrib02_CD());
        holder.purchasedate.setText(user.getFirst_sale_dt());
        holder.skudesc.setText(user.getDesc_text());
        holder.vinno.setText(user.getSerial_num());

        return row;

    }

    static class UserHolder {
        TextView model, dealer, variant, color, purchasedate, vinno, skudesc;


    }


}




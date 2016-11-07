package com.herocorp.ui.activities.DSEapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.models.Campaign;
import com.herocorp.ui.activities.DSEapp.models.Followup;

import java.util.ArrayList;

/**
 * Created by rsawh on 01-Oct-16.
 */
public class Campaignadapter extends ArrayAdapter<Campaign> {
    View view;
    Context context;
    int layoutResourceId;
    ArrayList<Campaign> data = new ArrayList<Campaign>();
    Typeface typeface;

    public Campaignadapter(Context context, int layoutResourceId,
                           ArrayList<Campaign> data) {
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
            holder.campaign_name = (TextView) row.findViewById(R.id.campaignname_textview);
            holder.source = (TextView) row.findViewById(R.id.source_textview);
            holder.status = (TextView) row.findViewById(R.id.status_textview);
            holder.date = (TextView) row.findViewById(R.id.campaigndate_textview);
            holder.category = (TextView) row.findViewById(R.id.category_textview);
            holder.subcategory = (TextView) row.findViewById(R.id.subcategory_textview);
            //    typeface = Typeface.createFromAsset(getContext().getAssets(), "DroidSerif-Regular.ttf");
            //   holder.textname.setTypeface(typeface);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final Campaign user = data.get(position);

        holder.campaign_name.setText(user.getCampaign_name() + "  (" + user.getCamp_type() + ")");
        holder.source.setText(user.getCamp_source());
        holder.status.setText(user.getStatus());
        holder.date.setText(user.getStart_date() + " to " + user.getEnd_date());
        holder.category.setText(user.getCategory());
        holder.subcategory.setText(user.getSub_category());
        return row;

    }

    static class UserHolder {
        TextView campaign_name;
        TextView source;
        TextView status;
        TextView date;
        TextView category;
        TextView subcategory;

    }

}
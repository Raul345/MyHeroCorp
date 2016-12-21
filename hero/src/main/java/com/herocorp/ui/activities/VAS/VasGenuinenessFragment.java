package com.herocorp.ui.activities.VAS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 07-Oct-16.
 */
public class VasGenuinenessFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
   /* FloatingActionButton fab;
*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.vas_genuineness_fragment, container, false);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());
       /* fab=(FloatingActionButton)rootView.findViewById(R.id.fab);
*/
        ImageView close = (ImageView) rootView.findViewById(R.id.close_icon);

        customViewParams.setImageViewCustomParams(close, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{20, 20, 20, 20}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        RelativeLayout topLayout1 = (RelativeLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{10, 0, 0, 10}, new int[]{0, 0, 0, 0}, topLayout1.getParent());
        LinearLayout topLayout2 = (LinearLayout) rootView.findViewById(R.id.top_layout2);
        customViewParams.setMarginAndPadding(topLayout2, new int[]{10, 0, 0, 10}, new int[]{0, 0, 0, 0}, topLayout2.getParent());


        TextView headername = (TextView) rootView.findViewById(R.id.textview_name);
        TextView subheadername = (TextView) rootView.findViewById(R.id.textview_subname);
        customViewParams.setMarginAndPadding(headername, new int[]{60, 0, 0, 0}, new int[]{0, 0, 0, 0}, headername.getParent());
        customViewParams.setMarginAndPadding(subheadername, new int[]{60, 0, 0, 0}, new int[]{0, 0, 0, 0}, subheadername.getParent());

        Button back = (Button) rootView.findViewById(R.id.button_back);
        customViewParams.setButtonCustomParams(back, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 50, 180, 40, customTypeFace.gillSansItalic, 0);

    }

    @Override
    public void onClick(View v) {

    }
}

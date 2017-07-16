package com.herocorp.ui.activities.products;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.herocorp.R;
import com.herocorp.infra.utils.ImageHandler;


/**
 * Created by rsawh on 24-Jun-17.
 */

public class CompareImageFragment extends Fragment {
    View rootView;
    ImageView compare_image;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.layout_compare_image, container, false);
        compare_image = (ImageView) rootView.findViewById(R.id.compareimage);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle bundle = this.getArguments();
        Log.e("img_name", bundle.getString("img_name"));
        //  Resources res = getActivity().getApplicationContext().getResources();
        //   Bitmap bitmap = ((BitmapDrawable) res.getDrawable(bundle.getInt("id"))).getBitmap();

        try {
            compare_image.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(bundle.getString("img_name")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        compare_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }
}

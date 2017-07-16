package com.herocorp.ui.activities.products;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.herocorp.R;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import java.util.ArrayList;

/**
 * Created by rsawh on 05-Oct-16.
 */
public class FeatureCompareFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CustomViewParams customViewParams;
    public ArrayList<ProductCompareModel> compare_images;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout scrollItemContainer;
    private ImageView featureIndividualImage;
    private ImageView arrowLeft, arrowRight;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        rootView = inflater.inflate(R.layout.feature_compare_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        compare_images = ((BaseDrawerActivity) getActivity()).compare_images;
        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {
        customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout1 = (LinearLayout) rootView.findViewById(R.id.top_layout1);
        customViewParams.setMarginAndPadding(topLayout1, new int[]{110, 50, 110, 60}, new int[]{0, 0, 0, 0}, topLayout1.getParent());

        ImageView image_comparefeature = (ImageView) rootView.findViewById(R.id.image_comparefeature);

        featureIndividualImage = (ImageView) rootView.findViewById(R.id.feature_individual_image);
        horizontalScrollView = (HorizontalScrollView) rootView.findViewById(R.id.horizontal_scroll_view);
        scrollItemContainer = (LinearLayout) rootView.findViewById(R.id.scroll_item_container);
        arrowLeft = (ImageView) rootView.findViewById(R.id.left_arrow);
        arrowRight = (ImageView) rootView.findViewById(R.id.right_arrow);
        customViewParams.setImageViewCustomParams(arrowLeft, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 0, 100);
        customViewParams.setImageViewCustomParams(arrowRight, new int[]{20, 0, 30, 0}, new int[]{0, 0, 0, 0}, 0, 100);

        menu.setOnClickListener(this);
        for (int i = 0; i < compare_images.size(); i++) {
            Log.e("iamge", compare_images.get(i).getProductFeatureImages());
        }
        setImagesInScrollView();
        rootView.findViewById(R.id.feature_individual_image_container).setOnClickListener(this);
        arrowLeft.setOnClickListener(this);
        arrowRight.setOnClickListener(this);
    }

    private void setImagesInScrollView() {

        ImageView individualImage;

        if (compare_images.size() <= 1) {

            arrowLeft.setVisibility(View.INVISIBLE);
            arrowLeft.setClickable(false);
            arrowRight.setVisibility(View.INVISIBLE);
            arrowRight.setClickable(false);

        } else {

            arrowLeft.setVisibility(View.VISIBLE);
            arrowLeft.setClickable(true);
            arrowRight.setVisibility(View.VISIBLE);
            arrowRight.setClickable(true);

        }

        for (int i = 0; i < compare_images.size(); i++) {
            try {
                final String imageName = compare_images.get(i).getProductFeatureImages();
                // Resources res = getActivity().getApplicationContext().getResources();
                //    final int id = res.getIdentifier(compare_images.get(i).getProductFeatureImages().replace(".png", ""), "drawable", getActivity().getPackageName());
                individualImage = new ImageView(getActivity());
                individualImage.setId(i);
                // individualImage.setImageDrawable(res.getDrawable(id));
                individualImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(imageName));

               /* Bitmap bitmap = ((BitmapDrawable) res.getDrawable(id)).getBitmap();
                individualImage.setImageBitmap(bitmap);*/
                individualImage.setScaleType(ImageView.ScaleType.FIT_XY);
                scrollItemContainer.addView(individualImage);
                customViewParams.setImageViewCustomParams(individualImage, new int[]{40, 9, 40, 9}, new int[]{9, 9, 9, 9}, 850, 1300);
                final ImageView finalIndividualImage = individualImage;
                individualImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("img_name", imageName);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            Fragment f = new CompareImageFragment();
                            f.setArguments(bundle);
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.addToBackStack(null);
                            ft.add(R.id.content_featurecompare, f);
                            ft.commit();

                         /*   Bitmap bitmap = ((BitmapDrawable) finalIndividualImage.getDrawable()).getBitmap();
                            featureIndividualImage.setImageBitmap(bitmap);
                            rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.VISIBLE);
                       */
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.menu_icon) {
            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (view.getId() == R.id.left_arrow) {

            horizontalScrollView.pageScroll(View.FOCUS_LEFT);

        } else if (view.getId() == R.id.right_arrow) {

            horizontalScrollView.pageScroll(View.FOCUS_RIGHT);

        } else if (view.getId() == R.id.feature_individual_image_container) {

            rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.GONE);

        }


    }

    public void onBackPressed() {

    }

}

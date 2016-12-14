package com.herocorp.ui.activities.products;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.core.models.ProductFeatureModel;
import com.herocorp.infra.db.repositories.product.ProductFeatureRepo;
import com.herocorp.infra.db.tables.schemas.products.ProductFeatureTable;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import java.util.ArrayList;

public class FeaturesFragment extends Fragment implements View.OnClickListener {

    private int productId;
    private CustomViewParams customViewParams;

    //Repository Class
    private ProductFeatureRepo featureRepo;

    //Data Model
    private ArrayList<ProductFeatureModel> featureList;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout scrollItemContainer;
    private ImageView featureIndividualImage;
    private View rootView;
    private ImageView arrowLeft, arrowRight;
    private TextView featureText;

    public FeaturesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.features_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        productId = ((BaseDrawerActivity) getActivity()).productId;

        try {
            featureRepo = new ProductFeatureRepo(getActivity());
            featureList = featureRepo.getRecords(null, ProductFeatureTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{80, 30, 80, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        customViewParams.setMarginAndPadding(rootView.findViewById(R.id.button_layout), new int[]{0, 0, 0, 40}, new int[]{0, 0, 0, 0}, rootView.findViewById(R.id.button_layout).getParent());

        horizontalScrollView = (HorizontalScrollView) rootView.findViewById(R.id.horizontal_scroll_view);

        scrollItemContainer = (LinearLayout) rootView.findViewById(R.id.scroll_item_container);

        arrowLeft = (ImageView) rootView.findViewById(R.id.left_arrow);
        arrowRight = (ImageView) rootView.findViewById(R.id.right_arrow);

        customViewParams.setImageViewCustomParams(arrowLeft, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 0, 100);
        customViewParams.setImageViewCustomParams(arrowRight, new int[]{20, 0, 30, 0}, new int[]{0, 0, 0, 0}, 0, 100);

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);

        Button galleryButton = (Button) rootView.findViewById(R.id.gallery);
        Button featuresButton = (Button) rootView.findViewById(R.id.features);
        Button specsButton = (Button) rootView.findViewById(R.id.specs);
        Button compareButton = (Button) rootView.findViewById(R.id.compare);

        customViewParams.setButtonCustomParams(galleryButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(featuresButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(specsButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 450, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(compareButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);

        /*TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 10}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);*/

        featureIndividualImage = (ImageView) rootView.findViewById(R.id.feature_individual_image);
        featureText = (TextView) rootView.findViewById(R.id.feature_individual_text);

        customViewParams.setImageViewCustomParams(featureIndividualImage, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 580, 600);
        customViewParams.setTextViewCustomParams(featureText, new int[]{0, 0, 0, 0}, new int[]{15, 15, 15, 15}, 40, customTypeFace.gillSans, 0);
        customViewParams.setHeightAndWidth(featureText, 0, 600);

        setImagesInScrollView();

        menu.setOnClickListener(this);

        arrowLeft.setOnClickListener(this);
        arrowRight.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        featuresButton.setOnClickListener(this);
        specsButton.setOnClickListener(this);
        compareButton.setOnClickListener(this);
        rootView.findViewById(R.id.feature_individual_image_container).setOnClickListener(this);

    }

    private void setImagesInScrollView() {

        ImageView individualImage;

        if (featureList.size() <= 3) {

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

        for (int i = 0; i < featureList.size(); i++) {
            try {

                String imageName = featureList.get(i).getFeatureImg();
                individualImage = new ImageView(getActivity());
                individualImage.setId(i);
                individualImage.setBackgroundColor(Color.GRAY);
                individualImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(imageName));
                scrollItemContainer.addView(individualImage);
                customViewParams.setImageViewCustomParams(individualImage, new int[]{45, 9, 45, 9}, new int[]{3, 3, 3, 3}, 380, 380);

                individualImage.setOnClickListener(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.gallery) {

            //  ((BaseDrawerActivity) getActivity()).openFragment(new GalleryFragment(), false);
            getActivity().onBackPressed();

        } else if (view.getId() == R.id.features) {

            //((BaseDrawerActivity) getActivity()).openFragment(new FeaturesFragment(), false);

        } else if (view.getId() == R.id.specs) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new SpecificationsFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_feature, f);
            ft.commit();

            // ((BaseDrawerActivity) getActivity()).openFragment(new SpecificationsFragment(), false);

        } else if (view.getId() == R.id.compare) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new ComparisonFragment();
            FragmentTransaction ft = fm.beginTransaction();
             ft.replace(R.id.content_feature, f);
            ft.commit();

            // ((BaseDrawerActivity) getActivity()).openFragment(new ComparisonFragment(), false);

        } else if (view.getId() == R.id.menu_icon) {

            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (view.getId() == R.id.left_arrow) {

            horizontalScrollView.pageScroll(View.FOCUS_LEFT);

        } else if (view.getId() == R.id.right_arrow) {

            horizontalScrollView.pageScroll(View.FOCUS_RIGHT);

        } else if (view.getId() == R.id.feature_individual_image_container) {

            rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.GONE);

        } else if (view instanceof ImageView) {

            try {
                featureText.setText(featureList.get(view.getId()).getFeatureImgText());
                featureIndividualImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(featureList.get(view.getId()).getFeatureImg()));
                rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(), featureList.get(view.getId()).getFeatureImgText(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}



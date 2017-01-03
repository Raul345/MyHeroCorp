package com.herocorp.ui.activities.products;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;

import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.core.models.ProductColorModel;
import com.herocorp.core.models.ProductGalleryModel;
import com.herocorp.core.models.ProductModel;
import com.herocorp.core.models.ProductRotationModel;
import com.herocorp.infra.db.repositories.product.ProductColorModelRepo;
import com.herocorp.infra.db.repositories.product.ProductGalleryRepo;
import com.herocorp.infra.db.repositories.product.ProductRepo;
import com.herocorp.infra.db.repositories.product.ProductRotationRepo;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.db.tables.schemas.products.ProductColorModelTable;
import com.herocorp.infra.db.tables.schemas.products.ProductGalleryTable;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.Params;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by rsawh on 27-Dec-16.
 */


public class GalleryFragment extends Fragment implements View.OnClickListener {
    int productId;
    private LinearLayout colorImageContainer;
    private CustomViewParams customViewParams;
    private ImageView bikeImage, arrowLeft, arrowRight;
    private TextView colorText, picturesText, threeSixtyText;
    private TextView colorNameText;

    private RelativeLayout layout360, seekbar_container;
    private ImageView bike_image360, close_btn, close_btnn;

    private int currentIndex;

    //Data Model
    private ArrayList<ProductColorModel> colorList;
    private ArrayList<ProductModel> productList;
    private ArrayList<ProductGalleryModel> galleryList;
    private ImageView featureIndividualImage;
    private View rootView;
    private ArrayList<ProductRotationModel> images360;

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.gallery_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        productId = ((BaseDrawerActivity) getActivity()).productId;

        ProductColorModelRepo colorRepo = new ProductColorModelRepo(getActivity());
        ProductRepo productRepo = new ProductRepo(getActivity());
        ProductGalleryRepo galleryRepo = new ProductGalleryRepo(getActivity());

        try {
            images360 = new ProductRotationRepo(getActivity()).getAllRotationImages(productId);
            productList = productRepo.getRecords(null, ProductTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            colorList = colorRepo.getRecords(null, ProductColorModelTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            galleryList = galleryRepo.getRecords(null, ProductGalleryTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        initView(rootView);

        return rootView;
    }

    private void initView(final View rootView) {

        try {

            customViewParams = new CustomViewParams(getActivity());
            CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

            ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
            ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

            customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
            customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

            bikeImage = (ImageView) rootView.findViewById(R.id.bike_image);
            customViewParams.setImageViewCustomParams(bikeImage, new int[]{10, 20, 10, 0}, new int[]{0, 0, 0, 0}, 350, 525);

            colorText = (TextView) rootView.findViewById(R.id.color_text);
            threeSixtyText = (TextView) rootView.findViewById(R.id.three_sixty_text);
            picturesText = (TextView) rootView.findViewById(R.id.picture_text);

            customViewParams.setTextViewCustomParams(colorText, new int[]{70, 0, 15, 0}, new int[]{0, 0, 0, 0}, 40, customTypeFace.gillSansBold, 0);
            customViewParams.setTextViewCustomParams(threeSixtyText, new int[]{15, 0, 10, 0}, new int[]{0, 0, 0, 0}, 40, customTypeFace.gillSansBold, 0);
            customViewParams.setTextViewCustomParams(picturesText, new int[]{10, 0, 70, 0}, new int[]{0, 0, 0, 0}, 40, customTypeFace.gillSansBold, 0);
            customViewParams.setHeightAndWidth(rootView.findViewById(R.id.txt_separator), 40, 5);

            arrowLeft = (ImageView) rootView.findViewById(R.id.left_arrow);
            arrowRight = (ImageView) rootView.findViewById(R.id.right_arrow);

            customViewParams.setImageViewCustomParams(arrowLeft, new int[]{0, 0, 40, 0}, new int[]{0, 0, 0, 0}, 0, 70);
            customViewParams.setImageViewCustomParams(arrowRight, new int[]{40, 0, 0, 0}, new int[]{0, 0, 0, 0}, 0, 70);

            colorNameText = (TextView) rootView.findViewById(R.id.bike_color_name);
            customViewParams.setTextViewCustomParams(colorNameText, new int[]{5, 0, 5, 0}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);

            colorImageContainer = (LinearLayout) rootView.findViewById(R.id.color_box_layout);
            customViewParams.setMarginAndPadding(colorImageContainer, new int[]{10, 0, 10, 0}, new int[]{0, 0, 0, 0}, colorImageContainer.getParent());

            customViewParams.setMarginAndPadding(rootView.findViewById(R.id.text_layout), new int[]{0, 0, 0, 30}, new int[]{0, 0, 0, 0}, colorImageContainer.getParent());

            Button galleryButton = (Button) rootView.findViewById(R.id.gallery);
            Button featuresButton = (Button) rootView.findViewById(R.id.features);
            Button specsButton = (Button) rootView.findViewById(R.id.specs);
            Button compareButton = (Button) rootView.findViewById(R.id.compare);

            customViewParams.setButtonCustomParams(galleryButton, new int[]{0, 10, 0, 10}, new int[]{40, 0, 0, 0}, 80, 350, 40, customTypeFace.gillSansItalic, 0);
            customViewParams.setButtonCustomParams(featuresButton, new int[]{0, 10, 0, 10}, new int[]{40, 0, 0, 0}, 80, 350, 40, customTypeFace.gillSansItalic, 0);
            customViewParams.setButtonCustomParams(specsButton, new int[]{0, 10, 0, 10}, new int[]{40, 0, 0, 0}, 80, 450, 40, customTypeFace.gillSansItalic, 0);
            customViewParams.setButtonCustomParams(compareButton, new int[]{0, 10, 0, 10}, new int[]{40, 0, 0, 0}, 80, 350, 40, customTypeFace.gillSansItalic, 0);

            TextView productName = (TextView) rootView.findViewById(R.id.bike_name);
            customViewParams.setTextViewCustomParams(productName, new int[]{100, 5, 100, 5}, new int[]{0, 0, 0, 0}, 45, customTypeFace.gillSansBold, 0);

            TextView productTag = (TextView) rootView.findViewById(R.id.bike_tag_line);
            customViewParams.setTextViewCustomParams(productTag, new int[]{100, 8, 100, 10}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);

            TextView productDetail = (TextView) rootView.findViewById(R.id.bike_detail_text);
            productDetail.setMovementMethod(new ScrollingMovementMethod());
            productDetail.setLineSpacing((new Params(getResources().getDisplayMetrics()).getSquareViewSize(40)), 0);
            customViewParams.setTextViewCustomParams(productDetail, new int[]{100, 12, 100, 20}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);

            featureIndividualImage = (ImageView) rootView.findViewById(R.id.feature_individual_image);
            customViewParams.setImageViewCustomParams(featureIndividualImage, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 800, 1200);

            bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(colorList.get(0).getImageName()));
            colorNameText.setText(getColorName(colorList.get(0).getImgColorIcon()));
            setProductColors();
            productName.setText(productList.get(0).getProductName());
            productTag.setText(productList.get(0).getProductTag());
            productDetail.setText(productList.get(0).getProductDetails());

            layout360 = (RelativeLayout) rootView.findViewById(R.id.layout360);
            bike_image360 = (ImageView) rootView.findViewById(R.id.bike_image360);
            close_btn = (ImageView) rootView.findViewById(R.id.close_btn);
            close_btnn = (ImageView) rootView.findViewById(R.id.close_btnn);
            seekbar_container = (RelativeLayout) rootView.findViewById(R.id.seekbar_container);
            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorText.setTextColor(getResources().getColor(R.color.color_red));
                    picturesText.setTextColor(Color.WHITE);
                    threeSixtyText.setTextColor(Color.WHITE);
                    layout360.setVisibility(View.GONE);
                }
            });
            close_btnn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.INVISIBLE);
                }
            });

            menu.setOnClickListener(this);
            galleryButton.setOnClickListener(this);
            featuresButton.setOnClickListener(this);
            specsButton.setOnClickListener(this);
            compareButton.setOnClickListener(this);
            colorText.setOnClickListener(this);
            picturesText.setOnClickListener(this);
            threeSixtyText.setOnClickListener(this);
            arrowLeft.setOnClickListener(this);
            arrowRight.setOnClickListener(this);
            bikeImage.setOnClickListener(this);
            rootView.findViewById(R.id.feature_individual_image_container).setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProductColors() {

        try {
            colorImageContainer.removeAllViews();

            for (int i = 0; i < colorList.size(); i++) {

                ImageView imageView = new ImageView(getActivity());
                imageView.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(colorList.get(i).getImgColorIcon()));
                colorImageContainer.addView(imageView);
                customViewParams.setImageViewCustomParams(imageView, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, 65, 65);
                imageView.setId(i);
                imageView.setOnClickListener(this);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        try {

            if (view.getId() == R.id.gallery) {

                //((BaseDrawerActivity) getActivity()).openFragment(new GalleryFragment(), false);

            } else if (view.getId() == R.id.features) {
                // ((BaseDrawerActivity) getActivity()).openFragment(new FeaturesFragment(), false);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment f = new FeaturesFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.content_gallery, f);
                ft.commit();

            } else if (view.getId() == R.id.specs) {

                // ((BaseDrawerActivity) getActivity()).openFragment(new SpecificationsFragment(), false);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment f = new SpecificationsFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.content_gallery, f);
                ft.commit();

            } else if (view.getId() == R.id.compare) {

                // ((BaseDrawerActivity) getActivity()).openFragment(new ComparisonFragment(), false);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment f = new ComparisonFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.content_gallery, f);
                ft.commit();

            } else if (view.getId() == R.id.menu_icon) {

                ((BaseDrawerActivity) getActivity()).toggleDrawer();

            } else if (view.getId() == R.id.color_text) {

                colorText.setTextColor(getResources().getColor(R.color.color_red));
                picturesText.setTextColor(Color.WHITE);
                threeSixtyText.setTextColor(Color.WHITE);
                arrowLeft.setVisibility(View.GONE);
                arrowRight.setVisibility(View.GONE);
                bikeImage.setClickable(true);
                setProductColors();
                colorImageContainer.setVisibility(View.VISIBLE);
                colorNameText.setVisibility(View.VISIBLE);
                bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(colorList.get(0).getImageName()));
                colorNameText.setText(getColorName(colorList.get(0).getImgColorIcon()));

            } else if (view.getId() == R.id.picture_text) {

                colorText.setTextColor(Color.WHITE);
                picturesText.setTextColor(getResources().getColor(R.color.color_red));
                threeSixtyText.setTextColor(Color.WHITE);
                arrowLeft.setVisibility(View.VISIBLE);
                arrowRight.setVisibility(View.VISIBLE);
                colorNameText.setVisibility(View.VISIBLE);
                colorImageContainer.setVisibility(View.INVISIBLE);
                bikeImage.setClickable(true);
                bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(galleryList.get(0).getGalleryImg()));
                colorNameText.setText(getColorName(galleryList.get(0).getGalleryImgText()));
                currentIndex = 0;

            } else if (view.getId() == R.id.three_sixty_text) {
                colorText.setTextColor(Color.WHITE);
                picturesText.setTextColor(Color.WHITE);
                threeSixtyText.setTextColor(getResources().getColor(R.color.color_red));
                layout360.setVisibility(View.VISIBLE);
                setThreeSixty();

              /*  colorText.setTextColor(Color.WHITE);
                picturesText.setTextColor(Color.WHITE);
                threeSixtyText.setTextColor(getResources().getColor(R.color.color_red));
                arrowLeft.setVisibility(View.GONE);
                arrowRight.setVisibility(View.GONE);
                colorImageContainer.removeAllViews();
                colorNameText.setVisibility(View.INVISIBLE);*/

                // colorImageContainer.setVisibility(View.VISIBLE);


            } else if (view.getId() == R.id.left_arrow) {
                if (currentIndex > 0) {
                    currentIndex -= 1;
                    bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(galleryList.get(currentIndex).getGalleryImg()));
                    colorNameText.setText(getColorName(galleryList.get(currentIndex).getGalleryImgText()));
                }

            } else if (view.getId() == R.id.right_arrow) {

                if (currentIndex < galleryList.size()) {
                    currentIndex += 1;
                    bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(galleryList.get(currentIndex).getGalleryImg()));
                    colorNameText.setText(getColorName(galleryList.get(currentIndex).getGalleryImgText()));
                }

            }/* else if (view.getId() == R.id.feature_individual_image_container) {

                rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.GONE);

            } */ else if (view.getId() == R.id.bike_image) {
                try {
                    rootView.findViewById(R.id.feature_individual_image_container).setVisibility(View.VISIBLE);
                    featureIndividualImage.setImageBitmap(((BitmapDrawable) bikeImage.getDrawable()).getBitmap());
                 /* mAttacher = new PhotoViewAttacher();
                    mAttacher.update();*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (view instanceof ImageView) {

                colorNameText.setText(getColorName(colorList.get(view.getId()).getImgColorIcon()));
                bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(colorList.get(view.getId()).getImageName()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getColorName(String colorImageName) {

        if (TextUtils.isDigitsOnly(colorImageName.charAt(0) + ""))
            colorImageName = colorImageName.substring(1);

        if (TextUtils.isDigitsOnly(colorImageName.charAt(0) + ""))
            colorImageName = colorImageName.substring(1);

        colorImageName = colorImageName.replace("-", " ");
        colorImageName = colorImageName.replace(".png", "");

        return colorImageName.trim();

    }

    public void setThreeSixty() {
        ProductRotationModel model = images360.get(0);
        Resources res = getActivity().getApplicationContext().getResources();
        int id = res.getIdentifier(model.getImageName().replace(".png", ""), "drawable", getActivity().getPackageName());

        bike_image360.setImageDrawable(res.getDrawable(id));


        SeekBar seekBar = new SeekBar(getActivity());
        seekBar.setMax(23);
        seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bar_style));
        seekBar.setProgress(0);
        seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bar_style));

        ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
        thumb.setIntrinsicHeight(new Params(getResources().getDisplayMetrics()).getSquareViewSize(60));
        thumb.setIntrinsicWidth(new Params(getResources().getDisplayMetrics()).getSquareViewSize(60));
        seekBar.setThumb(thumb);

        seekbar_container.removeAllViews();
        seekbar_container.addView(seekBar);
        customViewParams.setHeightAndWidth(seekBar, 70, 1000);
        customViewParams.setMarginAndPadding(seekBar, new int[]{0, 10, 0, 10}, new int[]{30, 10, 30, 25}, seekBar.getParent());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                System.out.println(".....111.......");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                System.out.println(".....222.......");
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("**********onProgressChanged**********:" + progress);
                ProductRotationModel model = images360.get(progress);
                Resources res = getActivity().getApplicationContext().getResources();
                int id = res.getIdentifier(model.getImageName().replace(".png", ""), "drawable", getActivity().getPackageName());
                bike_image360.setImageDrawable(res.getDrawable(id));
            }
        });
    }
}

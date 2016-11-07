package com.herocorp.ui.activities.products;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.herocorp.R;
import com.herocorp.infra.db.repositories.product.ProductEngineRepo;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;
import com.herocorp.ui.utility.SpecsHandler;

public class SpecificationsFragment extends Fragment implements View.OnClickListener {

    private int selectedLayoutViewId = -1;

    private View rootView;
    private int productId;
    private ProductEngineRepo engineRepo;
    private CustomViewParams customViewParams;

    private LinearLayout engineLayout, transmissionLayout, suspensionsLayout, brakesLayout, wheelsLayout, electricLayout, dimensionsLayout;
    private ImageView engine, transmission, suspensions, brakes, wheels, electric, dimensions;
    private TextView engineText, transmissionText, suspensionsText, brakesText, wheelsText, electricText, dimensionsText;

    private LinearLayout detailLayout;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.specification_fragment, container, false);

        productId = ((BaseDrawerActivity) getActivity()).productId;

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

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 450, 40, customTypeFace.gillSansItalic, 0);

        LinearLayout specContainerLayout = (LinearLayout) rootView.findViewById(R.id.specification_container_layout);
        customViewParams.setMarginAndPadding(specContainerLayout, new int[]{30, 0, 30, 0}, new int[]{0, 10, 0, 10}, specContainerLayout.getParent());

        scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{40, 5, 40, 5}, new int[]{5, 5, 5, 5}, scrollView.getParent());

        engineLayout = (LinearLayout) rootView.findViewById(R.id.engine_layout);
        transmissionLayout = (LinearLayout) rootView.findViewById(R.id.transmission_layout);
        suspensionsLayout = (LinearLayout) rootView.findViewById(R.id.suspension_layout);
        brakesLayout = (LinearLayout) rootView.findViewById(R.id.brakes_layout);
        wheelsLayout = (LinearLayout) rootView.findViewById(R.id.wheels_layout);
        electricLayout = (LinearLayout) rootView.findViewById(R.id.electric_layout);
        dimensionsLayout = (LinearLayout) rootView.findViewById(R.id.dimensions_layout);

        customViewParams.setMarginAndPadding(engineLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);
        customViewParams.setMarginAndPadding(transmissionLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);
        customViewParams.setMarginAndPadding(suspensionsLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);
        customViewParams.setMarginAndPadding(brakesLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);
        customViewParams.setMarginAndPadding(wheelsLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);
        customViewParams.setMarginAndPadding(electricLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);
        customViewParams.setMarginAndPadding(dimensionsLayout, new int[]{15, 10, 15, 10}, new int[]{0, 0, 0, 0}, engineLayout);

        customViewParams.setHeightAndWidth(engineLayout, 160, 200);
        customViewParams.setHeightAndWidth(transmissionLayout, 160, 200);
        customViewParams.setHeightAndWidth(suspensionsLayout, 160, 200);
        customViewParams.setHeightAndWidth(brakesLayout, 160, 200);
        customViewParams.setHeightAndWidth(wheelsLayout, 160, 200);
        customViewParams.setHeightAndWidth(electricLayout, 160, 200);
        customViewParams.setHeightAndWidth(dimensionsLayout, 160, 200);

        engine = (ImageView) rootView.findViewById(R.id.engine);
        transmission = (ImageView) rootView.findViewById(R.id.transmission);
        suspensions = (ImageView) rootView.findViewById(R.id.suspension);
        brakes = (ImageView) rootView.findViewById(R.id.brakes);
        wheels = (ImageView) rootView.findViewById(R.id.wheels);
        electric = (ImageView) rootView.findViewById(R.id.electric);
        dimensions = (ImageView) rootView.findViewById(R.id.dimensions);

        customViewParams.setImageViewCustomParams(engine, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 90, 90);
        customViewParams.setImageViewCustomParams(transmission, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 90, 90);
        customViewParams.setImageViewCustomParams(suspensions, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 75, 75);
        customViewParams.setImageViewCustomParams(brakes, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 75, 75);
        customViewParams.setImageViewCustomParams(wheels, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 75, 75);
        customViewParams.setImageViewCustomParams(electric, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 75, 75);
        customViewParams.setImageViewCustomParams(dimensions, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 75, 75);

        engineText = (TextView) rootView.findViewById(R.id.engine_text);
        transmissionText = (TextView) rootView.findViewById(R.id.transmission_text);
        suspensionsText = (TextView) rootView.findViewById(R.id.suspension_text);
        brakesText = (TextView) rootView.findViewById(R.id.brakes_text);
        wheelsText = (TextView) rootView.findViewById(R.id.wheels_text);
        electricText = (TextView) rootView.findViewById(R.id.electric_text);
        dimensionsText = (TextView) rootView.findViewById(R.id.dimensions_text);

        customViewParams.setTextViewCustomParams(engineText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(transmissionText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(suspensionsText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(brakesText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(wheelsText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(electricText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(dimensionsText, new int[]{5, 0, 5, 5}, new int[]{0, 0, 0, 0}, 32, customTypeFace.gillSans, 0);

        customViewParams.setMarginAndPadding(rootView.findViewById(R.id.button_layout), new int[]{0, 0, 0, 40}, new int[]{0, 0, 0, 0}, rootView.findViewById(R.id.button_layout).getParent());

        Button galleryButton = (Button) rootView.findViewById(R.id.gallery);
        Button featuresButton = (Button) rootView.findViewById(R.id.features);
        Button specsButton = (Button) rootView.findViewById(R.id.specs);
        Button compareButton = (Button) rootView.findViewById(R.id.compare);

        customViewParams.setButtonCustomParams(galleryButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(featuresButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(specsButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 450, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(compareButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);

        detailLayout = (LinearLayout) rootView.findViewById(R.id.detail_layout);

        /*TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0, 10, 0, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0, 2, 0, 10}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30), null, null, null);*/

        setPreData();

        engineLayout.setOnClickListener(this);
        transmissionLayout.setOnClickListener(this);
        suspensionsLayout.setOnClickListener(this);
        brakesLayout.setOnClickListener(this);
        wheelsLayout.setOnClickListener(this);
        electricLayout.setOnClickListener(this);
        dimensionsLayout.setOnClickListener(this);

        menu.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        featuresButton.setOnClickListener(this);
        specsButton.setOnClickListener(this);
        compareButton.setOnClickListener(this);
    }

    private void setPreData() {

        engineLayout.setBackgroundColor(Color.WHITE);
        engineText.setTextColor(getResources().getColor(R.color.color_red));
        engine.setImageDrawable(getResources().getDrawable(R.drawable.engine_hover));

        selectedLayoutViewId = engineLayout.getId();

        new SpecsHandler(getActivity(), productId, -1).handleEngineData(detailLayout);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.gallery) {

            //((BaseDrawerActivity) getActivity()).openFragment(new GalleryFragment(), false);
            getActivity().onBackPressed();

        } else if (view.getId() == R.id.features) {

            //((BaseDrawerActivity) getActivity()).openFragment(new FeaturesFragment(), false);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new FeaturesFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_specification, f);
            ft.commit();
        } else if (view.getId() == R.id.specs) {

            //((BaseDrawerActivity) getActivity()).openFragment(new SpecificationsFragment(), false);

        } else if (view.getId() == R.id.compare) {

            // ((BaseDrawerActivity) getActivity()).openFragment(new ComparisonFragment(), false);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new ComparisonFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_specification, f);
            ft.commit();

        } else if (view.getId() == R.id.menu_icon) {

            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (view.getId() == R.id.engine_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleEngineData(detailLayout);

        } else if (view.getId() == R.id.transmission_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleTransmissionData(detailLayout);

        } else if (view.getId() == R.id.suspension_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleSuspensionData(detailLayout);

        } else if (view.getId() == R.id.brakes_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleBrakesData(detailLayout);

        } else if (view.getId() == R.id.wheels_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleTyresData(detailLayout);

        } else if (view.getId() == R.id.electric_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleElectricalData(detailLayout);

        } else if (view.getId() == R.id.dimensions_layout) {

            setSelectedOrUnSelected(selectedLayoutViewId, false);
            setSelectedOrUnSelected(view.getId(), true);
            selectedLayoutViewId = view.getId();
            new SpecsHandler(getActivity(), productId, -1).handleDimensionsData(detailLayout);

        }
    }

    private void setSelectedOrUnSelected(int selectedLayoutId, boolean doSelect) {

        if (selectedLayoutId == R.id.engine_layout) {

            if (doSelect) {
                engineLayout.setBackgroundColor(Color.WHITE);
                engineText.setTextColor(getResources().getColor(R.color.color_red));
                engine.setImageDrawable(getResources().getDrawable(R.drawable.engine_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                engineLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                engineText.setTextColor(Color.WHITE);
                engine.setImageDrawable(getResources().getDrawable(R.drawable.engine_normal));
            }

        } else if (selectedLayoutId == R.id.transmission_layout) {

            if (doSelect) {
                transmissionLayout.setBackgroundColor(Color.WHITE);
                transmissionText.setTextColor(getResources().getColor(R.color.color_red));
                transmission.setImageDrawable(getResources().getDrawable(R.drawable.transmission_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                transmissionLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                transmissionText.setTextColor(Color.WHITE);
                transmission.setImageDrawable(getResources().getDrawable(R.drawable.transmission_normal));
            }

        } else if (selectedLayoutId == R.id.suspension_layout) {

            if (doSelect) {
                suspensionsLayout.setBackgroundColor(Color.WHITE);
                suspensionsText.setTextColor(getResources().getColor(R.color.color_red));
                suspensions.setImageDrawable(getResources().getDrawable(R.drawable.suspension_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                suspensionsLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                suspensionsText.setTextColor(Color.WHITE);
                suspensions.setImageDrawable(getResources().getDrawable(R.drawable.suspension_normal));
            }

        } else if (selectedLayoutId == R.id.brakes_layout) {

            if (doSelect) {
                brakesLayout.setBackgroundColor(Color.WHITE);
                brakesText.setTextColor(getResources().getColor(R.color.color_red));
                brakes.setImageDrawable(getResources().getDrawable(R.drawable.brakes_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                brakesLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                brakesText.setTextColor(Color.WHITE);
                brakes.setImageDrawable(getResources().getDrawable(R.drawable.brakes_normal));
            }

        } else if (selectedLayoutId == R.id.wheels_layout) {

            if (doSelect) {
                wheelsLayout.setBackgroundColor(Color.WHITE);
                wheelsText.setTextColor(getResources().getColor(R.color.color_red));
                wheels.setImageDrawable(getResources().getDrawable(R.drawable.wheels_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                wheelsLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                wheelsText.setTextColor(Color.WHITE);
                wheels.setImageDrawable(getResources().getDrawable(R.drawable.wheels_normal));
            }

        } else if (selectedLayoutId == R.id.electric_layout) {

            if (doSelect) {
                electricLayout.setBackgroundColor(Color.WHITE);
                electricText.setTextColor(getResources().getColor(R.color.color_red));
                electric.setImageDrawable(getResources().getDrawable(R.drawable.electrical_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                electricLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                electricText.setTextColor(Color.WHITE);
                electric.setImageDrawable(getResources().getDrawable(R.drawable.electrical_normal));
            }

        } else if (selectedLayoutId == R.id.dimensions_layout) {

            if (doSelect) {
                dimensionsLayout.setBackgroundColor(Color.WHITE);
                dimensionsText.setTextColor(getResources().getColor(R.color.color_red));
                dimensions.setImageDrawable(getResources().getDrawable(R.drawable.dimension_hover));
                scrollView.fullScroll(View.FOCUS_UP);
            } else {
                dimensionsLayout.setBackground(getResources().getDrawable(R.drawable.square_bg_red));
                dimensionsText.setTextColor(Color.WHITE);
                dimensions.setImageDrawable(getResources().getDrawable(R.drawable.dimension_normal));
            }
        }
    }

}

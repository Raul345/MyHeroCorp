package com.herocorp.ui.activities.products;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.models.ProductModel;
import com.herocorp.infra.db.repositories.product.ProductRepo;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import java.util.ArrayList;

public class ComparisonFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View rootView;

    private final String DEFAULT_SPINNER_TEXT = "SELECT A PRODUCT";

    private int productId;

    private ProductRepo productRepo;
    private ArrayList<ProductModel> productList;
    private ArrayList<String> productNameList;
    private CustomViewParams customViewParams;
    private CustomTypeFace customTypeFace;
    private ImageView otherBikeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.comparison_fragment, container, false);

        productId = ((BaseDrawerActivity) getActivity()).productId;

        try {
            productRepo = new ProductRepo(getActivity());
            productList = productRepo.getRecords(null, null, null, null);
            extractProductNameList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView(rootView);
        return rootView;
    }

    private void extractProductNameList() {
        productNameList = new ArrayList<String>();
        productNameList.add(DEFAULT_SPINNER_TEXT);

        int pos = 0;
        for (int i = 0; i < productList.size(); i++) {

            ProductModel productModel = productList.get(i);

            if (productModel.getProductID() == productId) {
                pos = i;
            } else {
                productNameList.add(productModel.getProductName());
            }
        }
        productList.remove(pos);
    }

    private void initView(View rootView) {

        customViewParams = new CustomViewParams(getActivity());
        customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{80, 30, 80, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{50, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);

        ImageView defaultBikeImage = (ImageView) rootView.findViewById(R.id.default_bike_image);
        otherBikeImage = (ImageView) rootView.findViewById(R.id.other_bike_image);

        customViewParams.setImageViewCustomParams(defaultBikeImage, new int[]{0, 0, 0, 30}, new int[]{0, 0, 0, 0}, 250, 380);
        customViewParams.setImageViewCustomParams(otherBikeImage, new int[]{0, 0, 0, 30}, new int[]{0, 0, 0, 0}, 250, 380);

        TextView defaultBikeName = (TextView) rootView.findViewById(R.id.default_bike_name);
        customViewParams.setTextViewCustomParams(defaultBikeName, new int[]{15, 15, 15, 15}, new int[]{0, 0, 0, 0}, 40, customTypeFace.gillSans, 0);

        Spinner productSpinner = (Spinner) rootView.findViewById(R.id.bike_spinner);
        customViewParams.setMarginAndPadding(productSpinner, new int[]{20, 0, 20, 0}, new int[]{10, 10, 10, 10}, productSpinner.getParent());

        Button featurecompare = (Button) rootView.findViewById(R.id.comparefeature_button);
        customViewParams.setButtonCustomParams(featurecompare, new int[]{20, 0, 20, 15}, new int[]{5, 5, 5, 5}, 50, 370, 30, customTypeFace.gillSansLight, 0);

        Button touchToCompareButton = (Button) rootView.findViewById(R.id.continue_button);
        customViewParams.setButtonCustomParams(touchToCompareButton, new int[]{20, 0, 20, 15}, new int[]{5, 5, 5, 5}, 50, 370, 30, customTypeFace.gillSansLight, 0);

        TextView separator = (TextView) rootView.findViewById(R.id.separator);
        TextView separator1 = (TextView) rootView.findViewById(R.id.separator1);

        customViewParams.setMarginAndPadding(separator, new int[]{0, 5, 0, 5}, new int[]{0, 0, 0, 0}, separator.getParent());
        customViewParams.setMarginAndPadding(separator1, new int[]{0, 5, 0, 5}, new int[]{0, 0, 0, 0}, separator1.getParent());
        customViewParams.setHeightAndWidth(separator, 0, 3);
        customViewParams.setHeightAndWidth(separator1, 0, 3);

        customViewParams.setMarginAndPadding(rootView.findViewById(R.id.button_layout), new int[]{0, 0, 0, 40}, new int[]{0, 0, 0, 0}, rootView.findViewById(R.id.button_layout).getParent());

        Button galleryButton = (Button) rootView.findViewById(R.id.gallery);
        Button featuresButton = (Button) rootView.findViewById(R.id.features);
        Button specsButton = (Button) rootView.findViewById(R.id.specs);
        Button compareButton = (Button) rootView.findViewById(R.id.compare);

        customViewParams.setButtonCustomParams(galleryButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(featuresButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(specsButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 450, 40, customTypeFace.gillSansItalic, 0);
        customViewParams.setButtonCustomParams(compareButton, new int[]{0, 20, 0, 20}, new int[]{40, 0, 0, 0}, 90, 350, 40, customTypeFace.gillSansItalic, 0);

        /*TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0,10,0,5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0,2,0,10}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30),null, null, null);*/

        setPreData(defaultBikeImage, defaultBikeName);
        setAdapter(productSpinner, productNameList);

        menu.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        featuresButton.setOnClickListener(this);
        specsButton.setOnClickListener(this);
        compareButton.setOnClickListener(this);
        touchToCompareButton.setOnClickListener(this);
        featurecompare.setOnClickListener(this);

    }

    private void setPreData(ImageView defaultBikeImage, TextView defaultBikeName) {
        try {
            ArrayList<ProductModel> defaultProductList = productRepo.getRecords(null, ProductTable.Cols.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)}, null);
            defaultBikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(defaultProductList.get(0).getProductIcon()));
            defaultBikeName.setText(defaultProductList.get(0).getProductName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(Spinner bikeSpinner, final ArrayList<String> bikeNamesList) {

        ArrayAdapter<String> specialityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.product_compare_list_layout, bikeNamesList) {

            @Override
            public int getCount() {
                return bikeNamesList.size();
            }

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                v.setBackgroundColor(Color.TRANSPARENT);

                customViewParams.setTextViewCustomParams(((TextView) v), new int[]{0, 0, 0, 0}, new int[]{20, 5, 20, 5}, 35, customTypeFace.gillSans, 0);
                if (productNameList.get(position).equalsIgnoreCase(DEFAULT_SPINNER_TEXT)) {
                    ((TextView) v).setTextColor(Color.GRAY);
                }

                return v;
            }

            // Affects opened state of the spinner
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                TextView view = (TextView) super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    view.setBackgroundColor(getResources().getColor(R.color.color_red));
                    customViewParams.setTextViewCustomParams(view, new int[]{0, 0, 0, 0}, new int[]{20, 0, 20, 0}, 35, customTypeFace.gillSansBold, 0);
                } else {
                    view.setBackgroundColor(Color.parseColor("#90000000"));
                    customViewParams.setTextViewCustomParams(view, new int[]{0, 0, 0, 0}, new int[]{20, 0, 20, 0}, 35, customTypeFace.gillSans, 0);
                }


                return view;

            }

        };

        specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bikeSpinner.setAdapter(specialityAdapter);
        bikeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        try {
            if (position == 0) {
                otherBikeImage.setImageDrawable(getResources().getDrawable(R.drawable.bike_place_holder));
                ((BaseDrawerActivity) getActivity()).otherProductId = -1;
            } else {
                otherBikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(productList.get(position - 1).getProductIcon()));
                ((BaseDrawerActivity) getActivity()).otherProductId = productList.get(position - 1).getProductID();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        otherBikeImage.setImageDrawable(getResources().getDrawable(R.drawable.bike_place_holder));
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.gallery) {

            // ((BaseDrawerActivity) getActivity()).openFragment(new GalleryFragment(), false);
            getActivity().onBackPressed();

        } else if (view.getId() == R.id.features) {

            // ((BaseDrawerActivity) getActivity()).openFragment(new FeaturesFragment(), false);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new FeaturesFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_compare, f);
            ft.commit();

        } else if (view.getId() == R.id.specs) {

            // ((BaseDrawerActivity) getActivity()).openFragment(new SpecificationsFragment(), false);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new SpecificationsFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_compare, f);
            ft.commit();

        } else if (view.getId() == R.id.compare) {

            ((BaseDrawerActivity) getActivity()).openFragment(new ComparisonFragment(), false);

        } else if (view.getId() == R.id.menu_icon) {

            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (view.getId() == R.id.continue_button) {

            if (((BaseDrawerActivity) getActivity()).otherProductId != -1)
            //((BaseDrawerActivity) getActivity()).openFragment(new ComparisonDetailFragment(), true);
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment f = new ComparisonDetailFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.content_compare, f);
                ft.commit();
            } else
                Toast.makeText(getActivity(), "Please select a product to compare", Toast.LENGTH_SHORT).show();

        }else if (view.getId() == R.id.comparefeature_button) {

            if (((BaseDrawerActivity) getActivity()).otherProductId != -1)
            //((BaseDrawerActivity) getActivity()).openFragment(new ComparisonDetailFragment(), true);
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment f = new FeatureCompareFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.add(R.id.content_compare, f);
                ft.commit();
            } else
                Toast.makeText(getActivity(), "Please select a product to compare", Toast.LENGTH_SHORT).show();

        }
    }

    public void onBackPressed() {
        // getFragmentManager().popBackStack(homeFragmentIdentifier, 0);
    }
}

package com.herocorp.ui.activities.products;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.models.ProductCategoryModel;
import com.herocorp.core.models.ProductModel;
import com.herocorp.infra.db.repositories.product.ProductCategoryRepo;
import com.herocorp.infra.db.repositories.product.ProductRepo;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import java.util.ArrayList;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private int selectedDotId = -1;
    private int selectButtonId = -1;

    private CustomViewParams customViewParams;
    private CustomTypeFace customTypeFace;

    private LinearLayout dotLayout;
    private LinearLayout textLayout;
    private LinearLayout leftButtonLayout;
    private LinearLayout rightButtonLayout;

    private ImageView bikeImage;

    //Repository Class
    private ProductCategoryRepo categoryRepo;
    private ProductRepo productRepo;

    //Data Model
    private ArrayList<ProductCategoryModel> categoryList;
    private ArrayList<ProductModel> productList;
    boolean flag = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bike_detail_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            categoryRepo = new ProductCategoryRepo(getActivity());
            productRepo = new ProductRepo(getActivity());

            //Get the List of the Categories
            categoryList = categoryRepo.getRecords(null, null, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        customViewParams = new CustomViewParams(getActivity());
        customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        dotLayout = (LinearLayout) rootView.findViewById(R.id.dot_layout);
        customViewParams.setMarginAndPadding(dotLayout, new int[]{0, 245, 0, 10}, new int[]{0, 0, 0, 0}, dotLayout.getParent());

        LinearLayout buttonLayout = (LinearLayout) rootView.findViewById(R.id.button_layout);
        customViewParams.setMarginAndPadding(buttonLayout, new int[]{0, 30, 0, 45}, new int[]{0, 0, 0, 0}, dotLayout.getParent());

        textLayout = (LinearLayout) rootView.findViewById(R.id.text_layout);
        leftButtonLayout = (LinearLayout) rootView.findViewById(R.id.left_side_button_layout);
        rightButtonLayout = (LinearLayout) rootView.findViewById(R.id.right_side_button_layout);

        /*TextView roundText1 = (TextView) rootView.findViewById(R.id.round_button1);
        TextView roundText2 = (TextView) rootView.findViewById(R.id.round_button2);
        TextView roundText3 = (TextView) rootView.findViewById(R.id.round_button3);
        TextView roundText4 = (TextView) rootView.findViewById(R.id.round_button4);
        TextView roundText5 = (TextView) rootView.findViewById(R.id.round_button5);

        customViewParams.setHeightAndWidth(roundText1, 50, 50);
        customViewParams.setHeightAndWidth(roundText2, 50, 50);
        customViewParams.setHeightAndWidth(roundText3, 50, 50);
        customViewParams.setHeightAndWidth(roundText4, 50, 50);
        customViewParams.setHeightAndWidth(roundText5, 50, 50);

        TextView line_text1 = (TextView) rootView.findViewById(R.id.line_text_view1);
        TextView line_text2 = (TextView) rootView.findViewById(R.id.line_text_view2);
        TextView line_text3 = (TextView) rootView.findViewById(R.id.line_text_view3);
        TextView line_text4 = (TextView) rootView.findViewById(R.id.line_text_view4);

        customViewParams.setHeightAndWidth(line_text1, 3, 130);
        customViewParams.setHeightAndWidth(line_text2, 3, 130);
        customViewParams.setHeightAndWidth(line_text3, 3, 130);
        customViewParams.setHeightAndWidth(line_text4, 3, 130);


        TextView text1 = (TextView) rootView.findViewById(R.id.round_button_text1);
        TextView text2 = (TextView) rootView.findViewById(R.id.round_button_text2);
        TextView text3 = (TextView) rootView.findViewById(R.id.round_button_text3);
        TextView text4 = (TextView) rootView.findViewById(R.id.round_button_text4);
        TextView text5 = (TextView) rootView.findViewById(R.id.round_button_text5);

        customViewParams.setTextViewCustomParams(text1, new int[]{5, 5, 27, 5}, new int[]{0, 0, 0, 0}, 25, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(text2, new int[]{27, 5, 27, 5}, new int[]{0, 0, 0, 0}, 25, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(text3, new int[]{27, 5, 27, 5}, new int[]{0, 0, 0, 0}, 25, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(text4, new int[]{27, 5, 27, 5}, new int[]{0, 0, 0, 0}, 25, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(text5, new int[]{27, 5, 5, 5}, new int[]{0, 0, 0, 0}, 25, customTypeFace.gillSans, 0);


        Button glamourButton = (Button) rootView.findViewById(R.id.glamour);
        Button passionPro = (Button) rootView.findViewById(R.id.passion_pro);
        Button hfDelux = (Button) rootView.findViewById(R.id.hf_delux);
        Button initor = (Button) rootView.findViewById(R.id.ignitor);
        Button hunk = (Button) rootView.findViewById(R.id.hunk);
        Button ismart = (Button) rootView.findViewById(R.id.i_smart);

        customViewParams.setButtonCustomParams(glamourButton, new int[]{300, 5, 5, 10}, new int[]{0, 0, 0, 0}, 80, 300, 30, customTypeFace.gillSans, 0);
        customViewParams.setButtonCustomParams(passionPro, new int[]{258, 5, 5, 10}, new int[]{0, 0, 0, 0}, 80, 300, 30, customTypeFace.gillSans, 0);
        customViewParams.setButtonCustomParams(hfDelux, new int[]{218, 5, 5, 10}, new int[]{0, 0, 0, 0}, 80, 300, 30, customTypeFace.gillSans, 0);
        customViewParams.setButtonCustomParams(initor, new int[]{5, 5, 300, 10}, new int[]{0, 0, 0, 0}, 80, 300, 30, customTypeFace.gillSans, 0);
        customViewParams.setButtonCustomParams(hunk, new int[]{5, 5, 258, 10}, new int[]{0, 0, 0, 0}, 80, 300, 30, customTypeFace.gillSans, 0);
        customViewParams.setButtonCustomParams(ismart, new int[]{5, 5, 218, 10}, new int[]{0, 0, 0, 0}, 80, 300, 30, customTypeFace.gillSans, 0);*/

        bikeImage = (ImageView) rootView.findViewById(R.id.bike_image_view);
        customViewParams.setImageViewCustomParams(bikeImage, new int[]{10, 10, 10, 10}, new int[]{0, 0, 0, 0}, 275, 412);

        /*TextView copyRightText1 = (TextView) rootView.findViewById(R.id.copy_right_text1);
        customViewParams.setTextViewCustomParams(copyRightText1, new int[]{0,10,0,5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        TextView copyRightText2 = (TextView) rootView.findViewById(R.id.copy_right_text2);
        customViewParams.setTextViewCustomParams(copyRightText2, new int[]{0,2,0,10}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);
        copyRightText2.setCompoundDrawables(customViewParams.setDrawableParams(getResources().getDrawable(R.drawable.ic_contact), 30, 30),null, null, null);*/

        bikeImage.setOnClickListener(this);
        menu.setOnClickListener(this);

        setSelectCategoryButtons();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setSelectCategoryButtons() {
        try {

            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getCategoryName().equalsIgnoreCase("competitors"))
                    flag = true;
            }

            for (int i = 0; i < categoryList.size(); i++) {
                if (!categoryList.get(i).getCategoryName().equalsIgnoreCase("competitors")) {
                    TextView roundTextView = new TextView(getActivity());
                    roundTextView.setId(categoryList.get(i).getCategoryID());
                    roundTextView.setBackground(getResources().getDrawable(i == 0 ? R.drawable.red_dot_icon : R.drawable.black_dot_icon));
                    dotLayout.addView(roundTextView);
                    customViewParams.setHeightAndWidth(roundTextView, 65, 65);
                    roundTextView.setOnClickListener(this);
                    if (i == 0)
                        selectedDotId = roundTextView.getId();

                    if (!flag) {
                        if (i < categoryList.size() - 1) {
                            TextView lineTextView = new TextView(getActivity());
                            lineTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                            dotLayout.addView(lineTextView);
                            customViewParams.setHeightAndWidth(lineTextView, 3, 160);
                        }
                    } else {
                        if (i < categoryList.size() - 2) {
                            TextView lineTextView = new TextView(getActivity());
                            lineTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                            dotLayout.addView(lineTextView);
                            customViewParams.setHeightAndWidth(lineTextView, 3, 160);
                        }
                    }

                    TextView categoryText = new TextView(getActivity());
                    categoryText.setGravity(Gravity.CENTER);
                    categoryText.setTextColor(Color.WHITE);
                    String text = categoryList.get(i).getCategoryName();
                    categoryText.setText(text);
                    textLayout.addView(categoryText);
                    customViewParams.setTextViewCustomParams(categoryText, new int[]{30, 5, 30, 5}, new int[]{0, 0, 0, 0}, 30, customTypeFace.gillSans, 0);

                }

                productList = productRepo.getRecords(
                        null,
                        ProductTable.Cols.CATEGORY_ID + "=? AND " + ProductTable.Cols.CATEGORY_ID + "<>?",
                        new String[]{String.valueOf(categoryList.get(0).getCategoryID()), String.valueOf(7)}, null);

                setProductList();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void setProductList() {

        int offset = 300;
        int limit = 42;
        int limitLeft = 0;
        int limitRight = 0;
        rightButtonLayout.removeAllViews();
        leftButtonLayout.removeAllViews();


        for (int i = 0; i < productList.size(); i++) {

            Button button = new Button(getActivity());
            button.setText(productList.get(i).getProductName());
            button.setId(productList.get(i).getProductID());
            button.setTextColor(i == 0 ? Color.WHITE : Color.BLACK);
            if (i == 0) {
                selectButtonId = button.getId();
                setBikeImage(button.getId());
            }

            if ((i + 1) % 2 == 0) {

                button.setBackground(getResources().getDrawable(i == 0 ? R.drawable.red_button_rl : R.drawable.white_button_rl));
                rightButtonLayout.addView(button);
                customViewParams.setButtonCustomParams(button,
                        new int[]{5, 5, offset - (limitRight), 10},
                        new int[]{35, 5, 35, 5}, 80, 300, 27, customTypeFace.gillSans, 0);
                limitRight += limit;
            } else {

                button.setBackground(getResources().getDrawable(i == 0 ? R.drawable.red_button_lr : R.drawable.white_button_lr));
                leftButtonLayout.addView(button);
                customViewParams.setButtonCustomParams(button,
                        new int[]{offset - (limitLeft), 5, 5, 10},
                        new int[]{35, 5, 35, 5}, 80, 300, 27, customTypeFace.gillSans, 0);

                limitLeft += limit;

            }

            button.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.bike_image_view) {

            ((BaseDrawerActivity) getActivity()).productId = selectButtonId;
          /*  ((BaseDrawerActivity) getActivity()).openFragment(new GalleryFragment(), true);*/
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = new GalleryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.content_productdetail, f);
            ft.addToBackStack("gallery");
            ft.commit();

        } else if (view.getId() == R.id.menu_icon) {

            ((BaseDrawerActivity) getActivity()).toggleDrawer();

        } else if (view instanceof Button) {

            uncheckSelectedButton();

            if (((View) view.getParent()).getId() == R.id.left_side_button_layout)
                view.setBackground(getResources().getDrawable(R.drawable.red_button_lr));
            else
                view.setBackground(getResources().getDrawable(R.drawable.red_button_rl));

            ((Button) view).setTextColor(Color.WHITE);
            selectButtonId = view.getId();


            setBikeImage(view.getId());

        } else if (view instanceof TextView) {

            if (selectedDotId != -1)
                dotLayout.findViewById(selectedDotId).setBackground(getResources().getDrawable(R.drawable.black_dot_icon));

            view.setBackground(getResources().getDrawable(R.drawable.red_dot_icon));
            try {
                selectedDotId = view.getId();

                productList = productRepo.getRecords(
                        null,
                        ProductTable.Cols.CATEGORY_ID + "=? AND " + ProductTable.Cols.CATEGORY_ID + " <>?",
                        new String[]{String.valueOf(selectedDotId), String.valueOf(7)}, null);

                if (productList.size() > 0) {
                    setProductList();
                } else {
                    bikeImage.setImageBitmap(null);
                    leftButtonLayout.removeAllViews();
                    rightButtonLayout.removeAllViews();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setBikeImage(int productId) {

        try {

            String imageName = null;
            if (productList != null) {
                for (ProductModel product : productList) {
                    if (product.getProductID() == productId) {
                        imageName = product.getProductIcon();
                        break;
                    }
                }
            }

            /*ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File path1 = cw.getDir("heroImages", Context.MODE_PRIVATE);
            //File f = new File(path1, imageName);
            File f = new File(path1, "pr_fe_img_8_1_1453098961.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            bikeImage.setImageBitmap(b);*/
            bikeImage.setImageBitmap(ImageHandler.getInstance(getActivity()).loadImageFromStorage(imageName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private String getProductIconName(int productId){
        String imageName = null;
        if (productList != null) {
            for (ProductModel product : productList) {
                if (product.getProductID() == productId) {
                    imageName = product.getProductIcon();
                    break;
                }
            }
        }
        return imageName;
    }*/

    private void uncheckSelectedButton() {

        boolean runRightLayoutCheck = false;
        int childcount = leftButtonLayout.getChildCount();

        for (int i = 0; i < childcount; i++) {

            View v = leftButtonLayout.getChildAt(i);
            if (v.getId() == selectButtonId) {
                v.setBackground(getResources().getDrawable(R.drawable.white_button_lr));
                ((Button) v).setTextColor(Color.BLACK);
                runRightLayoutCheck = false;
                break;
            }
            runRightLayoutCheck = true;
        }
        if (runRightLayoutCheck) {

            for (int i = 0; i < childcount; i++) {

                View v = rightButtonLayout.getChildAt(i);
                if (v.getId() == selectButtonId) {
                    v.setBackground(getResources().getDrawable(R.drawable.white_button_rl));
                    ((Button) v).setTextColor(Color.BLACK);
                    break;
                }
            }
        }
    }
}

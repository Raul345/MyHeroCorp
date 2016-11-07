package com.herocorp.ui.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by AmitGautam on 25-04-2016.
 */
public class CustomViewParams {

    private final Context context;
    private Params params;

    public CustomViewParams(Context context) {

        params = new Params(context.getResources().getDisplayMetrics());
        this.context = context;

    }

    public void setTextViewCustomParams(View view, int margin[], int padding[], int fontSize, Typeface typeface, int fontStyle) {

        setMarginAndPadding(view, margin, padding, view.getParent());

        if (view instanceof TextView) {

            ((TextView) view).setTypeface(typeface, fontStyle);
            ((TextView) view).setTextSize(params.getFontSize(fontSize));

        }
    }

    public void setEditTextCustomParams(View view, int margin[], int padding[], int fontSize, Typeface typeface, int fontStyle) {

        setMarginAndPadding(view, margin, padding, view.getParent());

        if (view instanceof EditText) {

            ((EditText) view).setTypeface(typeface, fontStyle);
            ((EditText) view).setTextSize(params.getFontSize(fontSize));

        }
    }

    public void setImageViewCustomParams(View view, int margin[], int padding[], int height, int width) {

        setMarginAndPadding(view, margin, padding, view.getParent());

        if (view instanceof ImageView) {
            setHeightAndWidth(view, height, width);
        }
    }

    public void setButtonCustomParams(View view, int margin[], int padding[], int height, int width, int fontSize, Typeface typeface, int fontStyle) {

        setMarginAndPadding(view, margin, padding, view.getParent());

        if (view instanceof Button) {

            setHeightAndWidth(view, height, width);

            if (fontSize > 0)
                ((Button) view).setTextSize(params.getFontSize(fontSize));

            ((Button) view).setTypeface(typeface, fontStyle);
        }
    }

    public Drawable setDrawableParams(Drawable drawable, int height, int width) {

        drawable.setBounds(0, 0, params.getSquareViewSize(width), params.getSquareViewSize(height));
        return drawable;
    }

    public void setHeaderParams(ImageView headerLeftIcon, ImageView headerRightIcon, TextView headerText) {
        if (headerLeftIcon != null)
            setImageViewCustomParams(headerLeftIcon, new int[]{5, 5, 5, 5}, new int[]{5, 5, 5, 5}, 100, 100);
        if (headerRightIcon != null)
            setImageViewCustomParams(headerRightIcon, new int[]{5, 5, 5, 5}, new int[]{5, 5, 5, 5}, 100, 100);
        if (headerText != null) {
            CustomTypeFace customTypeFace = new CustomTypeFace(context);
            setTextViewCustomParams(headerText, new int[]{5, 5, 5, 5}, new int[]{5, 5, 5, 5}, 50, customTypeFace.gillSans, 0);
        }
    }

    public void setMarginAndPadding(View view, int margin[], int padding[], ViewParent parent) {

        if (parent instanceof RelativeLayout) {

            ((RelativeLayout.LayoutParams) view.getLayoutParams()).setMargins(
                    params.getSpacing(margin[0]), params.getSpacing(margin[1]), params.getSpacing(margin[2]), params.getSpacing(margin[3]));

        } else if (parent instanceof LinearLayout) {

            ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(
                    params.getSpacing(margin[0]), params.getSpacing(margin[1]), params.getSpacing(margin[2]), params.getSpacing(margin[3]));
        }

        view.setPadding(params.getSpacing(padding[0]), params.getSpacing(padding[1]), params.getSpacing(padding[2]), params.getSpacing(padding[3]));
    }

    public void setHeightAndWidth(View view, int height, int width) {

        if (height > 0)
            (view.getLayoutParams()).height = params.getSquareViewSize(height);
        if (width > 0)
            (view.getLayoutParams()).width = params.getSquareViewSize(width);

    }


}

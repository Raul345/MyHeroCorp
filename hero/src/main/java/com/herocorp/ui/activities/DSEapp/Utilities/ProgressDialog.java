package com.herocorp.ui.activities.DSEapp.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.herocorp.R;

/**
 * Created by rsawh on 06-Dec-16.
 */

/*
public class ProgressDialog extends Dialog {
    public static ProgressDialog progrssDialog;

    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }


    public void onWindowFocusChanged(boolean hasFocus) {
        ProgressBar imageView = (ProgressBar) findViewById(R.id.progress);
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    */
/***
     * show progress dialog
     *
     * @param context
     * @param message
     * @param indeterminate
     * @param cancelable
     * @param cancelListener
     * @return
     *//*

    public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate, boolean cancelable,
                                   OnCancelListener cancelListener) {
        ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_hud);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }

    public static void showDialog(Context context, CharSequence message, boolean cancelable) {
        if (progrssDialog == null) {
            progrssDialog = ProgressHUD.show(context, message, false, false, null);

        }
    }

    public static void dismissDialog() {
        if (progrssDialog != null && progrssDialog.isShowing()) {
            progrssDialog.cancel();
            progrssDialog = null;
        }
    }

}
*/

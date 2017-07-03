package com.herocorp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.auth.SignInActivity;
import com.herocorp.ui.utility.ClearCache;
import com.herocorp.ui.utility.PreferenceUtil;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class activity_splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PreferenceUtil.setFlag_UPDATE(this, true);
        PreferenceUtil.setFlagAuth(this, true);
        new Handler().
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (PreferenceUtil.get_IsUserLogin(getApplicationContext()))
                            openHomeScreen();
                        else {
                            Intent i = new Intent(activity_splash.this, SignInActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, 1000);
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, BaseDrawerActivity.class);
        startActivity(intent);
        finish();
    }
}

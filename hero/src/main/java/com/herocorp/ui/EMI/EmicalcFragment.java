package com.herocorp.ui.EMI;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.herocorp.R;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

/**
 * Created by rsawh on 24-Oct-16.
 */
public class EmicalcFragment extends Activity implements View.OnClickListener {
    private CustomViewParams customViewParams;
    WebView emiWebView;
    ImageView back;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emicalculator_fragment);
        initView();
    }

    private void initView() {
        customViewParams = new CustomViewParams(this);
        ImageView heroLogo = (ImageView) findViewById(R.id.app_logo);
        back = (ImageView) findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(back, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);
        progressBar = (ProgressBar) findViewById(R.id.progress);

       /* ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{50, 50, 50, 50}, new int[]{0, 0, 0, 0}, scrollView.getParent());
*/

        RelativeLayout scrollView = (RelativeLayout) findViewById(R.id.layout_view);
        customViewParams.setMarginAndPadding(scrollView, new int[]{50, 50, 50, 50}, new int[]{0, 0, 0, 0}, scrollView.getParent());

        emiWebView = (WebView) findViewById(R.id.webView1);
        emiWebView.getSettings().setLoadWithOverviewMode(true);
        emiWebView.setVerticalScrollBarEnabled(true);

        emiWebView.getSettings().setUseWideViewPort(true);
        emiWebView.setBackgroundColor(Color.TRANSPARENT);

        WebSettings webSetting = emiWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);

        emiWebView.setWebViewClient(new WebViewClient());
        emiWebView.loadUrl("file:///android_asset/EmiCalculator/emi.html");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            back.setVisibility(View.VISIBLE);
            //do what you want to do

        }

    }
}

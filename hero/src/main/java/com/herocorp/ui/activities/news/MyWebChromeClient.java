package com.herocorp.ui.activities.news;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by rsawh on 05-Jan-17.
 */

public class MyWebChromeClient extends WebChromeClient {
    private ProgressListener mListener;

    public MyWebChromeClient(ProgressListener listener) {
        mListener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mListener.onUpdateProgress(newProgress);
        super.onProgressChanged(view, newProgress);
    }

    public interface ProgressListener {
        public void onUpdateProgress(int progressValue);
    }
}

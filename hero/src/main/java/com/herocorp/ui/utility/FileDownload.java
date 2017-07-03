package com.herocorp.ui.utility;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.herocorp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rsawh on 29-Jun-17.
 */


public class FileDownload extends AsyncTask<Void, Void, File> {
    ProgressDialog progressDialog;
    String pdfUrl;
    Context context;
    String filenewname;
    int flag;

    public FileDownload(String pdfUrl, Context context, String filenewname, int flag) {
        this.pdfUrl = pdfUrl;
        this.context = context;
        progressDialog = ProgressDialog.show(context, null, null);
        progressDialog.setContentView(R.layout.progresslayout);
        this.flag = flag;
        this.filenewname = filenewname;
    }

    @Override
    protected File doInBackground(Void... params) {
        URL url = null;
        String filePath = "";
        File file = null;
        try {
            url = new URL(pdfUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            // connect
            urlConnection.connect();

            String dir = Environment.getExternalStorageDirectory() + "/Hero_docs";
            //create folder
            File folder = new File(dir); //folder name
            folder.mkdirs();

            //create file
            file = new File(dir, filenewname);
            FileOutputStream fos = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalsize = urlConnection.getContentLength();
            byte[] buf = new byte[512];
            while (true) {
                int len = inputStream.read(buf);
                if (len == -1) {
                    break;
                }
                fos.write(buf, 0, len);
            }
            inputStream.close();
            fos.flush();
            fos.close();
            filePath = file.getAbsolutePath();
        } catch (MalformedURLException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        } catch (IOException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        progressDialog.dismiss();
        Intent target = new Intent(Intent.ACTION_VIEW);
        Intent intent = Intent.createChooser(target, "Open File");
        if (file != null && file.exists() && flag == 1) {
            target.setDataAndType(Uri.fromFile(file), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (file != null && file.exists() && flag == 2) {
            target.setDataAndType(Uri.fromFile(file), "application/msword");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {

            // Instruct the user to install a PDF reader here, or something
        }
    }
}


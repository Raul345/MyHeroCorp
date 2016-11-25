package com.herocorp.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//import com.google.firebase.messaging.FirebaseMessaging;
import com.herocorp.R;
import com.herocorp.core.constants.AppConstants;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.core.interfaces.SyncServiceCallBack;
import com.herocorp.core.interfaces.iNetworkResponseCallback;
import com.herocorp.core.models.AuthenticateUserModel;
import com.herocorp.infra.db.SQLiteDataHelper;
import com.herocorp.infra.db.tables.schemas.products.ProductRotationTable;
import com.herocorp.infra.netio.AuthenticateUserService;
import com.herocorp.infra.utils.NetConnections;
//import com.herocorp.ui.FCMservice.FCMInstanceIdservice;
import com.herocorp.ui.EMI.EmicalcFragment;
import com.herocorp.ui.VAS.VasWarrantyfragment;
import com.herocorp.ui.activities.DSEapp.Fragment.ContactAlertFragment;
import com.herocorp.ui.activities.DSEapp.Fragment.Home.HomeFragment;
import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.contact_us.ContactUsFragmrnt;
import com.herocorp.ui.activities.home.DealerDashboardFragment;
import com.herocorp.ui.activities.products.ProductDetailFragment;
import com.herocorp.ui.app.App;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class BaseDrawerActivity extends FragmentActivity implements View.OnClickListener {

    private final int drawerGravity = Gravity.LEFT;
    private static final int REQUEST_PERMISSION_READ_PHONE_STATE = 1;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private final String TAG = BaseDrawerActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;

    public int productId = -1;
    public int otherProductId = -1;

    Fragment fragment = null;

    private TelephonyManager telephonyManager;

    private SharedPreferences sharedPreferences;

    private String deviceImei;
    private String userId = "ROBINK11610";
    private String appVersion;
    private String deviceVersion;
    private String refreshedToken;
    NetworkConnect networkConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        initView();

        try {
            if (NetConnections.isConnected(this)) {

                telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                PackageManager manager = getPackageManager();
                PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

                appVersion = info.versionName;
                deviceVersion = Build.VERSION.CODENAME;
                deviceImei = telephonyManager.getDeviceId();


                showPhoneStatePermission();
                //  fetch_data();

                new check_version().execute(URLConstants.CHECK_VERSION);
                //FCM service
                /*FirebaseMessaging.getInstance().subscribeToTopic("news");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FCMInstanceIdservice fcm = new FCMInstanceIdservice(deviceImei, userId, appVersion, deviceVersion, "1.0");
                        fcm.onTokenRefresh();

                   }
                });*/


            } else if (!(sharedPreferences.getBoolean(AppConstants.IS_USER_LOGGED_IN, false) &&
                    App.shouldAppRun(sharedPreferences.getString(AppConstants.VALIDITY_DATE, "")))) {
                finish();
            }
            insertRotationDataInDB();
           /* boolean bool = sharedPreferences.getBoolean(AppConstants.IS_360_RECORD_INSERTED, false);
            if (bool) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertRotationDataInDB();
                    }
                }).start();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private boolean authenticateUser(String deviceId, String appVersion, String deviceVersion) throws Exception {

        AuthenticateUserService.authenticateUser(TAG, deviceId, appVersion, deviceVersion,
                new iNetworkResponseCallback<AuthenticateUserModel>() {
                    @Override
                    public void onSuccess(ArrayList<AuthenticateUserModel> data) {

                        try {

                            sharedPreferences.edit().putBoolean(AppConstants.IS_USER_LOGGED_IN, true).commit();
                            sharedPreferences.edit().putString(AppConstants.VALIDITY_DATE, data.get(0).getAppValiditiDate()).commit();

                            showExternalStoragePermission();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(AuthenticateUserModel data) {
                    }

                    @Override
                    public void onFailure(String message, Boolean showToast) {

                        new AlertDialog.Builder(BaseDrawerActivity.this)
                                .setMessage(message)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sharedPreferences.edit().putBoolean(AppConstants.IS_USER_LOGGED_IN, false).commit();
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    }
                });

        return false;
    }

    private void initView() {

        CustomViewParams customViewParams = new CustomViewParams(this);
        CustomTypeFace customTypeFace = new CustomTypeFace(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawerLayout.setDrawerListener(this);

        LinearLayout navMenuLayout = (LinearLayout) findViewById(R.id.nav_menu_layout);
        customViewParams.setHeightAndWidth(navMenuLayout, 0, 650);

        RelativeLayout profileContainer = (RelativeLayout) findViewById(R.id.profile_contaioner);
        customViewParams.setHeightAndWidth(profileContainer, 400, 0);

        ImageView imageView = (ImageView) findViewById(R.id.profile_image_container);
        ImageView closeDrawer = (ImageView) findViewById(R.id.drawer_close);
        ImageView logoDrawer = (ImageView) findViewById(R.id.drawer_logo);

        customViewParams.setImageViewCustomParams(imageView, new int[]{0, 0, 0, 20}, new int[]{0, 0, 0, 0}, 150, 150);
        customViewParams.setImageViewCustomParams(closeDrawer, new int[]{0, 20, 20, 0}, new int[]{0, 0, 0, 0}, 60, 60);
        customViewParams.setImageViewCustomParams(logoDrawer, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);

        TextView nameText = (TextView) findViewById(R.id.name_text);
        customViewParams.setTextViewCustomParams(nameText, new int[]{0, 10, 0, 0}, new int[]{0, 0, 0, 0}, 55, customTypeFace.gillSans, 0);
        nameText.setVisibility(View.GONE);

        ImageView navHomeImage = (ImageView) findViewById(R.id.nav_home_image);
        ImageView navProductImage = (ImageView) findViewById(R.id.nav_products_image);
        ImageView navValueImage = (ImageView) findViewById(R.id.nav_value_image);
        ImageView navNewsImage = (ImageView) findViewById(R.id.nav_news_image);
        ImageView navDseImage = (ImageView) findViewById(R.id.nav_dse_image);
        ImageView navContactImage = (ImageView) findViewById(R.id.nav_contactus_image);
        ImageView navFAQImage = (ImageView) findViewById(R.id.nav_faq_image);
        ImageView navIssueImage = (ImageView) findViewById(R.id.nav_issue_image);
        ImageView navSyncImage = (ImageView) findViewById(R.id.nav_sync_image);
        ImageView navEmiImage = (ImageView) findViewById(R.id.nav_emi_image);


        customViewParams.setImageViewCustomParams(navHomeImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navProductImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navValueImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navNewsImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navDseImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navContactImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navFAQImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navIssueImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navSyncImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);
        customViewParams.setImageViewCustomParams(navEmiImage, new int[]{30, 0, 20, 0}, new int[]{0, 0, 0, 0}, 40, 40);


        TextView navHomeText = (TextView) findViewById(R.id.nav_home_text);
        TextView navProductText = (TextView) findViewById(R.id.nav_products_text);
        TextView navValueText = (TextView) findViewById(R.id.nav_value_text);
        TextView navNewsText = (TextView) findViewById(R.id.nav_news_text);
        TextView navDseText = (TextView) findViewById(R.id.nav_dse_text);
        TextView navContactText = (TextView) findViewById(R.id.nav_contactus_text);
        TextView navFAQText = (TextView) findViewById(R.id.nav_faq_text);
        TextView navIssueText = (TextView) findViewById(R.id.nav_issue_text);
        TextView navSyncText = (TextView) findViewById(R.id.nav_sync_text);
        TextView navEmiText = (TextView) findViewById(R.id.nav_emi_text);

        customViewParams.setTextViewCustomParams(navHomeText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navProductText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navValueText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navNewsText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navDseText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navContactText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navFAQText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navIssueText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navSyncText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);
        customViewParams.setTextViewCustomParams(navEmiText, new int[]{0, 30, 0, 30}, new int[]{0, 0, 0, 0}, 35, customTypeFace.gillSans, 0);

        navMenuLayout.setOnClickListener(this);
        closeDrawer.setOnClickListener(this);
        findViewById(R.id.nav_home_layout).setOnClickListener(this);
        findViewById(R.id.nav_products_layout).setOnClickListener(this);
        findViewById(R.id.nav_contactus_layout).setOnClickListener(this);
        findViewById(R.id.nav_faq_layout).setOnClickListener(this);
        findViewById(R.id.nav_dse_layout).setOnClickListener(this);
        findViewById(R.id.nav_issue_layout).setOnClickListener(this);
        findViewById(R.id.nav_value_layout).setOnClickListener(this);
        findViewById(R.id.nav_news_layout).setOnClickListener(this);
        findViewById(R.id.nav_emi_layout).setOnClickListener(this);
        findViewById(R.id.nav_sync_layout).setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, new DealerDashboardFragment()).commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!sharedPreferences.getBoolean(AppConstants.IS_SYNC_COMPLETED, false))
                        startSync();

                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }

                break;

            case REQUEST_PERMISSION_READ_PHONE_STATE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        authenticateUser(telephonyManager.getDeviceId(), appVersion, deviceVersion);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void showExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }

        } else {

            if (!sharedPreferences.getBoolean(AppConstants.IS_SYNC_COMPLETED, false))
                startSync();
        }
    }

    public void showPhoneStatePermission() throws Exception {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_READ_PHONE_STATE);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_READ_PHONE_STATE);
            }

        } else {

            authenticateUser(telephonyManager.getDeviceId(), appVersion, deviceVersion);
        }
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.drawer_close) {
            toggleDrawer();

        } else if (i == R.id.nav_home_layout) {
            toggleDrawer();
            fragment = new DealerDashboardFragment();
            openFragment(fragment, false);


        } else if (i == R.id.nav_products_layout) {
            toggleDrawer();
            if (sharedPreferences.getBoolean(AppConstants.IS_SYNC_COMPLETED, false)) {
                fragment = new ProductDetailFragment();
                openFragment(fragment, false);
            } else {
                Toast.makeText(this, "Please Wait! data sync is in process.", Toast.LENGTH_SHORT).show();
            }


        } else if (i == R.id.nav_dse_layout) {

            toggleDrawer();

            try {
                toggleDrawer();
                fragment = new HomeFragment();

                openFragment(fragment, false);
//                Intent intent = getPackageManager().getLaunchIntentForPackage("com.herocorp.ui.activities.DSEapp.Fragment.Home.HomeFragment;");
//                startActivity(intent);


            } catch (Exception e) {
                Toast.makeText(this, "DSE App not installed!", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.nav_sync_layout) {
            toggleDrawer();

            new AlertDialog.Builder(this)
                    .setTitle("Do you want to start sync?")
                    .setMessage("Sync will take a while, and cannot be canceled once started.\nPress yes to continue.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            sharedPreferences.edit().putBoolean(AppConstants.IS_SYNC_COMPLETED, false).commit();

                            fragment = new DealerDashboardFragment();
                            openFragment(fragment, false);

                            startSync();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else if (i == R.id.nav_contactus_layout) {
            openFragment(new ContactUsFragmrnt(), false);
        } else if (i == R.id.nav_value_layout) {

            toggleDrawer();

            try {
                toggleDrawer();
                fragment = new VasWarrantyfragment();
                openFragment(fragment, false);
//                Intent intent = getPackageManager().getLaunchIntentForPackage("com.herocorp.ui.activities.DSEapp.Fragment.Home.HomeFragment;");
//                startActivity(intent);


            } catch (Exception e) {
                Toast.makeText(this, "VAS App not installed!", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.nav_faq_layout) {
            try {
                toggleDrawer();
               /* fragment = new EmicalcFragment();

                openFragment(fragment, false);*/
                //   startActivity(new Intent(getApplicationContext(),EmicalcFragment.class));

            } catch (Exception e) {
                Toast.makeText(this, "personal info not installed!" + e, Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.nav_emi_layout) {
            try {
                toggleDrawer();
               /* fragment = new EmicalcFragment();

                openFragment(fragment, false);*/
                startActivity(new Intent(getApplicationContext(), EmicalcFragment.class));

            } catch (Exception e) {
                Toast.makeText(this, "personal info not installed!" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openFragment(Fragment fragment, boolean doBackStack) {
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_container, fragment);


            if (doBackStack)
                transaction.addToBackStack("yo");

            transaction.commit();
        }
    }

    public void toggleDrawer() {

        if (drawerLayout != null && drawerLayout.isDrawerOpen(drawerGravity)) {

            drawerLayout.closeDrawer(drawerGravity);

        } else if (drawerLayout != null && !(drawerLayout.isDrawerOpen(drawerGravity))) {

            drawerLayout.openDrawer(drawerGravity);
        }
    }

    public void closeDrawer() {

        if (drawerLayout != null && drawerLayout.isDrawerOpen(drawerGravity)) {

            drawerLayout.closeDrawer(drawerGravity);

        }
    }

    public void openDrawer() {

        if (drawerLayout != null && !(drawerLayout.isDrawerOpen(drawerGravity))) {

            drawerLayout.openDrawer(drawerGravity);
        }
    }

    private void startSync() {

        App.getInstance().startSync(new SyncServiceCallBack() {
            @Override
            public void completed() {
                Toast.makeText(BaseDrawerActivity.this, "Sync Completed", Toast.LENGTH_LONG).show();
                Log.e("Sync", "Completed");

                sharedPreferences.edit().putBoolean(AppConstants.IS_SYNC_COMPLETED, true).commit();
                App.setProgress(100);
                //Copy database
                writeDatabaseToSD(BaseDrawerActivity.this);
            }

            @Override
            public void error() {
                Toast.makeText(BaseDrawerActivity.this, "Sync error", Toast.LENGTH_LONG).show();
                sharedPreferences.edit().putBoolean(AppConstants.IS_SYNC_COMPLETED, false).commit();
                App.setProgress(100);
                Log.e("Sync", "Error");
            }
        });

        App.getProgressBar(this, "Sync is running");
        //Toast.makeText(this, "Sync Started", Toast.LENGTH_SHORT).show();
        Log.e("Sync", "Started");
    }

    public static void writeDatabaseToSD(Context context) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String backupDBPath = "/BackupFolder/herohonda.db";
                File currentDB = context.getDatabasePath(SQLiteDataHelper.DB_NAME);
                File backupDB = new File(sd, backupDBPath);

                File backupDir = new File(sd, "/BackupFolder");
                if (!backupDir.exists()) {
                    backupDir.mkdir();
                }

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Log.e("writeDatabaseToSD", "writeDatabaseToSD()", e);
        }
    }

    private void insertRotationDataInDB() {

        final String INSERT_TABLE1 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(7,'passion_pro_360d_s01.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s02.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s03.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s04.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s05.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s06.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s07.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s08.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s09.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s10.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s11.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s12.png','2016-09-06 19:56:51'),"
                + "(7,'passion_pro_360d_s13.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s14.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s15.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s16.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s17.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s18.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s19.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s20.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s21.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s22.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s23.png','2016-09-06 19:57:37'),"
                + "(7,'passion_pro_360d_s24.png','2016-09-06 19:57:37')";

        final String INSERT_TABLE2 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(8,'super_splendor_360_s01.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s02.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s03.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s04.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s05.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s06.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s07.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s08.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s09.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s10.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s11.png','2016-09-06 20:04:14'),"
                + "(8,'super_splendor_360_s12.png','2016-09-06 20:04:15'),"
                + "(8,'super_splendor_360_s13.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s14.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s15.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s16.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s17.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s18.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s19.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s20.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s21.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s22.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s23.png','2016-09-06 20:04:54'),"
                + "(8,'super_splendor_360_s24.png','2016-09-06 20:04:54')";

        final String INSERT_TABLE3 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(12,'karizma_zmr_360_s01.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s02.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s03.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s04.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s05.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s06.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s07.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s08.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s09.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s10.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s11.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s12.png','2016-09-06 20:05:32'),"
                + "(12,'karizma_zmr_360_s13.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s14.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s15.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s16.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s17.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s18.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s19.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s20.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s21.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s22.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s23.png','2016-09-06 20:06:11'),"
                + "(12,'karizma_zmr_360_s24.png','2016-09-06 20:06:11')";

        final String INSERT_TABLE4 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(19,'karizma_r_360_s01.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s02.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s03.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s04.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s05.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s06.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s07.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s08.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s09.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s10.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s11.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s12.png','2016-09-06 20:07:16'),"
                + "(19,'karizma_r_360_s13.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s14.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s15.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s16.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s17.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s18.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s19.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s20.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s21.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s22.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s23.png','2016-09-06 20:07:46'),"
                + "(19,'karizma_r_360_s24.png','2016-09-06 20:07:46')";

        final String INSERT_TABLE6 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(20,'spl_pro_classic_wasv_360_s01.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s02.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s03.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s04.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s05.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s06.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s07.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s08.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s09.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s10.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s11.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s12.png','2016-09-06 20:08:50'),"
                + "(20,'spl_pro_classic_wasv_360_s13.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s14.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s15.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s16.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s17.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s18.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s19.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s20.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s21.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s22.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s23.png','2016-09-06 20:09:31'),"
                + "(20,'spl_pro_classic_wasv_360_s24.png','2016-09-06 20:09:31')";

        final String INSERT_TABLE7 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(21,'xtreme_sports_360_s01.png','2016-09-06 20:10:18'),"
                + "(21,'xtreme_sports_360_s02.png','2016-09-06 20:10:18'),"
                + "(21,'xtreme_sports_360_s03.png','2016-09-06 20:10:18'),"
                + "(21,'xtreme_sports_360_s04.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s05.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s06.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s07.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s08.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s09.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s10.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s11.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s12.png','2016-09-06 20:10:19'),"
                + "(21,'xtreme_sports_360_s13.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s14.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s15.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s16.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s17.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s18.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s19.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s20.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s21.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s22.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s23.png','2016-09-06 20:10:57'),"
                + "(21,'xtreme_sports_360_s24.png','2016-09-06 20:10:57')";

        final String INSERT_TABLE8 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(22,'xtreme_360_s01.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s02.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s03.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s04.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s05.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s06.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s07.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s08.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s09.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s10.png','2016-09-06 20:11:50'),"
                + "(22,'xtreme_360_s11.png','2016-09-06 20:11:51'),"
                + "(22,'xtreme_360_s12.png','2016-09-06 20:11:51'),"
                + "(22,'xtreme_360_s13.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s14.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s15.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s16.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s17.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s18.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s19.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s20.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s21.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s22.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s23.png','2016-09-06 20:12:27'),"
                + "(22,'xtreme_360_s24.png','2016-09-06 20:12:27')";

        final String INSERT_TABLE9 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(23,'hunk_360_degree_s01.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s02.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s03.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s04.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s05.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s06.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s07.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s08.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s09.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s10.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s11.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s12.png','2016-09-06 20:13:05'),"
                + "(23,'hunk_360_degree_s13.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s14.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s15.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s16.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s17.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s18.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s19.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s20.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s21.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s22.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s23.png','2016-09-06 20:13:44'),"
                + "(23,'hunk_360_degree_s24.png','2016-09-06 20:13:44')";

        final String INSERT_TABLE10 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(24,'hf_deluxe_new_360d_s01.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s02.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s03.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s04.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s05.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s06.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s07.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s08.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s09.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s10.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s11.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s12.png','2016-09-06 20:15:18'),"
                + "(24,'hf_deluxe_new_360d_s13.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s14.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s15.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s16.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s17.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s18.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s19.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s20.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s21.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s22.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s23.png','2016-09-06 20:15:47'),"
                + "(24,'hf_deluxe_new_360d_s24.png','2016-09-06 20:15:47')";

        final String INSERT_TABLE11 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(25,'hf_deluxe_eco_360d_s01.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s02.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s03.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s04.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s05.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s06.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s07.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s08.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s09.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s10.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s11.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s12.png','2016-09-06 20:15:59'),"
                + "(25,'hf_deluxe_eco_360d_s13.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s14.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s15.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s16.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s17.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s18.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s19.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s20.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s21.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s22.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s23.png','2016-09-06 20:16:18'),"
                + "(25,'hf_deluxe_eco_360d_s24.png','2016-09-06 20:16:18')";

        final String INSERT_TABLE12 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(26,'achiever_grey_360d_s01.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s02.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s03.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s04.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s05.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s06.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s07.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s08.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s09.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s10.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s11.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s12.png','2016-09-06 20:16:30'),"
                + "(26,'achiever_grey_360d_s13.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s14.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s15.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s16.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s17.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s18.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s19.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s20.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s21.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s22.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s23.png','2016-09-06 20:16:43'),"
                + "(26,'achiever_grey_360d_s24.png','2016-09-06 20:16:43')";

        final String INSERT_TABLE13 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(27,'splendor_power_start_cb_red_360d_01.png','2016-09-06 20:31:30'),"
                + "(27,'splendor_power_start_cb_red_360d_02.png','2016-09-06 20:31:30'),"
                + "(27,'splendor_power_start_cb_red_360d_03.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_04.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_05.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_06.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_07.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_08.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_09.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_10.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_11.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_12.png','2016-09-06 20:31:31'),"
                + "(27,'splendor_power_start_cb_red_360d_13.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_14.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_15.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_16.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_17.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_18.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_19.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_20.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_21.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_22.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_23.png','2016-09-06 20:32:07'),"
                + "(27,'splendor_power_start_cb_red_360d_24.png','2016-09-06 20:32:07')";

        final String INSERT_TABLE14 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(28,'ignitor_360_d_s01.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s02.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s03.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s04.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s05.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s06.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s07.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s08.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s09.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s10.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s11.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s12.png','2016-09-06 20:18:18'),"
                + "(28,'ignitor_360_d_s13.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s14.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s15.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s16.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s17.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s18.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s19.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s20.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s21.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s22.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s23.png','2016-09-06 20:18:39'),"
                + "(28,'ignitor_360_d_s24.png','2016-09-06 20:18:39')";

        final String INSERT_TABLE15 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(29,'glamour_new_fi_360d_s01.png','2016-09-06 20:19:24'),"
                + "(29,'glamour_new_fi_360d_s02.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s03.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s04.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s05.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s06.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s07.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s08.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s09.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s10.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s11.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s12.png','2016-09-06 20:19:25'),"
                + "(29,'glamour_new_fi_360d_s13.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s14.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s15.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s16.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s17.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s18.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s19.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s20.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s21.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s22.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s23.png','2016-09-06 20:19:32'),"
                + "(29,'glamour_new_fi_360d_s24.png','2016-09-06 20:19:32')";

        final String INSERT_TABLE16 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(30,'passion_pro_tr_360_d_s01.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s02.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s03.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s04.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s05.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s06.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s07.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s08.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s09.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s10.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s11.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s12.png','2016-09-06 20:20:14'),"
                + "(30,'passion_pro_tr_360_d_s13.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s14.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s15.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s16.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s17.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s18.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s19.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s20.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s21.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s22.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s23.png','2016-09-06 20:20:31'),"
                + "(30,'passion_pro_tr_360_d_s24.png','2016-09-06 20:20:31')";

        final String INSERT_TABLE17 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(31,'splendor_i_smart_wasv_360d_s01.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s02.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s03.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s04.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s05.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s06.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s07.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s08.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s09.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s10.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s11.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s12.png','2016-09-06 20:21:37'),"
                + "(31,'splendor_i_smart_wasv_360d_s13.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s14.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s15.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s16.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s17.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s18.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s19.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s20.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s21.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s22.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s23.png','2016-09-06 20:22:03'),"
                + "(31,'splendor_i_smart_wasv_360d_s24.png','2016-09-06 20:22:03')";

        final String INSERT_TABLE18 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(32,'splendor_pro_j1_wasv_360d_s01.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s02.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s03.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s04.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s05.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s06.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s07.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s08.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s09.png','2016-09-06 20:09:54'),"
                + "(32,'splendor_pro_j1_wasv_360d_s10.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s11.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s12.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s13.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s14.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s15.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s16.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s17.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s18.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s19.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s20.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s21.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s22.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s23.png','2016-09-06 20:10:30'),"
                + "(32,'splendor_pro_j1_wasv_360d_s24.png','2016-09-06 20:10:30')";

        final String INSERT_TABLE19 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(33,'hf_dawn_360_degree_s01.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s02.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s03.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s04.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s05.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s06.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s07.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s08.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s09.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s10.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s11.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s12.png','2016-09-06 20:12:05'),"
                + "(33,'hf_dawn_360_degree_s13.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s14.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s15.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s16.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s17.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s18.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s19.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s20.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s21.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s22.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s23.png','2016-09-06 20:12:29'),"
                + "(33,'hf_dawn_360_degree_s24.png','2016-09-06 20:12:29')";

        final String INSERT_TABLE20 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(34,'duet_360d_s01.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s02.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s03.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s04.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s05.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s06.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s07.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s08.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s09.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s10.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s11.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s12.png','2016-09-06 20:12:54'),"
                + "(34,'duet_360d_s13.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s14.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s15.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s16.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s17.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s18.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s19.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s20.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s21.png','2016-09-06 20:13:27'),"
                + "(34,'duet_360d_s22.png','2016-09-06 20:13:28'),"
                + "(34,'duet_360d_s23.png','2016-09-06 20:13:28'),"
                + "(34,'duet_360d_s24.png','2016-09-06 20:13:28')";

        final String INSERT_TABLE21 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(35,'maestro_edge_techno_blue_360_degree_s01.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s02.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s03.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s04.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s05.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s06.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s07.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s08.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s09.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s10.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s11.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s12.png','2016-09-06 20:13:59'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s13.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s14.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s15.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s16.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s17.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s18.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s19.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s20.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s21.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s22.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s23.png','2016-09-06 20:14:04'),"
                + "(35,'maestro_edge_techno_blue_360_degree_s24.png','2016-09-06 20:14:04')";

        final String INSERT_TABLE22 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(36,'maestro_360_d_s01.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s02.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s03.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s04.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s05.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s06.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s07.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s08.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s09.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s10.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s11.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s12.png','2016-09-06 20:15:13'),"
                + "(36,'maestro_360_d_s13.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s14.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s15.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s16.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s17.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s18.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s19.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s20.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s21.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s22.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s23.png','2016-09-06 20:15:24'),"
                + "(36,'maestro_360_d_s24.png','2016-09-06 20:15:24')";

        final String INSERT_TABLE23 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(37,'pleasure_360_degree_s01.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s02.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s03.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s04.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s05.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s06.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s07.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s08.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s09.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s10.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s11.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s12.png','2016-09-06 20:15:50'),"
                + "(37,'pleasure_360_degree_s13.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s14.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s15.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s16.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s17.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s18.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s19.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s20.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s21.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s22.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s23.png','2016-09-06 20:16:55'),"
                + "(37,'pleasure_360_degree_s24.png','2016-09-06 20:16:55')";

        final String INSERT_TABLE24 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(38,'passion_x_pro_360_degree_s01.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s02.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s03.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s04.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s05.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s06.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s07.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s08.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s09.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s10.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s11.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s12.png','2016-09-06 20:17:24'),"
                + "(38,'passion_x_pro_360_degree_s13.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s14.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s15.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s16.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s17.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s18.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s19.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s20.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s21.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s22.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s23.png','2016-09-06 20:18:38'),"
                + "(38,'passion_x_pro_360_degree_s24.png','2016-09-06 20:18:38')";

        final String INSERT_TABLE25 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(39,'glamour_360d_s01.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s02.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s03.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s04.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s05.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s06.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s07.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s08.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s09.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s10.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s11.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s12.png','2016-09-06 20:19:15'),"
                + "(39,'glamour_360d_s13.png','2016-09-06 20:20:13'),"
                + "(39,'glamour_360d_s14.png','2016-09-06 20:20:13'),"
                + "(39,'glamour_360d_s15.png','2016-09-06 20:20:13'),"
                + "(39,'glamour_360d_s16.png','2016-09-06 20:20:13'),"
                + "(39,'glamour_360d_s17.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s18.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s19.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s20.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s21.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s22.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s23.png','2016-09-06 20:20:14'),"
                + "(39,'glamour_360d_s24.png','2016-09-06 20:20:14')";

        final String INSERT_TABLE26 = "INSERT INTO " +
                ProductRotationTable.TABLE_NAME + " (" +
                ProductRotationTable.Cols.PRODUCT_ID + ", " +
                ProductRotationTable.Cols.IMAGE_NAME + ", " +
                ProductRotationTable.Cols.CREATED_DATE + ")" +
                " VALUES "
                + "(54,'splendor_ismart_110_360d_s01.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s02.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s03.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s04.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s05.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s06.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s07.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s08.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s09.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s10.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s11.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s12.png','2016-09-06 20:21:01'),"
                + "(54,'splendor_ismart_110_360d_s13.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s14.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s15.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s16.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s17.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s18.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s19.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s20.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s21.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s22.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s23.png','2016-09-06 20:21:32'),"
                + "(54,'splendor_ismart_110_360d_s24.png','2016-09-06 20:21:32')";


        try {
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE1);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE2);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE3);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE4);
            //  SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE5);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE6);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE7);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE8);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE9);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE10);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE11);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE12);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE13);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE14);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE15);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE16);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE17);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE18);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE19);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE20);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE21);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE22);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE23);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE24);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE25);
            SQLiteDataHelper.getInstance(this).getDatabase().execSQL(INSERT_TABLE26);
            sharedPreferences.edit().putBoolean(AppConstants.IS_360_RECORD_INSERTED, true).commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update version_notification
    public void push_notification(String version, String path) {
        Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        resultIntent.setData(Uri.parse(path));
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;//Your request code
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        String message = "Click to upgrade app";
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.hero_logo)
                .setContentTitle("New Version Available(" + version + ")")
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.hero_logo))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build());

    }


    //version check
    public class check_version extends AsyncTask<String, Void, String> {

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(String... params) {
            try {
                if (NetConnections.isConnected(getApplicationContext())) {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL url;
                    HttpURLConnection connection = null;
                    try {
                        //Create connection
                        url = new URL(params[0]);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(true);
                        //Get Response
                        InputStream is = connection.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        String line;
                        StringBuffer response = new StringBuffer();
                        while ((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();
                        return response.toString();

                    } catch (Exception e) {

                        e.printStackTrace();
                        return null;

                    } finally {

                        if (connection != null) {
                            connection.disconnect();
                        }
                    }

                } else
                    Toast.makeText(

                            getApplicationContext(),

                            "Check your connection !!", Toast.LENGTH_SHORT).

                            show();

            } catch (
                    Exception e
                    )

            {

                e.printStackTrace();
                return null;

            } finally

            {

            }

            return response.toString();
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Log.e("version_response", result);
                result = result.replace("[", "");
                result = result.replace("]", "");
                result = result.replace("\\/", "/");
                JSONObject jsono = new JSONObject(result);
                String newversion = jsono.getString("version");
                String new_version = newversion.replace(".", "");
                String path = jsono.getString("path");
                appVersion = appVersion.replace(".", "");
                if (Integer.parseInt(appVersion) < Integer.parseInt(new_version)) {
                    push_notification(newversion, path);
                    update_alert(newversion, path);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(Toast.makeText(getApplicationContext(), "Check your Connection !!", Toast.LENGTH_SHORT));
            }

        }
    }


    public void update_alert(String version, String path) {
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        bundle.putString("header", "Message");
        bundle.putString("msg", "New Version Available(v " + version + ")");
        bundle.putInt("flag", 1);
        bundle.putString("name1", "DOWNLOAD");
        FragmentManager fm = getSupportFragmentManager();
        ContactAlertFragment dialogFragment = new ContactAlertFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        dialogFragment.show(fm, "Sample Fragment");
    }


    public void fetch_data() {
        String newversion, path;
        sharedPreferences = getSharedPreferences("hero", 0);
        newversion = sharedPreferences.getString("version", "1.0");
        path = sharedPreferences.getString("path", null);

        String new_version = newversion.replace(".", "");
        appVersion = appVersion.replace(".", "");
        if (Integer.parseInt(appVersion) < Integer.parseInt(new_version)) {
            push_notification(newversion, path);
            update_alert(newversion, path);
        }
    }
}



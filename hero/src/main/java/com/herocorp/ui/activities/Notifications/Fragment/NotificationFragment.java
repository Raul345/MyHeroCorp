package com.herocorp.ui.activities.Notifications.Fragment;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herocorp.R;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.Notifications.Adapter.Notificationadapter;
import com.herocorp.ui.activities.Notifications.Model.Notification;

import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rsawh on 02-Feb-17.
 */

public class NotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Notificationadapter userAdapter;
    ArrayList<Notification> userArray = new ArrayList<Notification>();
    ListView userList;
    SwipeRefreshLayout swipe_refresh_notify;
    TextView notify_msg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.notification_fragment, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((BaseDrawerActivity) getActivity()).closeDrawer();
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        CustomViewParams customViewParams = new CustomViewParams(getActivity());
        CustomTypeFace customTypeFace = new CustomTypeFace(getActivity());

        ImageView heroLogo = (ImageView) rootView.findViewById(R.id.app_logo);
        ImageView menu = (ImageView) rootView.findViewById(R.id.menu_icon);

        customViewParams.setImageViewCustomParams(heroLogo, new int[]{30, 30, 0, 0}, new int[]{0, 0, 0, 0}, 120, 120);
        customViewParams.setImageViewCustomParams(menu, new int[]{0, 30, 30, 0}, new int[]{0, 0, 0, 0}, 100, 100);

        LinearLayout topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        customViewParams.setMarginAndPadding(topLayout, new int[]{80, 30, 80, 0}, new int[]{0, 0, 0, 0}, topLayout.getParent());

        Button buttonHeader = (Button) rootView.findViewById(R.id.buttonHeader);
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{30, 0, 0, 0}, 90, 400, 40, customTypeFace.gillSansItalic, 0);

        RelativeLayout detailLayout = (RelativeLayout) rootView.findViewById(R.id.detail_layout);
        customViewParams.setMarginAndPadding(detailLayout, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, detailLayout.getParent());

        userAdapter = new Notificationadapter(getContext(), R.layout.notification_layout_row, userArray);

        userList = (ListView) rootView.findViewById(R.id.list_notifications);

        notify_msg = (TextView) rootView.findViewById(R.id.notify_message);
        swipe_refresh_notify = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_notify);
        customViewParams.setMarginAndPadding(swipe_refresh_notify, new int[]{80, 20, 80, 20}, new int[]{0, 0, 0, 0}, swipe_refresh_notify.getParent());


        userList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userList.getChildAt(0) != null) {
                    swipe_refresh_notify.setEnabled(userList.getFirstVisiblePosition() == 0 && userList.getChildAt(0).getTop() == 0);
                }
            }
        });

       /* userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News data = userAdapter.getItem(position);
                String url = data.getUrl();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                Fragment f = new NewsWebViewLayout();
                f.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.content_news, f);
                ft.commit();

            }
        });*/
        swipe_refresh_notify.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          swipe_refresh_notify.setRefreshing(true);
                                          // send_request();
                                          new Fetch_news().execute(URLConstants.FETCH_NEWS);

                                      }
                                  }
        );


        menu.setOnClickListener(new View.OnClickListener()

                                {
                                    @Override
                                    public void onClick(View v) {
                                        ((BaseDrawerActivity) getActivity()).toggleDrawer();
                                    }
                                }

        );
        swipe_refresh_notify.setOnRefreshListener(this);
    }

    private void updateList() {
        userList.setAdapter(userAdapter);
        if (userAdapter.getCount() > 0)
            notify_msg.setVisibility(View.INVISIBLE);
        else
            notify_msg.setVisibility(View.VISIBLE);

    }

    public void onDestroy() {
        userList.setAdapter(null);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        new Fetch_news().execute(URLConstants.FETCH_NEWS);

    }

    //version check
    public class Fetch_news extends AsyncTask<String, Void, String> {

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipe_refresh_notify.setRefreshing(true);
        }


        protected String doInBackground(String... params) {
            try {
                if (NetConnections.isConnected(getContext())) {

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

                } else {

                    Toast.makeText(

                            getContext(),

                            "Internal Server Error!!", Toast.LENGTH_SHORT).

                            show();
                }
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
                Log.e("news_response", result);
                userAdapter.clear();
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("Success").equalsIgnoreCase("TRUE")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("Result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String heading = j.getString("heading");
                        String url = j.getString("news_url");
                        String create_date = j.getString("created_date");
                        userAdapter.add(new Notification(heading, create_date));
                    }
                }
               /* GsonBuilder builder = new GsonBuilder();
                Object o = builder.create().fromJson(json.toString(), Object.class);
                Log.e("gson", o.toString() + "");*/

               /* GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(NewsList.class, new NewsListDeserializer());
                Gson gson = builder.create();
                NewsList list = gson.fromJson(json.toString(), NewsList.class);

                //System.out.println(list.newsdetail);
                Log.e("gson", list.newsdetail.toString());*/
                swipe_refresh_notify.setRefreshing(false);
/*
                userAdapter.add(new News("http://www.heromotocorp.com/en-in/press-releases/hero-motocorp-launches-a-slew-of-road-safety-initiatives-in-partnership-with-gurgaon-police--258.html", "bike 1jdjssjdjshdkjs sfsf ss", "20-11-2016"));
                userAdapter.add(new News("http://www.heromotocorp.com/en-in/press-releases/hero-motocorp-commences-the-new-year-with-robust-sales-259.html", "bike 2jdjssjdjshdkjs sfsf ss", "10-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 5jdjssjdjshdkjs sfsf ss", "30-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 1jdjssjdjshdkjs sfsf ss", "20-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 2jdjssjdjshdkjs sfsf ss", "10-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 5jdjssjdjshdkjs sfsf ss", "30-11-2016"));*/
                userAdapter.notifyDataSetChanged();

                updateList();

            } catch (JSONException e) {
                swipe_refresh_notify.setRefreshing(false);
                notify_msg.setVisibility(View.VISIBLE);
                e.printStackTrace();
            } catch (Exception e) {
                swipe_refresh_notify.setRefreshing(false);
                Log.e("internal_error_news", e.toString());
                notify_msg.setVisibility(View.VISIBLE);
                // Toast.makeText(getContext(), "Internal Server Error!!" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }


}


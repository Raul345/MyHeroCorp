package com.herocorp.ui.activities.news;

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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.herocorp.R;

import com.herocorp.core.constants.URLConstants;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.activities.BaseDrawerActivity;

import com.herocorp.ui.activities.DSEapp.ConnectService.NetworkConnect;
import com.herocorp.ui.activities.news.Model.News;
import com.herocorp.ui.activities.news.adapter.Newsadapter;
import com.herocorp.ui.utility.CustomTypeFace;
import com.herocorp.ui.utility.CustomViewParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

/**
 * Created by rsawh on 21-Dec-16.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Newsadapter userAdapter;
    ArrayList<News> userArray = new ArrayList<News>();
    ListView userList;
    SwipeRefreshLayout swipe_refresh_news;
    TextView news_msg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
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
        customViewParams.setButtonCustomParams(buttonHeader, new int[]{0, 10, 0, 10}, new int[]{120, 0, 0, 0}, 90, 400, 40, customTypeFace.gillSansItalic, 0);

        RelativeLayout detailLayout = (RelativeLayout) rootView.findViewById(R.id.detail_layout);
        customViewParams.setMarginAndPadding(detailLayout, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, detailLayout.getParent());

        userAdapter = new Newsadapter(getContext(), R.layout.news_layout_row, userArray);

        userList = (ListView) rootView.findViewById(R.id.list_news);

        news_msg = (TextView) rootView.findViewById(R.id.news_message);
        swipe_refresh_news = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_news);
        customViewParams.setMarginAndPadding(swipe_refresh_news, new int[]{80, 20, 80, 20}, new int[]{0, 0, 0, 0}, swipe_refresh_news.getParent());


        userList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userList.getChildAt(0) != null) {
                    swipe_refresh_news.setEnabled(userList.getFirstVisiblePosition() == 0 && userList.getChildAt(0).getTop() == 0);
                }
            }
        });

        swipe_refresh_news.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipe_refresh_news.setRefreshing(true);
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
        swipe_refresh_news.setOnRefreshListener(this);
    }

    private void updateList() {
        userList.setAdapter(userAdapter);
       /* if (userAdapter.getCount() > 0)
            pendingorders_msg.setVisibility(View.INVISIBLE);
        else
            pendingorders_msg.setVisibility(View.VISIBLE);
        swipe_refresh_orders.setRefreshing(false);*/
    }

    public void onDestroy() {
        userList.setAdapter(null);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        //  send_request();
    }

    //version check
    public class Fetch_news extends AsyncTask<String, Void, String> {

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipe_refresh_news.setRefreshing(true);
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
               // Log.e("news_response", result);

                JSONObject jsonObject = new JSONObject(result);
                JSONObject json = jsonObject.getJSONObject("Result");
               /* GsonBuilder builder = new GsonBuilder();
                Object o = builder.create().fromJson(json.toString(), Object.class);
                Log.e("gson", o.toString() + "");*/

                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(NewsList.class, new NewsListDeserializer());
                Gson gson = builder.create();
                NewsList list = gson.fromJson(json.toString(), NewsList.class);

                //System.out.println(list.newsdetail);
                Log.e("gson", list.newsdetail.toString());

                swipe_refresh_news.setRefreshing(false);
                userAdapter.add(new News("https://www.google.com", "bike 1jdjssjdjshdkjs sfsf ss", "20-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 2jdjssjdjshdkjs sfsf ss", "10-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 5jdjssjdjshdkjs sfsf ss", "30-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 1jdjssjdjshdkjs sfsf ss", "20-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 2jdjssjdjshdkjs sfsf ss", "10-11-2016"));
                userAdapter.add(new News("https://www.google.com", "bike 5jdjssjdjshdkjs sfsf ss", "30-11-2016"));
                userAdapter.notifyDataSetChanged();

                updateList();

            } catch (JSONException e) {
                swipe_refresh_news.setRefreshing(false);
                e.printStackTrace();
            } catch (Exception e) {
                swipe_refresh_news.setRefreshing(false);
                Toast.makeText(getContext(), "Internal Server Error!!" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private static class NewsList {
        List<NewsDetail> newsdetail;

        private NewsList(List<NewsDetail> newsdetail) {
            this.newsdetail = newsdetail;
        }
    }

    private static class NewsDetail {
        String id_news;
        String heading;
        String createdby;
        String createddate;
        // and so on


        @Override
        public String toString() {
            return "News{" +
                    "id_news='" + id_news + '\'' +
                    ", heading='" + heading + '\'' +
                    ", createdby='" + createdby + '\'' +
                    ", createddate='" + createddate + '\'' +
                    '}';
        }
    }

    private static class NewsListDeserializer implements JsonDeserializer<NewsList> {
        @Override
        public NewsList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = jsonElement.getAsJsonObject();
            List<NewsDetail> newss = new ArrayList<NewsDetail>();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
//                System.out.println(entry.getKey() + " " + entry.getValue());
                // Use default deserialisation for City objects:
                NewsDetail city = context.deserialize(entry.getValue(), NewsDetail.class);
                newss.add(city);
            }
            return new NewsList(newss);
        }
    }


}

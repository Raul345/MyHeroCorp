package com.herocorp.core.constants;

/**
 * URL constant class.
 * Created by rsawh on 21/09/16.
 */
public class URLConstants {

    /*Base URL*/
    private final static String BASE_URL_LIVE = "http://tab.hmcl.biz/heroDealerApp/";
    private final static String BASE_URL_DEV = "http://abym.in/clientProof/hero_motors/";

    //Base URL dse version 2
    //  private final static String BASE_DSEURL_LIVE = "http://tab.hmcl.biz/dse_app_live_v2/";

    //Base URL dse version 3
    private final static String BASE_DSEURL_LIVE = "http://tab.hmcl.biz/dse_app_live_v3/";
    private final static String BASE_DSEURL_DEV = "http://tab.hmcl.biz/dse_app_UAT/";


    private final static String BASE_URL_QA = BASE_URL_LIVE;
    private final static String BASE_URL_LIVE_WEBSERVICE = "http://tab.hmcl.biz/heroDealerApp_UAT/webservices/Index";
    public static String BASE_URL;
    public static String BASE_DSE_URL = BASE_DSEURL_LIVE;

    // produce=URLs
    public final static String GET_CATEGORY_LIST = "categoriesList";
    public final static String GET_CATEGORY_PRODUCTS_LIST = "categoryProductsListing";
    public final static String GET_PRODUCT_DETAILS = "productDetails";

    public final static String GET_PRODUCT_IMAGE = "upload/ProductsImage/";
    public final static String GET_PRODUCT_COLOR_IMAGE = "upload/colorsIcon/";
    public final static String AUTHENTICATE_USER = "userAuthentication";

    public final static String ENCRYPT1 = "http://abym.in/clientProof/hero_motors/encrypt";
    public final static String ENCRYPT = "http://tab.hmcl.biz/heroDealerApp/encrypt";


    //DSE LIVE URLS version 2

    public final static String LOGIN = BASE_DSE_URL + "login.php";
    public final static String PENDING_ORDER = BASE_DSE_URL + "fetch_order_data.php";
    public final static String PENDING_FOLLOWUP = BASE_DSE_URL + "get_follow_up.php";
    public final static String BIKE_MAKE_MODEL = BASE_DSE_URL + "bike_make_model.php";
    public final static String SYNC_FOLLOW_UP = BASE_DSE_URL + "sync_follow_up.php";
    public final static String GET_DISTRICT = BASE_DSE_URL + "get_district.php";
    public final static String GET_DISTRICT_DATA = BASE_DSE_URL + "get_district_data.php";
    public final static String GET_CAMPAIGN_DATA = BASE_DSE_URL + "fetch_campaign_data.php";
    public final static String ADD_ENQUIRY = BASE_DSE_URL + "syncRecords.php";
    public final static String FETCH_TEST_RIDE = BASE_DSE_URL + "fetch_test_ride.php";
    public final static String SYNC_TEST_RIDE = BASE_DSE_URL + "sync_test_ride.php";
    public final static String CHECK_VERSION = BASE_DSE_URL + "check_version.php";
    public final static String FETCH_CONTACT = BASE_DSE_URL + "fetch_contact.php";
    public final static String FETCH_PITCH = BASE_DSE_URL + "sales_pitch.php";


    public final static String FETCH_NEWS = "http://abym.in/clientProof/hero_motors/webservices/Index/newsListings";
    public final static String TOKEN_DATA = "http://abym.in/clientProof/hero_motors/userAuthentication";
    public final static String FETCH_VAS = "http://abym.in/clientProof/hero_motors/webservices/Index/valueAddedServices";

    // public final static String TOKEN_DATA = BASE_URL_LIVE_WEBSERVICE + "/userAuthentication";
    // public final static String FETCH_NEWS = BASE_URL_LIVE_WEBSERVICE + "/newsListings";
    //public final static String FETCH_VAS = BASE_URL_LIVE_WEBSERVICE + "/valueAddedServices";
    public final static String FETCH_FAQ = BASE_URL_LIVE_WEBSERVICE + "/getFaqs";


    /*Static Block*/
    /* Which Products environment is currently active */
    static {
        if (AppConstants.isLive) {
            URLConstants.BASE_URL = URLConstants.BASE_URL_LIVE;
        } else if (AppConstants.isDebug) {
            URLConstants.BASE_URL = URLConstants.BASE_URL_DEV;
        } else {
            URLConstants.BASE_URL = URLConstants.BASE_URL_QA;
        }
    }

    /*Static Block*/
    /* Which DSE environment is currently active */
    static {
        if (AppConstants.isDSELive) {
            URLConstants.BASE_DSE_URL = URLConstants.BASE_DSEURL_LIVE;
        } else if (AppConstants.isDSEDebug) {
            URLConstants.BASE_DSE_URL = URLConstants.BASE_DSEURL_DEV;
        } else {
            URLConstants.BASE_DSE_URL = URLConstants.BASE_DSEURL_LIVE;
        }
    }
}

package com.herocorp.core.constants;

/**
 * URL constant class.
 * Created by rsawh on 21/09/16.
 */
public class URLConstants {

    /*Base URL*/
    private final static String BASE_URL_LIVE = "http://tab.hmcl.biz/heroDealerApp/";
    private final static String BASE_URL_DEV = "http://abym.in/clientProof/hero_motors/";
    private final static String BASE_URL_QA = BASE_URL_LIVE;
    public static String BASE_URL;

    //URLs
    public final static String GET_CATEGORY_LIST = "categoriesList";
    public final static String GET_CATEGORY_PRODUCTS_LIST = "categoryProductsListing";
    public final static String GET_PRODUCT_DETAILS = "productDetails";

    public final static String GET_PRODUCT_IMAGE = "upload/ProductsImage/";
    public final static String GET_PRODUCT_COLOR_IMAGE = "upload/colorsIcon/";
    public final static String AUTHENTICATE_USER = "userAuthentication";

    //DSE APP
    public final static String TOKEN_DATA = "http://abym.in/clientProof/hero_motors/userAuthentication";
    public final static String ENCRYPT1 = "http://abym.in/clientProof/hero_motors/encrypt";
 /*   public final static String ENCRYPT = "http://tab.hmcl.biz/heroDealerApp/encrypt";
    public final static String LOGIN = "http://tab.hmcl.biz/dse_app_UAT/login.php";
    public final static String PENDING_ORDER = "http://tab.hmcl.biz/dse_app_UAT/fetch_order_data.php";
    public final static String PENDING_FOLLOWUP = "http://tab.hmcl.biz/dse_app_UAT/get_follow_up.php";
    public final static String BIKE_MAKE_MODEL = "http://tab.hmcl.biz/dse_app_UAT/bike_make_model.php";
    public final static String SYNC_FOLLOW_UP = "http://tab.hmcl.biz/dse_app_UAT/sync_follow_up.php";
    public final static String GET_DISTRICT = "http://tab.hmcl.biz/dse_app_UAT/get_district.php";
    public final static String GET_DISTRICT_DATA = "http://tab.hmcl.biz/dse_app_UAT/get_district_data.php";
    public final static String GET_CAMPAIGN_DATA = "http://tab.hmcl.biz/dse_app_UAT/fetch_campaign_data.php";
    public final static String ADD_ENQUIRY = "http://tab.hmcl.biz/dse_app_UAT/syncRecords.php";
    public final static String FETCH_TEST_RIDE = "http://tab.hmcl.biz/dse_app_UAT/fetch_test_ride.php";
    public final static String SYNC_TEST_RIDE = "http://tab.hmcl.biz/dse_app_UAT/sync_test_ride.php";
    public final static String CHECK_VERSION = "http://tab.hmcl.biz/dse_app_UAT/check_version.php";
    public final static String FETCH_CONTACT = "http://tab.hmcl.biz/dse_app_UAT/fetch_contact.php";*/

    public final static String ENCRYPT = "http://tab.hmcl.biz/heroDealerApp/encrypt";
    public final static String LOGIN = "http://tab.hmcl.biz/dse_app_live/login.php";
    public final static String PENDING_ORDER = "http://tab.hmcl.biz/dse_app_live/fetch_order_data.php";
    public final static String PENDING_FOLLOWUP = "http://tab.hmcl.biz/dse_app_live/get_follow_up.php";
    public final static String BIKE_MAKE_MODEL = "http://tab.hmcl.biz/dse_app_live/bike_make_model.php";
    public final static String SYNC_FOLLOW_UP = "http://tab.hmcl.biz/dse_app_live/sync_follow_up.php";
    public final static String GET_DISTRICT = "http://tab.hmcl.biz/dse_app_live/get_district.php";
    public final static String GET_DISTRICT_DATA = "http://tab.hmcl.biz/dse_app_live/get_district_data.php";
    public final static String GET_CAMPAIGN_DATA = "http://tab.hmcl.biz/dse_app_live/fetch_campaign_data.php";
    public final static String ADD_ENQUIRY = "http://tab.hmcl.biz/dse_app_live/syncRecords.php";
    public final static String FETCH_TEST_RIDE = "http://tab.hmcl.biz/dse_app_live/fetch_test_ride.php";
    public final static String SYNC_TEST_RIDE = "http://tab.hmcl.biz/dse_app_live/sync_test_ride.php";
    public final static String CHECK_VERSION = "http://tab.hmcl.biz/dse_app_live/check_version.php";
    public final static String FETCH_CONTACT = "http://tab.hmcl.biz/dse_app_live/fetch_contact.php";
    public final static String FETCH_NEWS="http://abym.in/clientProof/hero_motors/webservices/Index/newsListings";

    /*Static Block*/
    /* Which environment is currently active */
    static {

        if (AppConstants.isLive) {
            URLConstants.BASE_URL = URLConstants.BASE_URL_LIVE;
        } else if (AppConstants.isDebug) {
            URLConstants.BASE_URL = URLConstants.BASE_URL_DEV;
        } else {
            URLConstants.BASE_URL = URLConstants.BASE_URL_QA;
        }
    }

}

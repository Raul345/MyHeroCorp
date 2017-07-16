package com.herocorp.infra.netio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.herocorp.core.constants.URLConstants;
import com.herocorp.core.interfaces.SyncServiceCallBack;
import com.herocorp.core.interfaces.iNetworkResponseCallback;
import com.herocorp.core.models.ProductBreakModel;
import com.herocorp.core.models.ProductCategoryModel;
import com.herocorp.core.models.ProductColorModel;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.core.models.ProductDimensionModel;
import com.herocorp.core.models.ProductElectricalModel;
import com.herocorp.core.models.ProductEngineModel;
import com.herocorp.core.models.ProductFeatureModel;
import com.herocorp.core.models.ProductGalleryModel;
import com.herocorp.core.models.ProductModel;
import com.herocorp.core.models.ProductSuperFeatureModel;
import com.herocorp.core.models.ProductSuspensionModel;
import com.herocorp.core.models.ProductTransmissionModel;
import com.herocorp.core.models.ProductTyreModel;
import com.herocorp.core.models.aggregates.ProductDetailAggregate;
import com.herocorp.infra.parsers.AppJsonParser;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.ui.app.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Product Net I/O Service Class.
 * Created by JitainSharma on 12/06/16.
 */
public class ProductIOService extends BaseNetIO {

    /**
     * Call API to fetch Product Categories.
     * Parse and return the response.
     *
     * @param tag
     * @param callback
     * @throws Exception
     */
    public static void getProductCategories(final String tag,
                                            final iNetworkResponseCallback<ProductCategoryModel> callback) throws Exception {

        JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(
                        Request.Method.POST,
                        URLConstants.BASE_URL + URLConstants.GET_CATEGORY_LIST,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response != null) {
                                        if (response.getBoolean("Success")) {
                                            ArrayList<ProductCategoryModel> modelArrayList = new ArrayList<>(0);
                                            //Parse the JSON
                                            JSONArray responseDataString = response.getJSONArray("Result");
                                            for (int i = 0; i < responseDataString.length(); i++) {
                                                modelArrayList.add(AppJsonParser.getInstance()
                                                        .getObjectFromString(responseDataString.get(i).toString(),
                                                                ProductCategoryModel.class));
                                            }
                                            callback.onSuccess(modelArrayList);

                                            return;
                                        }

                                        callback.onFailure(response.getString("Message"), true);

                                    }
                                } catch (Exception e) {
                                    Log.e(tag, "Failed ", e);
                                    callback.onFailure("Failed", true);
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onFailure(errorMessage(error), true);
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getAccessHeaders();
                    }
                };

        setTimeOut(jsonObjReq);
        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

    public static void getProductByCategory(final String tag, final int categoryID,
                                            final iNetworkResponseCallback<ProductModel> callback) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("categoryId", String.valueOf(categoryID));

        JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(
                        Request.Method.POST,
                        URLConstants.BASE_URL + URLConstants.GET_CATEGORY_PRODUCTS_LIST,
                        jsonObject.toString(),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response != null) {

                                        if (response.getBoolean("Success")) {
                                            ArrayList<ProductModel> modelArrayList = new ArrayList<>(0);

                                            //Parse the JSON
                                            JSONArray responseDataString = response.getJSONArray("Result");
                                            for (int i = 0; i < responseDataString.length(); i++) {

                                                ProductModel productModel = AppJsonParser.getInstance()
                                                        .getObjectFromString(responseDataString.get(i).toString(),
                                                                ProductModel.class);
                                                productModel.setCategoryID(categoryID);

                                                modelArrayList.add(productModel);
                                            }
                                            callback.onSuccess(modelArrayList);

                                            return;
                                        }

                                        callback.onFailure(response.getString("Message"), true);

                                    }
                                } catch (Exception e) {
                                    Log.e(tag, "Failed ", e);
                                    callback.onFailure("Failed", true);
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onFailure(errorMessage(error), true);
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getAccessHeaders();
                    }
                };

        setTimeOut(jsonObjReq);
        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

    public static void getProductDetails(final String tag, final int productID,
                                         final iNetworkResponseCallback<ProductDetailAggregate> callback) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", productID);

        JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(
                        Request.Method.POST,
                        URLConstants.BASE_URL + URLConstants.GET_PRODUCT_DETAILS,
                        jsonObject.toString(),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response != null) {

                                        if (response.getBoolean("Success")) {

                                            ProductDetailAggregate productDetailAggregate = new ProductDetailAggregate();
                                            //Parse the JSON
                                            JSONArray responseDataString = response.getJSONArray("Result");

                                            if (responseDataString.length() > 0) {

                                                JSONObject object = responseDataString.getJSONObject(0);

                                                productDetailAggregate.setProductID(productID);


                                                productDetailAggregate.setTyreModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("tyres").toString(),
                                                                ProductTyreModel.class));
                                                productDetailAggregate.getTyreModel().setProductID(productID);

                                                productDetailAggregate.setTransmissionModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("transmissionAndFrame").toString(),
                                                                ProductTransmissionModel.class));
                                                productDetailAggregate.getTransmissionModel().setProductID(productID);

                                                productDetailAggregate.setBreakModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("breaks").toString(),
                                                                ProductBreakModel.class));
                                                productDetailAggregate.getBreakModel().setProductID(productID);


                                                productDetailAggregate.setDimensionModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("dimension").toString(),
                                                                ProductDimensionModel.class));
                                                productDetailAggregate.getDimensionModel().setProductID(productID);

                                                productDetailAggregate.setElectricalModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("electricals").toString(),
                                                                ProductElectricalModel.class));
                                                productDetailAggregate.getElectricalModel().setProductID(productID);

                                                productDetailAggregate.setEngineModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("engine").toString(),
                                                                ProductEngineModel.class));
                                                productDetailAggregate.getEngineModel().setProductID(productID);

                                                productDetailAggregate.setSuspensionModel(AppJsonParser.getInstance()
                                                        .getObjectFromString(object.getJSONObject("suspension").toString(),
                                                                ProductSuspensionModel.class));
                                                productDetailAggregate.getSuspensionModel().setProductID(productID);

                                                ArrayList<ProductColorModel> colorModelList = new ArrayList<>(0);
                                                //Set Product main_slider_img model which is color model
                                                if (object.has("main_slider_img")) {
                                                    JSONArray jsonArrayColor = object.getJSONArray("main_slider_img");

                                                    for (int i = 0; i < jsonArrayColor.length(); i++) {

                                                        ProductColorModel colorModel = AppJsonParser.getInstance()
                                                                .getObjectFromString(jsonArrayColor.get(i).toString(),
                                                                        ProductColorModel.class);

                                                        colorModel.setProductID(productID);
                                                        colorModelList.add(colorModel);
                                                    }

                                                }
                                                productDetailAggregate.setColorModel(colorModelList);


                                                ArrayList<ProductFeatureModel> featureModelList = new ArrayList<>(0);

                                                if (object.has("feature_img")) {
                                                    //For Product Feature Images
                                                    JSONArray jsonArrayFeature = object.getJSONArray("feature_img");

                                                    for (int i = 0; i < jsonArrayFeature.length(); i++) {

                                                        ProductFeatureModel featureModel = AppJsonParser.getInstance()
                                                                .getObjectFromString(jsonArrayFeature.get(i).toString(),
                                                                        ProductFeatureModel.class);

                                                        featureModel.setProductID(productID);
                                                        featureModelList.add(featureModel);

                                                    }

                                                }
                                                productDetailAggregate.setFeatureModel(featureModelList);


                                                //super features
                                                ArrayList<ProductSuperFeatureModel> superfeatureModelList = new ArrayList<>(0);

                                                if (object.has("super_feature_img")) {
                                                    //For Product Feature Images
                                                    JSONArray jsonArrayFeature = object.getJSONArray("super_feature_img");

                                                    for (int i = 0; i < jsonArrayFeature.length(); i++) {

                                                        ProductSuperFeatureModel featureModel = AppJsonParser.getInstance()
                                                                .getObjectFromString(jsonArrayFeature.get(i).toString(),
                                                                        ProductSuperFeatureModel.class);

                                                        featureModel.setProductID(productID);
                                                        superfeatureModelList.add(featureModel);

                                                    }

                                                }
                                                productDetailAggregate.setSuperfeatureModelList(superfeatureModelList);



                                                ArrayList<ProductGalleryModel> GalleryModelList = new ArrayList<>(0);
                                                if (object.has("gallery_img")) {
                                                    //Parse Product Gallery Img
                                                    JSONArray jsonArrayGallery = object.getJSONArray("gallery_img");
                                                    for (int i = 0; i < jsonArrayGallery.length(); i++) {

                                                        ProductGalleryModel galleryModel = AppJsonParser.getInstance()
                                                                .getObjectFromString(jsonArrayGallery.get(i).toString(),
                                                                        ProductGalleryModel.class);

                                                        galleryModel.setProductID(productID);
                                                        GalleryModelList.add(galleryModel);
                                                    }
                                                }
                                                productDetailAggregate.setGalleryModelList(GalleryModelList);

                                            }
                                            callback.onSuccess(productDetailAggregate);
                                            return;
                                        }

                                        callback.onFailure(response.getString("Message"), true);

                                    }
                                } catch (Exception e) {
                                    Log.e(tag, "Failed ", e);
                                    callback.onFailure("Failed", true);
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onFailure(errorMessage(error), true);
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getAccessHeaders();
                    }
                };

        setTimeOut(jsonObjReq);
        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

    public static void fetchImage(final String tag, String url, final String fileName,
                                  final SyncServiceCallBack syncServiceCallBack) {

        ImageRequest imgRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        ImageHandler.getInstance()
                                .saveImage(response, fileName);

                        syncServiceCallBack.completed();

                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                syncServiceCallBack.error();
            }
        });


        App.getInstance().addToRequestQueue(imgRequest, tag);

    }


    public static void getCompareDetails(final String tag,
                                         final iNetworkResponseCallback<ProductCompareModel> callback) throws Exception {

        JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(
                        Request.Method.POST,
                        URLConstants.BASE_URL + URLConstants.GET_PRODUCT_COMPARE_DATA,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response != null) {
                                        if (response.getBoolean("Success")) {
                                            ArrayList<ProductCompareModel> modelArrayList = new ArrayList<>(0);
                                            //Parse the JSON
                                            JSONArray responseDataString = response.getJSONArray("Result");
                                            for (int i = 0; i < responseDataString.length(); i++) {
                                                modelArrayList.add(AppJsonParser.getInstance()
                                                        .getObjectFromString(responseDataString.get(i).toString(),
                                                                ProductCompareModel.class));
                                            }
                                            callback.onSuccess(modelArrayList);

                                            return;
                                        }

                                        callback.onFailure(response.getString("Message"), true);

                                    }
                                } catch (Exception e) {
                                    Log.e(tag, "Failed ", e);
                                    callback.onFailure("Failed", true);
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onFailure(errorMessage(error), true);
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getAccessHeaders();
                    }
                };

        setTimeOut(jsonObjReq);
        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

}

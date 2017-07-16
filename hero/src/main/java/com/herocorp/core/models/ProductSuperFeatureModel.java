package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rsawh on 15-Jul-17.
 */

public class ProductSuperFeatureModel extends BaseEntity {

    private int _ID;
    private int productID;

    @JsonProperty("super_feature_img")
    private String super_feature_img;

    @JsonProperty("super_feature_img_text")
    private String super_feature_img_text;

    public ProductSuperFeatureModel() {
    }

    public ProductSuperFeatureModel(int _ID, int productID, String featureImg,
                               String featureImgText) {

        this._ID = _ID;
        this.productID = productID;
        this.super_feature_img = featureImg;
        this.super_feature_img_text= featureImgText;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getFeatureImg() {
        return super_feature_img;
    }

    public void setFeatureImg(String featureImg) {
        this.super_feature_img= featureImg;
    }

    public String getFeatureImgText() {
        return super_feature_img_text;
    }

    public void setFeatureImgText(String featureImgText) {
        this.super_feature_img_text = featureImgText;
    }
}

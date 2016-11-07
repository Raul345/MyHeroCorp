package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Feature Table Model Class.
 * Created by JitainSharma on 12/06/16.
 */
public class ProductFeatureModel extends BaseEntity {

    private int _ID;
    private int productID;

    @JsonProperty("feature_img")
    private String featureImg;

    @JsonProperty("feature_img_text")
    private String featureImgText;

    public ProductFeatureModel() {
    }

    public ProductFeatureModel(int _ID, int productID, String featureImg,
                               String featureImgText) {

        this._ID = _ID;
        this.productID = productID;
        this.featureImg = featureImg;
        this.featureImgText = featureImgText;
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
        return featureImg;
    }

    public void setFeatureImg(String featureImg) {
        this.featureImg = featureImg;
    }

    public String getFeatureImgText() {
        return featureImgText;
    }

    public void setFeatureImgText(String featureImgText) {
        this.featureImgText = featureImgText;
    }
}

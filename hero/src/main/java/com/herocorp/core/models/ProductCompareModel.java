package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rsawh on 23-Jun-17.
 */

public class ProductCompareModel extends BaseEntity {

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("productFeatureImages")
    private String productFeatureImages;

    @JsonProperty("createDate")
    private String createDate;

    public ProductCompareModel() {
    }

    public ProductCompareModel(int productId, String productFeatureImages, String createDate) {
        this.productId = productId;
        this.productFeatureImages = productFeatureImages;
        this.createDate = createDate;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductFeatureImages() {
        if (productFeatureImages == null) {
            return "";
        }
        return productFeatureImages;
    }

    public void setProductFeatureImages(String productFeatureImages) {
        this.productFeatureImages = productFeatureImages;
    }

    public String getCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

  /*  public void setDate(String date) {
        if (date == null) {
            Long tsLong = System.currentTimeMillis() / 1000;
            this.c = tsLong.toString();
        } else {
            this.createDate = date;
        }
    }*/
}

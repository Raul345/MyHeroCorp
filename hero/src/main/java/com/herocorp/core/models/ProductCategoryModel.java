package com.herocorp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Category Data Model
 * Created by JitainSharma on 11/06/16.
 */
public class ProductCategoryModel extends BaseEntity {

    @JsonProperty("categoryId")
    private int categoryID;

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("categoryRangeFrom")
    private float ccRangeFrom;

    @JsonProperty("categoryRangeTo")
    private float ccRangeTo;

    private long updatedDate;

    public ProductCategoryModel() {
    }

    public ProductCategoryModel(int categoryID, String categoryName,
                                float ccRangeFrom, float ccRangeTo,
                                long updatedDate) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.ccRangeFrom = ccRangeFrom;
        this.ccRangeTo = ccRangeTo;
        this.updatedDate = updatedDate;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public float getCcRangeFrom() {
        return ccRangeFrom;
    }

    public void setCcRangeFrom(float ccRangeFrom) {
        this.ccRangeFrom = ccRangeFrom;
    }

    public float getCcRangeTo() {
        return ccRangeTo;
    }

    public void setCcRangeTo(float ccRangeTo) {
        this.ccRangeTo = ccRangeTo;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }
}